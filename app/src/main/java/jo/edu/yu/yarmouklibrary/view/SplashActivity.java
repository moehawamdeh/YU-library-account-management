package jo.edu.yu.yarmouklibrary.view;

import androidx.appcompat.app.AppCompatActivity;
import jo.edu.yu.yarmouklibrary.R;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
