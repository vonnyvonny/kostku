package com.ezraaudivano.kostku;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.ezraaudivano.kostku.model.User;
import com.ezraaudivano.kostku.ui.gallery.GalleryFragment;
import com.ezraaudivano.kostku.ui.pengumuman.PengumumanFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import de.hdodenhof.circleimageview.CircleImageView;

public class MenuActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    String emailString;
    public static final int mode = Activity.MODE_PRIVATE;
    TextView emailSide, tvNameSide;
    public Bundle mBundle;

    User users;

    private AppBarConfiguration mAppBarConfiguration;

    StorageReference stoRef;
    FirebaseAuth firebaseAuth;
    CircleImageView img;
    String viewName, viewEmail;
    private static final String TAG = SignUpActivity.class.getSimpleName();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String nameFB = user.getDisplayName();
    String emailFB = user.getEmail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        System.out.println("name : "+nameFB);
        System.out.println("email : "+emailFB);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        emailSide = navigationView.getHeaderView(0).findViewById(R.id.tvEmailSide);
        tvNameSide = navigationView.getHeaderView(0).findViewById(R.id.tvNameSide);
        setText();
        emailSide.setText(emailFB);
        tvNameSide.setText(nameFB);

        img = findViewById(R.id.imgSide);
        img = navigationView.getHeaderView(0).findViewById(R.id.imgSide);
        firebaseAuth = FirebaseAuth.getInstance();
        stoRef = FirebaseStorage.getInstance().getReference();


        StorageReference profRef = stoRef.child(firebaseAuth.getCurrentUser().getUid()+"profile.jpg");
        profRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img);
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String mag = "Successfull";
                        if(!task.isSuccessful()){
                            mag = "Failed";
                        }
                        
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int tempId = item.getItemId();

        if(tempId == R.id.action_settings){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MenuActivity.this, LoginActivity.class));
            finish();
        }else if (tempId == R.id.editProfSelect){
            Bundle mBundle = new Bundle();
            Intent intent = new Intent(getApplicationContext(), EditProfile.class);
            mBundle.putString("fullname", nameFB);
            mBundle.putString("email", emailFB);
            intent.putExtra("EditProf", mBundle);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    private void loadPreferences(){
//
//        String name = "profile";
//        preferences = getSharedPreferences(name, mode);
//        if(preferences != null){
//            emailString = preferences.getString("email", "");
//            emailSide.setText(emailString);
//        }
//    }

//    public void getBundle (){
//        //get Bundle from activity
//        mBundle = getIntent().getBundleExtra("EditProf");
//
//        //get variable from bundle
//        if(mBundle.getString("email") != null){
//            viewName = mBundle.getString("fullname");
//            viewEmail = mBundle.getString("email");
//        }else
//        {
//            viewEmail = mBundle.getString("em");
//        }
//    }

    public void setText (){
            tvNameSide.setText(viewName);
    }

//    private void getName() {
//
//        class AddUser extends AsyncTask<Void, Void, Void> {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                users = DatabaseUser.getInstance(getApplicationContext()).getDatabase()
//                        .userDao()
//                        .getName(emailString);
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                tvNameSide.setText(users.getNama());
//
//            }
//        }
//
//        AddUser add = new AddUser();
//        add.execute();
//    }



}