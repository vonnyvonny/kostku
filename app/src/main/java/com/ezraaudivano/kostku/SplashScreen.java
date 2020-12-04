package com.ezraaudivano.kostku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.ezraaudivano.kostku.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.SQLOutput;

public class SplashScreen extends AppCompatActivity {
    User users;
    TextView helloTV;
    private SharedPreferences preferences;
    String emailString="",telpString, usernameTemp="";
    FirebaseAuth fAuth;
    String nameUser;
    String namauserBundle, emailBundle;
    String theme;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static final int mode = Activity.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fAuth = FirebaseAuth.getInstance();
        helloTV = findViewById(R.id.textViewSplash);

        if(fAuth.getCurrentUser() != null)
        {
            String nameFB = user.getDisplayName();
            helloTV.setText(nameFB);
        }

        loadPreferences();

    }

    private void loadPreferences(){
        String name = "profile";
        preferences = getSharedPreferences(name, mode);
        emailString = preferences.getString("email", "");

        String nameP = "theme";
        preferences = getSharedPreferences(nameP, mode);
        theme = preferences.getString("tema", "");


        if(fAuth.getCurrentUser() != null) {

            Thread thread = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(SplashScreen.this, MenuActivity.class));
                        finish();

                    }
                }
            };
            thread.start();
        }else{
//            helloTV.setText("");
            Thread thread = new Thread() {
                public void run() {
                    try {
                        sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                        finish();

                    }
                }
            };
            thread.start();
        }
        if (theme.equalsIgnoreCase("temaHijau")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

//    private void getName() {
//
//        class AddUser extends AsyncTask<Void, Void, Void> {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                System.out.println("\n\n\n\n\n\n  PERTAMA \n\n\n\n\n");
//                users = DatabaseUser.getInstance(getApplicationContext()).getDatabase()
//                        .userDao()
//                        .getName(emailString);
//                System.out.println("\n\n\n\n\n\n  PERTAMA adalah = "+users.getNama()+"\n\n\n\n\n");
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                helloTV.setText("Hello " + users.getNama());
//                System.out.println("TEMA ADALAH = "+users.getTheme());
//                namauserBundle = nameUser;
////                    if(users.getTheme().equalsIgnoreCase("temaHijau")) {
//                    if (theme.equalsIgnoreCase("temaHijau")) {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    } else {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    }
////                }
//            }
//        }
//
//        AddUser add = new AddUser();
//        add.execute();
//    }

}