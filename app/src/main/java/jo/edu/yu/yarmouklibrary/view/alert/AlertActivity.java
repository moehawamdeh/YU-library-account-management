package jo.edu.yu.yarmouklibrary.view.alert;

import androidx.fragment.app.Fragment;
import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.view.base.SingleFragmentActivity;
import jo.edu.yu.yarmouklibrary.view.fines.FinesFragment;

public class AlertActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AlertFragment();
    }
    @Override
    protected String setTitle() {
        return getResources().getString(R.string.alerts);
    }
}
