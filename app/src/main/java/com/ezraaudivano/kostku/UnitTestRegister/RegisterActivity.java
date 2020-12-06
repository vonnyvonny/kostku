package com.ezraaudivano.kostku.UnitTestRegister;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.UnitTestRegister.ActivityUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import static android.widget.Toast.LENGTH_SHORT;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private MaterialButton btnSignup;
    private TextInputEditText regname, regemail, repassword, retypepassword;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignup = findViewById(R.id.btnSignup);
        regname = findViewById(R.id.reg_name);
        regemail = findViewById(R.id.reg_email);
        repassword = findViewById(R.id.reg_pass);
        retypepassword = findViewById(R.id.reg_Repass);

        presenter = new RegisterPresenter(this, new RegisterService());
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onRegisterClicked();
            }
        });
    }

    @Override
    public String getNama() {
        return regname.getText().toString();
    }
    @Override
    public void showNamaError(String message) {
        regname.setError(message);
    }

    @Override
    public String getEmail() {
        return regemail.getText().toString();
    }
    @Override
    public void showEmailError(String message) {
        regemail.setError(message);
    }

    @Override
    public String getRePassword() {
        return retypepassword.getText().toString();
    }
    @Override
    public void showRePassError(String message) {
        retypepassword.setError(message);
    }

    @Override
    public String getPassword() {
        return repassword.getText().toString();
    }
    @Override
    public void showPasswordError(String message) {
        repassword.setError(message);
    }

    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

//    @Override
//    public void startUserProfileActivity() {
//        new ActivityUtil(this).startUserProfile();
//    }

    @Override
    public void showRegisterError(String message) {
        Toast.makeText(this, message, LENGTH_SHORT).show();
    }
    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, LENGTH_SHORT).show();
    }
}
