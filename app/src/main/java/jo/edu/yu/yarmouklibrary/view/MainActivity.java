package jo.edu.yu.yarmouklibrary.view;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.background.AlarmReceiver;
import jo.edu.yu.yarmouklibrary.model.User;
import jo.edu.yu.yarmouklibrary.databinding.ActivityMainBinding;
import jo.edu.yu.yarmouklibrary.view.alert.AlertActivity;
import jo.edu.yu.yarmouklibrary.view.base.BaseActivity;
import jo.edu.yu.yarmouklibrary.view.books.BooksActivity;
import jo.edu.yu.yarmouklibrary.view.fines.FinesActivity;
import jo.edu.yu.yarmouklibrary.view.helper.LocaleHelper;
import jo.edu.yu.yarmouklibrary.view.search.SearchActivity;
import jo.edu.yu.yarmouklibrary.viewmodel.UserViewModel;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends BaseActivity implements Observer<User>,View.OnClickListener {
    public static final String EXTRA_ID = "user_id";
    private ActivityMainBinding mBinding;
    private String id;
    UserViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        id=getIntent().getStringExtra(EXTRA_ID);
        mViewModel=ViewModelProviders.of(this).get(UserViewModel.class);
        if(id==null||id.isEmpty()){
            mViewModel.getID();
        }

        mViewModel.getUser(id).observe(this,this);
        mViewModel.getLoader().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                mBinding.progressBarLoading.setVisibility(visibility);
            }
        });
        mBinding.textId.setText(id);
        mBinding.buttonFines.setOnClickListener(this);
        mBinding.buttonAlerts.setOnClickListener(this);
        mBinding.buttonBooks.setOnClickListener(this);
        mBinding.buttonSearch.setOnClickListener(this);
        mBinding.buttonLogout.setOnClickListener(this);
        mBinding.buttonRefresh.setOnClickListener(this);
        mBinding.buttonLanguageSwitch.setOnClickListener(this);
        scheduleAlarm();
    }
    //##################################
    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = SystemClock.elapsedRealtime();
        // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstMillis,
                1*5*1000, pIntent);
        Log.i("LIBRARY_BACKGROUND", "Alarm scheduled");


    }
    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
    //##############################
    @Override
    public void onChanged(User user) {
        if(mBinding.getUser()!=null) {
            if (mBinding.getUser().getBooksCount() != user.getBooksCount())
                highlightChange(mBinding.textReservations);
            if (mBinding.getUser().getLateBooksCount() != user.getLateBooksCount())
                highlightChange(mBinding.textLateBooks);
        }
        mBinding.setUser(user);
    }
    @Override
    public void onClick(View view) {
        if(view == mBinding.buttonFines){
            Intent intent=new Intent(MainActivity.this,FinesActivity.class);
            startActivity(intent);
        }else if(view==mBinding.buttonLanguageSwitch){
            if(LocaleHelper.getLanguage(MainActivity.this).equals("ar"))
                LocaleHelper.setLocale(MainActivity.this,"en");
            else LocaleHelper.setLocale(MainActivity.this,"ar");
            recreate();
        }
        else if(view==mBinding.buttonLogout){
            mViewModel.logout();
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
        else if(view==mBinding.buttonRefresh){
            highlightChange(mBinding.textReservations);
            highlightChange(mBinding.textLateBooks);
        }else if(view==mBinding.buttonSearch){
            Intent intent=new Intent(this,SearchActivity.class);
            startActivity(intent);
        }else if(view==mBinding.buttonBooks){
            Intent intent = new Intent(this,BooksActivity.class);
            startActivity(intent);
        }
        else if(view==mBinding.buttonAlerts)
        {
            Intent intent = new Intent(this,AlertActivity.class);
            startActivity(intent);
        }
    }
    private void highlightChange(View view){
        ObjectAnimator colorFade = ObjectAnimator.ofObject((TextView)view, "textColor" /*view attribute name*/, new ArgbEvaluator(),  Color.WHITE /*from color*/, this.getResources().getColor(R.color.colorPrimary) /*to color*/);
        colorFade.setDuration(1000);
        colorFade.setStartDelay(200);
        colorFade.start();
    }
}
