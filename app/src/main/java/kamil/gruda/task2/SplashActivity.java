package kamil.gruda.task2;

import androidx.annotation.NonNull;
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
    private long timeOnStart;
    private long timeOnEnd;
    private long timeToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();
        timeOnStart = System.currentTimeMillis();


        final SharedPreferences preferences = getSharedPreferences(SHARED_PROP_NAME, 0);
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


        if (savedInstanceState != null) {

            timeOnEnd = savedInstanceState.getLong(getString(R.string.timeBundle));
            timeToUpdate = savedInstanceState.getLong(getString(R.string.timeToUpdateBundle));
            timeToUpdate = timeToUpdate - timeOnEnd;
            savedInstanceState.putLong(getString(R.string.timeToUpdateBundle), timeToUpdate);
            handler.postDelayed(runnable, timeToUpdate);

        } else {
            handler.postDelayed(runnable, SPLASH_SCREEN_TIME);
            timeToUpdate = 6000L;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        timeOnEnd = System.currentTimeMillis() - timeOnStart;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null)
            handler.removeCallbacks(runnable);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(getString(R.string.timeBundle), timeOnEnd);
        outState.putLong(getString(R.string.timeToUpdateBundle), timeToUpdate);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        timeOnEnd = savedInstanceState.getLong(getString(R.string.timeBundle));
        timeToUpdate = savedInstanceState.getLong(getString(R.string.timeToUpdateBundle));

    }


}
