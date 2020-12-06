package com.ezraaudivano.kostku.UnitTestRegister;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ezraaudivano.kostku.LoginActivity;
import com.ezraaudivano.kostku.SignUpActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterService {
    public static final String TAG = "TAG";
    int cek=0;
    public void register(final RegisterView view, String email, String nama, String password, String rePassword) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        final FirebaseUser[] user = new FirebaseUser[1];

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nama)
                .build();

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                                                cek = 1;
                                                view.startMainActivity();

                                            }
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                } else {
                    cek = 0;

                }
            }
        });

    }

    public Boolean getValid (final RegisterView view, String email, String nama, String password, String rePassword){
        final Boolean[] bool = new Boolean[1];
        register(view,email,nama,password,rePassword);
        if (cek == 1 ){
            bool[0] = true;
        } else {
            bool[0] = false;
        }
        return bool[0];
    }
}
