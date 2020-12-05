package com.ezraaudivano.kostku.UnitTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private MaterialButton btnLogin;
    private TextInputEditText email, password;
    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnlogin);
        email = findViewById(R.id.emailLog);
        password = findViewById(R.id.passwordLog);
        presenter = new LoginPresenter(this, new LoginService());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLoginClicked();
            }
        });
    }

        @Override
        public String getEmail() {
            return email.getText().toString();
        }
        @Override
        public void showEmailError(String message) {
            email.setError(message);
        }

        @Override
        public String getPassword() {
            return password.getText().toString();
        }
        @Override
        public void showPasswordError(String message) {
            password.setError(message);
        }

        @Override
        public void startMainActivity() {
            new ActivityUtil(this).startMainActivity();
        }

        @Override
        public void startUserProfileActivity() {
            new ActivityUtil(this).startUserProfile();
        }

        @Override
        public void showLoginError(String message) {
            Toast.makeText(this, message, LENGTH_SHORT).show();
        }

        @Override
        public void showErrorResponse(String message) {
            Toast.makeText(this, message, LENGTH_SHORT).show();
        }
    }