package jo.edu.yu.yarmouklibrary.view.books;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import jo.edu.yu.yarmouklibrary.model.Transaction;
import jo.edu.yu.yarmouklibrary.databinding.FragmentBookHistoryBinding;
import jo.edu.yu.yarmouklibrary.view.DateWatcher;
import jo.edu.yu.yarmouklibrary.view.base.BaseHolder;
import jo.edu.yu.yarmouklibrary.view.base.BaseRecyclerAdapter;
import jo.edu.yu.yarmouklibrary.viewmodel.UserViewModel;

public class BooksHistoryFragment  extends Fragment implements Observer<List<Transaction>> {
    private UserViewModel mViewModel;
    private FragmentBookHistoryBinding mBinding;
    private TransactionAdapter mBooksAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBooksAdapter=new TransactionAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_book_history,container,false);
        mBinding.recyclerViewBooksHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerViewBooksHistory.setAdapter(mBooksAdapter);
        DateWatcher watcher=new DateWatcher(mBinding.fieldDateFrom);
        DateWatcher watcher2=new DateWatcher(mBinding.fieldDateTo);
        mBinding.fieldDateFrom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_START = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (mBinding.fieldDateFrom.getRight() - mBinding.fieldDateFrom.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Calendar calendar=Calendar.getInstance();
                        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                mBinding.fieldDateFrom.setText(i+"-"+(i1+1)+"-"+i2);
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        dialog.show();
                        return true;
                    }
                }
                return false;
            }
        });
        mBinding.fieldDateTo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_START = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (mBinding.fieldDateTo.getRight() - mBinding.fieldDateTo.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Calendar calendar=Calendar.getInstance();
                        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                mBinding.fieldDateTo.setText(i+"-"+(i1+1)+"-"+i2);
                            }
                        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        dialog.show();
                        return true;
                    }
                }
                return false;
            }
        });

        mBinding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.getTransactionHistory(mBinding.fieldDateFrom.getText().toString(),mBinding.fieldDateTo.getText().toString())
                        .observe(BooksHistoryFragment.this,BooksHistoryFragment.this);
                mViewModel.getLoader().observe(BooksHistoryFragment.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer visibility) {
                        mBinding.progressBarLoading.setVisibility(visibility);
                    }
                });
            }
        });
        return mBinding.getRoot();
    }
    //TODO: refactor code, use data binding with observer directle @{viewModel.loader}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel=ViewModelProviders.of(this).get(UserViewModel.class);
        //mViewModel.getTransactionHistory().observe(this,this);
//        mViewModel.getLoader().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer visibility) {
//                mBinding.progressBarLoading.setVisibility(visibility);
//            }
//        });
        mViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(mBinding.getRoot(),s,Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onChanged(List<Transaction> booksList) {
        mBooksAdapter.setItems(booksList);
        if(booksList == null || booksList.isEmpty())
        {
            mBinding.layoutNoBooks.setVisibility(View.VISIBLE);
        }
        else {
            mBinding.layoutNoBooks.setVisibility(View.GONE);
        }
    }


    private class TransactionAdapter extends BaseRecyclerAdapter<Transaction> {
        @Override
        protected BaseHolder<Transaction> getHolder(View view) {
            return new TransactionAdapter.BookHolder(view);
        }
        @Override
        protected int getResourceID() {
            return R.layout.item_transaction;
        }
        class BookHolder extends BaseHolder<Transaction> {
            BookHolder(@NonNull View itemView) {
                super(itemView);
            }
            @Override
            public void bind(final Transaction transaction) {
                //TODO use data binding
                TextView title=itemView.findViewById(R.id.text_title);
                TextView barcode=itemView.findViewById(R.id.text_barcode);
                TextView date=itemView.findViewById(R.id.text_date);
                TextView type=itemView.findViewById(R.id.text_type);
                barcode.setText(transaction.getBarCode());
                title.setText(transaction.getTitle());
                type.setText(transaction.getType());
                date.setText(new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(transaction.getTransactionDate()));
            }
        }
    }
}
