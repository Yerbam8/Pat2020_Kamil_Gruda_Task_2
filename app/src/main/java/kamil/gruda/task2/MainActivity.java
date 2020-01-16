package kamil.gruda.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static kamil.gruda.task2.LoginActivity.ISLOGIN_PROP_KEY;
import static kamil.gruda.task2.LoginActivity.SHARED_PROP_NAME;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button logoutButton = findViewById(R.id.logoutButton);
        final Button loginButton = findViewById(R.id.loginButton);
        SharedPreferences preferences = getSharedPreferences(SHARED_PROP_NAME, 0);
        boolean isLog = preferences.getBoolean(ISLOGIN_PROP_KEY, false);
        if (!isLog) {
            logoutButton.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.VISIBLE);
        }
        logoutButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            logout();
        });
        loginButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PROP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this, R.string.logout_toast, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


}

