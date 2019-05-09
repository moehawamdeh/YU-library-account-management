package jo.edu.yu.yarmouklibrary.view.base;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import jo.edu.yu.yarmouklibrary.view.helper.LocaleHelper;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}
