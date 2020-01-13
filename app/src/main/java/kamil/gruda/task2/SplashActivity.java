package kamil.gruda.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import static kamil.gruda.task2.LoginActivity.EMAIL;
import static kamil.gruda.task2.LoginActivity.ISLOGIN;
import static kamil.gruda.task2.LoginActivity.SHARED;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIME = 6000;
    private Handler handler;
    private Runnable runnable;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        loadData();
        String name = preferences.getString(EMAIL, " ");
        TextView textView = findViewById(R.id.splashName);
        final boolean isLog = preferences.getBoolean(ISLOGIN, false);

        if (isLog) {
            textView.setText("Welcome " + name);
        }

        runnable = () -> {
            if (isLog) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, SPLASH_SCREEN_TIME);


    }

    private void loadData() {
        preferences = getSharedPreferences(SHARED, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null)
            handler.removeCallbacks(runnable);
    }
}
