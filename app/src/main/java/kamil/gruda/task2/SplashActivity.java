package kamil.gruda.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.Objects;

import static kamil.gruda.task2.LoginActivity.EMAIL_PROP_KEY;
import static kamil.gruda.task2.LoginActivity.ISLOGIN_PROP_KEY;
import static kamil.gruda.task2.LoginActivity.SHARED_PROP_NAME;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIME = 6000;
    private Handler handler;
    private Runnable runnable;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();

        loadData();
        String name = preferences.getString(EMAIL_PROP_KEY, " ");
        TextView textView = findViewById(R.id.splashName);
        final boolean isLog = preferences.getBoolean(ISLOGIN_PROP_KEY, false);

        if (isLog) {
            textView.setText(MessageFormat.format("{0} {1}", getString(R.string.welcome), name));
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
        preferences = getSharedPreferences(SHARED_PROP_NAME, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null)
            handler.removeCallbacks(runnable);
    }
}
