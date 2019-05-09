package jo.edu.yu.yarmouklibrary.view.books;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.model.data.LibraryPolicy;
import jo.edu.yu.yarmouklibrary.model.Book;
import jo.edu.yu.yarmouklibrary.databinding.FragmentBooksBinding;
import jo.edu.yu.yarmouklibrary.view.base.BaseHolder;
import jo.edu.yu.yarmouklibrary.view.base.BaseRecyclerAdapter;
import jo.edu.yu.yarmouklibrary.viewmodel.UserViewModel;

public class BooksFragment extends Fragment implements Observer<List<Book>> {
    private UserViewModel mViewModel;
    private FragmentBooksBinding mBinding;
    private BooksFragment.BooksAdapter mBooksAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBooksAdapter=new BooksFragment.BooksAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_books,container,false);
        mBinding.recyclerViewBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerViewBooks.setAdapter(mBooksAdapter);

        return mBinding.getRoot();
    }
    //TODO: refactor code, use data binding with observer directle @{viewModel.loader}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel=ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mViewModel.getBooks().observe(this,this);
        mViewModel.getLoader().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                mBinding.progressBarLoading.setVisibility(visibility);
            }
        });
        mViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(mBinding.getRoot(),s,Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onChanged(List<Book> booksList) {
        mBooksAdapter.setItems(booksList);
        if(booksList == null || booksList.isEmpty())
        {
            mBinding.layoutNoBooks.setVisibility(View.VISIBLE);
        }
        else {
            mBinding.layoutNoBooks.setVisibility(View.GONE);
        }
    }


    class BooksAdapter extends BaseRecyclerAdapter<Book> {
        @Override
        protected BaseHolder<Book> getHolder(View view) {
            return new BooksFragment.BooksAdapter.BookHolder(view);
        }
        @Override
        protected int getResourceID() {
            return R.layout.item_book_summary;
        }
        private class BookHolder extends BaseHolder<Book> {
            BookHolder(@NonNull View itemView) {
                super(itemView);
                       }
            @Override
            public void bind(final Book book) {
                //TODO use data binding
                TextView title=itemView.findViewById(R.id.text_title);
                TextView dueDate=itemView.findViewById(R.id.text_due_date);
                TextView status=itemView.findViewById(R.id.text_status);
                TextView barcode=itemView.findViewById(R.id.text_barcode);
                barcode.setText(book.getBarCode());
                title.setText(book.getTitle());
                dueDate.setText(new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(book.getDueDate()));
                long days=book.getDaysLeft();
                boolean extend=true;
                switch (LibraryPolicy.getOverdueStatus(days)){
                    case HOLD:{
                        extend=false;
                        status.setText(getString(R.string.hold));
                        break;
                    }
                    case SAFE_LATE:{
                        status.setText(getResources().getQuantityString(R.plurals.days_left,(int)days,(int)days));
                        break;
                    }
                    case LATE:{
                        status.setText(getResources().getQuantityString(R.plurals.days_left,(int)days,(int)days));
                        break;
                    }
                    case LOST:{
                        status.setText(getResources().getString(R.string.lost));
                        break;
                    }
                }
                if(extend){
                    Button extendButton=itemView.findViewById(R.id.materialButton);
                    extendButton.setVisibility(View.VISIBLE);
                    extendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO implement extend
                            Intent intent=new Intent(getActivity(),BookActivity.class);
                            intent.putExtra(BookActivity.EXTRA_BOOK,book);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }
}
