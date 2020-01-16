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

import java.util.Objects;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +
            "(?=.*[@!#$%^&+=])" +
            "(?=\\S+$)" +
            ".{6,}" +
            "$");

    public static final String SHARED_PROP_NAME = "loginapp";
    public static final String LOGIN_PROP_KEY = "login";
    public static final String EMAIL_PROP_KEY = "email";
    public static final String PASSWORD_PROP_KEY = "password";
    public static final String ISLOGIN_PROP_KEY = "isLogin";

    private EditText login, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        login = findViewById(R.id.editTextLogin);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        final Button submit = findViewById(R.id.submit);


        submit.setOnClickListener(v -> {
            if (check()) {
                save();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }


    private void save() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PROP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGIN_PROP_KEY, login.getText().toString());
        editor.putString(EMAIL_PROP_KEY, email.getText().toString());
        editor.putString(PASSWORD_PROP_KEY, password.getText().toString());
        editor.putBoolean(ISLOGIN_PROP_KEY, true);
        editor.apply();
    }


    private boolean validateLogin() {
        String loginInput = login.getText().toString();

        if (loginInput.isEmpty()) {
            login.setError(getString(R.string.empty_field_error));
            return false;
        } else if (loginInput.matches("\\S")) {
            login.setError(getString(R.string.white_spaces_error));
            return false;

        } else if (loginInput.toLowerCase().equals(loginInput)) {
            return true;
        } else {
            login.setError(getString(R.string.lowercase_error));
            return false;
        }
    }

    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError(getString(R.string.empty_email_error));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError(getString(R.string.valid_email_error));
            return false;

        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError(getString(R.string.empty_password_error));
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError(getString(R.string.weak_password_error));
            return false;
        } else {
            return true;
        }
    }

    private boolean check() {
        if (!validateLogin() | !validateEmail() | !validatePassword()) {
            return false;
        }
        Toast.makeText(this, R.string.login_toast, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


}
