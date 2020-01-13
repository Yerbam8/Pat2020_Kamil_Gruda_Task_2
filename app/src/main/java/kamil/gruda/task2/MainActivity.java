package kamil.gruda.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import static kamil.gruda.task2.LoginActivity.SHARED;

public class MainActivity extends AppCompatActivity {
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            if (logoutButton.getText().toString().equals("Login")) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            } else {
                logout();
            }


        });
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        logoutButton.setText("Login");
        Toast.makeText(this, "Logout ", Toast.LENGTH_SHORT).show();

    }
}

