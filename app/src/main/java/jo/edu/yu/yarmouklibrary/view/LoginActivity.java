package jo.edu.yu.yarmouklibrary.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.model.data.UserCache;
import jo.edu.yu.yarmouklibrary.databinding.ActivityLoginBinding;
import jo.edu.yu.yarmouklibrary.view.base.BaseActivity;
import jo.edu.yu.yarmouklibrary.view.helper.LocaleHelper;
import jo.edu.yu.yarmouklibrary.viewmodel.LogInViewModel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.daandtu.webscraper.WebScraper;


public class LoginActivity extends BaseActivity implements Observer<LogInViewModel.AuthState> {
    private ActivityLoginBinding mBinding;
    private LogInViewModel mViewModel;
    private String mUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=ViewModelProviders.of(this).get(LogInViewModel.class);
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_login);
        mBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBinding.inputUserId.getEditableText()!=null && mBinding.inputPassword.getEditableText()!=null) {
                    String id = mBinding.inputUserId.getText().toString();
                    mUID=id;
                    String pass = mBinding.inputPassword.getText().toString();
                    if(id.isEmpty()){
                        mBinding.inputLayoutUserId.setError(getString(R.string.error_empty_id));
                        return;
                    }
                    if(pass.isEmpty()){
                        mBinding.inputLayoutUserId.setError(getString(R.string.error_empty_pass));
                        return;
                    }
                    //

                    //Authenticate
                    //mViewModel.getAuthenticationState(id).observe(LoginActivity.this,LoginActivity.this);
                    //show loading
                    //
                }
            }
        });
        mBinding.buttonLanguageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LocaleHelper.getLanguage(LoginActivity.this).equals("ar"))
                    LocaleHelper.setLocale(LoginActivity.this,"en");
                else LocaleHelper.setLocale(LoginActivity.this,"ar");
                recreate();
            }
        });

    }

    @Override
    public void onChanged(LogInViewModel.AuthState authState) {
        if(authState==LogInViewModel.AuthState.GRANTED)
        {
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            if(mBinding.stayLoggedCheckBox.isChecked())
                UserCache.persistID(LoginActivity.this,mUID);
            intent.putExtra(MainActivity.EXTRA_ID,mUID);
            ////////////////
            createNotificationChannel();
            ////////////////
            startActivity(intent);
            finish();
        }
        else if(authState==LogInViewModel.AuthState.DECLINED){
            //show pop_up
            //stop loading
        }
    }
    //TODO is this the best place to create the channel?
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test";
            String description = "testing";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("testchannel", name, importance);
            channel.setDescription(description);
            channel.setShowBadge(true);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
