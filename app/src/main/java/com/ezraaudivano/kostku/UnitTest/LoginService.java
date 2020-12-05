package com.ezraaudivano.kostku.UnitTest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ezraaudivano.kostku.LoginActivity;
import com.ezraaudivano.kostku.MenuActivity;
import com.ezraaudivano.kostku.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginService {
    int cek=0;

    public void login(final LoginView view, String email, String password) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        final FirebaseUser[] user = new FirebaseUser[1];


        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user[0] = FirebaseAuth.getInstance().getCurrentUser();

                    view.startUserProfileActivity();
                    cek=1;

                } else {
//                    Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    cek=0;
                }
            }
        });
    }
    public Boolean getValid(final LoginView view, String nim, String password){
        final Boolean[] bool = new Boolean[1];
        login(view, nim, password) ;
        if (cek == 1 ){
            bool[0] = true;
        } else {
            bool[0] = false;
        }
        return bool[0];
    }

}
