package jo.edu.yu.yarmouklibrary.view.fines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.model.Fine;
import jo.edu.yu.yarmouklibrary.databinding.FragmentFinesBinding;
import jo.edu.yu.yarmouklibrary.view.base.BaseHolder;
import jo.edu.yu.yarmouklibrary.view.base.BaseRecyclerAdapter;
import jo.edu.yu.yarmouklibrary.viewmodel.UserViewModel;

public class FinesFragment extends Fragment implements Observer<List<Fine>> {
    private UserViewModel mViewModel;
    private FragmentFinesBinding mBinding;
    private FinesAdapter mFinesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFinesAdapter=new FinesAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_fines,container,false);
        mBinding.cardInfo.setInfoText(getResources().getString(R.string.fines_policy_content));
        mBinding.cardInfo.setTitleText(getResources().getString(R.string.fines_policy));
        mBinding.recyclerViewFines.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerViewFines.setAdapter(mFinesAdapter);

        return mBinding.getRoot();
    }
    //TODO: refactor code, use data binding with observer directle @{viewModel.loader}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel=ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mViewModel.getFines().observe(this,this);
        mViewModel.getTotalFines().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float total) {
                mBinding.textTotal.setText(String.valueOf(total));
            }
        });
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
    public void onChanged(List<Fine> fineList) {
        mFinesAdapter.setItems(fineList);
        if(fineList == null || fineList.isEmpty())
        {
            mBinding.layoutNoFines.setVisibility(View.VISIBLE);
            mBinding.textTotal.setText(String.valueOf(0));
        }
        else {
            mBinding.layoutNoFines.setVisibility(View.GONE);
            mBinding.textTotal.setText(String.valueOf(fineList.size()));
        }
    }
    class FinesAdapter extends BaseRecyclerAdapter<Fine>{
        @Override
        protected BaseHolder<Fine> getHolder(View view) {
            return new FineHolder(view);
        }
        @Override
        protected int getResourceID() {
            return R.layout.item_fine;
        }
        class FineHolder extends BaseHolder<Fine> {
            FineHolder(@NonNull View itemView) {
                super(itemView);
            }
            @Override
            public void bind(Fine fine) {
                TextView value=itemView.findViewById(R.id.text_value);
                TextView book=itemView.findViewById(R.id.text_book);
                TextView barcode=itemView.findViewById(R.id.text_barcode);
                TextView dueDate=itemView.findViewById(R.id.text_due_date);
                TextView returnDate=itemView.findViewById(R.id.text_return_date);
                TextView type=itemView.findViewById(R.id.text_type);

                type.setText(fine.getType());
                book.setText(fine.getBookTitle());
                barcode.setText(fine.getBarcode());
                if(fine.getDue()!=null)
                dueDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(fine.getDue()));
                else dueDate.setText("--");
                if(fine.getReturnDate()!=null)
                returnDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(fine.getReturnDate()));
                else returnDate.setText("--");
                value.setText(String.valueOf(fine.getValue()));
            }
        }
    }
}
