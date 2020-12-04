package com.ezraaudivano.kostku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btnLogin;
    private TextView register;
    private TextInputEditText email, password;
    private TextInputLayout emailL, passwordL;
    View v;
    private SharedPreferences preferences;
    public static final int mode = Activity.MODE_PRIVATE;


    FirebaseAuth fAuth;
    String emailString;

    private String CHANNEL_ID = "Channel 1";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnlogin);
        register = findViewById(R.id.registerClick);
        fAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailLog);
        password = findViewById(R.id.passwordLog);

        emailL = findViewById(R.id.input_email_layout);
        passwordL = findViewById(R.id.input_passwordLog_layout);


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String em = email.getText().toString().trim();
                String ps = password.getText().toString().trim();

                if(TextUtils.isEmpty(em) || TextUtils.isEmpty(ps) || ps.length() < 6 || !Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
                    if(TextUtils.isEmpty(em) || !Patterns.EMAIL_ADDRESS.matcher(em).matches()){
                        emailL.setError("Invalid Email!");
                    }else{
                        emailL.setError("");
                    }
                    if(ps.isEmpty()){
                        passwordL.setError("Invalid Password!");
                    }else {
                        passwordL.setError("");
                    }

                    if(ps.length() < 6){
                        passwordL.setError("Password too short!");
                    }else {
                        passwordL.setError("");
                    }

                }else {
                    System.out.println("SYALALABEZA");
                       fAuth.signInWithEmailAndPassword(em, ps).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = FirebaseAuth.getInstance().getCurrentUser();
                                    if(!user.isEmailVerified())
                                    {
                                        Toast.makeText(LoginActivity.this, "Harap Verifikasi dulu", Toast.LENGTH_SHORT).show();
                                    }else{
                                        addNotification();
                                        Toast.makeText(LoginActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                                        savedPreferences();
                                        Bundle mBundle = new Bundle();
                                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                        mBundle.putString("email", em);
                                        intent.putExtra("EditProf", mBundle);
                                        startActivity(intent);
                                        finish();
                                        createNotificationChannel();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public void savedPreferences () {
        TextInputEditText email = findViewById(R.id.emailLog);
//        TextInputEditText lastNameText = v.findViewById(R.id.input_last);
        String name = "profile";
        preferences = this.getSharedPreferences(name, mode);
        SharedPreferences.Editor editor = preferences.edit();
        if(!email.getText().toString().isEmpty() ){
            editor.putString("email", email.getText().toString());
            editor.apply();

        }else{
            Toast.makeText(LoginActivity.this, "Fill Correctly", Toast.LENGTH_SHORT).show();

        }

    }

    private void addNotification() {
        //konstruktor NotificationCompat.Builder harus diberi XHANNEL ID untuk API LEVEL 26+
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Selamat Datang di KostKu")
                .setContentText("Daftarkan kost anda untuk mendapat pelanggan baru")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Membuat intent yang menampilkan notifikasi
        Intent notificationIntent = new Intent(this, SignUpActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        //Menampilkan Notifikasi
        NotificationManager manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        //NotificationChannel diperlukan untuk API 26+
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}