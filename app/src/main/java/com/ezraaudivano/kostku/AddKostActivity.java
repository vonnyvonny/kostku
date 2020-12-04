package com.ezraaudivano.kostku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ezraaudivano.kostku.API.KostAPI;
import com.ezraaudivano.kostku.model.Kost;
import com.ezraaudivano.kostku.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class AddKostActivity extends AppCompatActivity {
    private Button button, addKost, btnUnggah, btnGallery;
    private TextInputEditText namaKost, alamatKost, size, sisa;
    private RadioGroup radioGroup;
    private ImageView img;
    String message, imageStr = "", selected;

    private Bitmap bitmap;
    private Uri selectedImage = null;

    private Kost kost;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String emailFB = user.getEmail();
    String getTipeKost;

    Bundle mBundle;
    String cek;

    RadioButton radioEx, radioReg;
    private static final int PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kost);

        namaKost = findViewById(R.id.kost_name);
        alamatKost = findViewById(R.id.addressKost);

        size = findViewById(R.id.SizeKost);
        sisa = findViewById(R.id.sisa_kamar);

        radioEx = findViewById(R.id.radioEks);
        radioReg = findViewById(R.id.radioReg);

        radioGroup = findViewById(R.id.radioGroupXML);

        btnUnggah = findViewById(R.id.uploadBtn);
        btnGallery = findViewById(R.id.uploadGalery);
        addKost = findViewById(R.id.addKost);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected="galeri";
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(AddKostActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                            PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else{
                        openGallery();
                    }
                }
                else{
                    openGallery();
                }
            }
        });

        btnUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected="kamera";
                System.out.println("APAKAHMASUK SINI");
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {

                    if(AddKostActivity.this.checkSelfPermission(Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_DENIED ||
                            AddKostActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                                    PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else{
                        openCamera();
                    }
                }
                else{
                    openCamera();
                }
            }
        });


        addKost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = namaKost.getText().toString();
                String address = alamatKost.getText().toString();
                String siz = size.getText().toString();
                String sisaKamar = sisa.getText().toString();




                if(name.isEmpty() || address.isEmpty() || siz.isEmpty() || sisaKamar.isEmpty() || (!radioReg.isChecked() && !radioEx.isChecked()))
                {
                    if(name.isEmpty())
                    {
                        namaKost.setError("Masukan nama kost yang valid");
                    }
                    if(address.isEmpty())
                    {
                        alamatKost.setError("Masukan alamat kost yang valid");
                    }
                    if(siz.isEmpty())
                    {
                        size.setError("Masukan luas kost yang valid");
                    }
                    if(sisaKamar.isEmpty())
                    {
                        sisa.setError("Masukan sisa kamar yang valid");
                    }

                    if(!radioEx.isChecked() && !radioReg.isChecked())
                    {
                        Toast.makeText(AddKostActivity.this, "Masukan inputan valid", Toast.LENGTH_SHORT).show();
                    }
                }else {



                    kost = new Kost(emailFB, name, getTipeKost, address, Integer.parseInt(siz), Integer.parseInt(sisaKamar), imageStr);
                    tambahKost(kost);
                    finish();
                }
            }
        });


    }

    public void tambahKost(Kost kostt){
        //Tambahkan tambah buku disini
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menambahkan Data Kost");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, KostAPI.URL_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan status dari response
                    finish();


                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(AddKostActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(AddKostActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                /*
                    Disini adalah proses memasukan/mengirimkan parameter key dengan data value,
                    dan nama key nya harus sesuai dengan parameter key yang diminta oleh jaringan
                    API.
                */
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nama_kost", kostt.getNama_kost());
                params.put("emailPemilik", emailFB);
                params.put("tipe_kost", kostt.getTipe_kost());
                params.put("alamat_kost", kostt.getAlamat_kost());
                params.put("luas_kost", String.valueOf(kostt.getLuas_kost()));
                params.put("sisa_kamar", String.valueOf(kostt.getSisa_kamar()));
                params.put("image", kostt.getImageUrl());

                return params;
            }
        };

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioEks:
                if (checked)
                    getTipeKost = "Eksklusif";
                    break;
            case R.id.radioReg:
                if (checked)
                    getTipeKost = "Reguler";
                    break;
        }
    }

    private void openGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    private void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    if(selected.equals("kamera"))
                        openCamera();
                    else
                        openGallery();
                }else{
                    Toast.makeText(AddKostActivity.this ,"Permision denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1)
        {
            selectedImage = data.getData();
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(selectedImage);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Toast.makeText(AddKostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
//            ivGambar.setImageBitmap(bitmap);
            bitmap = getResizedBitmap(bitmap, 512);
        }
        else if(resultCode == RESULT_OK && requestCode == 2)
        {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
//            ivGambar.setImageBitmap(bitmap);
            bitmap = getResizedBitmap(bitmap, 512);

        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        imageStr = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


}