package jo.edu.yu.yarmouklibrary.view.base;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.view.helper.LocaleHelper;
import jo.edu.yu.yarmouklibrary.viewmodel.UserViewModel;

public abstract class SingleFragmentActivity extends BaseActivity {
    protected abstract Fragment createFragment();
    protected abstract String setTitle();
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ActionBar bar = getSupportActionBar();
        if(bar!=null)
        {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setTitle(setTitle());
        }
        UserViewModel viewModel=ViewModelProviders.of(this).get(UserViewModel.class);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
