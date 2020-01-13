package kamil.gruda.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +
            "(?=.*[@!#$%^&+=])" +
            "(?=\\S+$)" +
            ".{6,}" +
            "$");

    public static final String SHARED = "loginapp";
    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ISLOGIN = "isLogin";

    private EditText login, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        login = findViewById(R.id.editTextLogin);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        Button submit = findViewById(R.id.submit);


        submit.setOnClickListener(v -> {
            if (check()) {
                save();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }


    private void save() {
        SharedPreferences preferences = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGIN, login.getText().toString());
        editor.putString(EMAIL, email.getText().toString());
        editor.putString(PASSWORD, password.getText().toString());
        editor.putBoolean(ISLOGIN, true);
        editor.apply();
    }


    private boolean validateLogin() {
        String loginInput = login.getText().toString();

        if (loginInput.isEmpty()) {
            login.setError("Field can't be empty");
            return false;
        } else if (loginInput.matches("\\S")) {
            login.setError("White spaces are not allowed!");
            return false;

        } else if (!isUpper(loginInput)) {
            login.setError("Only lowercase");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Email can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;

        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password too weak");
            return false;
        } else {
            return true;
        }
    }

    private boolean check() {
        if (!validateLogin() | !validateEmail() | !validatePassword()) {
            return false;
        }
        Toast.makeText(this, "Login into", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean isUpper(String str) {

        char[] charArray = str.toCharArray();

        for (char c : charArray) {
            if (Character.isUpperCase(c))
                return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
