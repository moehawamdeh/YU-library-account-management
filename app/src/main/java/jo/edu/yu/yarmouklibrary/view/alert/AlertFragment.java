package jo.edu.yu.yarmouklibrary.view.alert;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
import jo.edu.yu.yarmouklibrary.databinding.FragmentAlertsBinding;
import jo.edu.yu.yarmouklibrary.databinding.FragmentFinesBinding;
import jo.edu.yu.yarmouklibrary.model.Fine;
import jo.edu.yu.yarmouklibrary.view.base.BaseHolder;
import jo.edu.yu.yarmouklibrary.view.base.BaseRecyclerAdapter;
import jo.edu.yu.yarmouklibrary.viewmodel.UserViewModel;

import static androidx.core.content.ContextCompat.getSystemService;

public class AlertFragment extends Fragment  {
    private UserViewModel mViewModel;
    private FragmentAlertsBinding mBinding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_alerts,container,false);

        mBinding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                NotificationManager notificationManager =
                        (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
// The id of the channel.
                String id = "testchannel";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.deleteNotificationChannel(id);
                }
            }
        });
        return mBinding.getRoot();
    }
    //TODO: refactor code, use data binding with observer directle @{viewModel.loader}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel=ViewModelProviders.of(getActivity()).get(UserViewModel.class);

    }



}
