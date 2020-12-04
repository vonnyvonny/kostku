package com.ezraaudivano.kostku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class EditProfile extends AppCompatActivity {
    private static final String TAG = "EditProfile";
    ImageView img;
    TextView editTV,editName,editHP,editEmail,editPass;
    FirebaseAuth fAuth;
    Button btnEdit, btnBack;

    MaterialButton btnClick;

    Bundle mBundle;
    String viewName,viewTelp,vieweEmail;

    User users;

    String theme;

    private SharedPreferences preferences;
    public static final int mode = Activity.MODE_PRIVATE;
    public static final int CAMERA_FROM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    StorageReference storageReference;
    String currentPhotoPath;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String nameFB = user.getDisplayName();
    String emailFB = user.getEmail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        img = findViewById(R.id.imageViewProfile);
        editTV = findViewById(R.id.editProfTxt);
        fAuth = FirebaseAuth.getInstance();
        btnBack = findViewById(R.id.btnBackProf);
        btnEdit  = findViewById(R.id.btnEditProf);
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editPass = findViewById(R.id.reg_pass);
        btnClick = findViewById(R.id.btnTheme);

        storageReference = FirebaseStorage.getInstance().getReference();
        editName.setText(nameFB);
        editEmail.setText(emailFB);
        System.out.println(""+nameFB);


//        getBundle();




        StorageReference profileRef = storageReference.child(fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img);
            }
        });

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mode = AppCompatDelegate.getDefaultNightMode();
                if (mode == AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    theme = "temaHijau";
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    theme = "temaAbu";
                }
                savedPreferences();
            }
        });



        editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle mBundle = new Bundle();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                mBundle.putString("fullname", viewName);
                mBundle.putString("email", vieweEmail);
                intent.putExtra("EditProf", mBundle);
                startActivity(intent);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Warning");
                    builder.setMessage("Are you sure ?");
                    builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.println("NAMA ADALAH = " + editName.getText().toString());


                            if(editPass.getText().toString().equalsIgnoreCase(""))
                            {
                                Toast.makeText(EditProfile.this, "Masukan Password Anda", Toast.LENGTH_SHORT).show();
                            }else {
                                user.updatePassword(editPass.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User password updated.");
                                                }
                                            }
                                        });
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(editName.getText().toString())
                                        .build();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User profile updated.");
                                                }
                                            }
                                        });

                                Bundle mBundle = new Bundle();
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                mBundle.putString("fullname", viewName);
                                mBundle.putString("email", vieweEmail);
                                intent.putExtra("EditProf", mBundle);
                                startActivity(intent);
                                finish();
                            }


                        }
                    });
                    builder.show();

            }
        });





    }

    private void askCameraPermissions ()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_FROM_CODE);;
        }else{
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_FROM_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else{
                Toast.makeText(this, "Camera Permission is Required to Use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==102) {
            if (resultCode == Activity.RESULT_OK) {
                btnBack.setEnabled(false);
                btnEdit.setEnabled(false);
                File f = new File(currentPhotoPath);
                Uri imageUri = Uri.fromFile(f);
                uploadImage(imageUri);
            }
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {

                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.ezraaudivano.kostku.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 102);

            }
        }
    }


    private void uploadImage(Uri imageUri) {
        final StorageReference fileRef = storageReference.child(fAuth.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                btnBack.setEnabled(true);
                btnEdit.setEnabled(true);
                Toast.makeText(EditProfile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(img);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void savedPreferences () {

        String name = "theme";
        preferences = this.getSharedPreferences(name, mode);
        SharedPreferences.Editor editor = preferences.edit();

            editor.putString("tema", theme);
            editor.apply();



    }


}
