package com.ezraaudivano.kostku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ezraaudivano.kostku.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "TAG";

    TextInputEditText nameET, telpET, emailET, usernameET, passET, repassET;
    Button btnSignUp;
    TextInputLayout nameL, telpL, emailL, usernameL, passL, repassL;

    TextView  btnToLogin;
    FirebaseAuth fAuth;
    ImageView selectedImg;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameET = findViewById(R.id.reg_name);
        emailET = findViewById(R.id.reg_email);
        passET = findViewById(R.id.reg_pass);

        repassET = findViewById(R.id.reg_Repass);

        btnToLogin = findViewById(R.id.toLoginBtn);
        btnSignUp = findViewById(R.id.btnSignup);

        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        String name = nameET.getText().toString().trim();

        String passRe = repassET.getText().toString().trim();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()) {
                    String pass = passET.getText().toString().trim();
                    String email = emailET.getText().toString().trim();
                    String name = nameET.getText().toString().trim();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();

                    fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser fuser = fAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        fuser.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "User profile updated.");
                                                        }
                                                    }
                                                });
                                        Toast.makeText(SignUpActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                    }
                                });



//                                Toast.makeText(SignUpActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();

                            } else {
                                Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });

    }


    public Boolean validation() {

        nameL = findViewById(R.id.input_name_layout);

        emailL = findViewById(R.id.input_email_layout);
//        usernameL = findViewById(R.id.input_username_layout);
        passL = findViewById(R.id.input_pass_layout);
        repassL = findViewById(R.id.input_passRe_layout);

        String name = nameET.getText().toString().trim();

        String email = emailET.getText().toString().trim();
        String pass = passET.getText().toString().trim();
        String passRe = repassET.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)  || TextUtils.isEmpty(pass) || pass.length() < 6 || TextUtils.isEmpty(passRe) || !passRe.equals(pass))
        {
            if (TextUtils.isEmpty(name)) {
                nameL.setError("Please Fill name");
            } else {
                nameL.setError("");
            }

            if (TextUtils.isEmpty(email)) {
                emailL.setError("Please Fill Email");
            } else {
                emailL.setError("");
            }

            if (TextUtils.isEmpty(pass)) {
                passL.setError("Please fill Password");
            } else if (pass.length() < 6) {
                passL.setError("Password too short");
            } else {
                passL.setError("");
            }

            if (TextUtils.isEmpty(passRe)) {
                repassL.setError("Please fill Password");
            } else if (!passRe.equals(pass)) {
                repassL.setError("Your password does not match");
            } else {
                repassL.setError("");
            }
            return false;
        }
            return true;

    }



}