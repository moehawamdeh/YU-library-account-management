package jo.edu.yu.yarmouklibrary.view.fines;

import androidx.fragment.app.Fragment;
import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.view.base.SingleFragmentActivity;

public class FinesActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new FinesFragment();
    }
    @Override
    protected String setTitle() {
        return getResources().getString(R.string.fines);
    }
}
