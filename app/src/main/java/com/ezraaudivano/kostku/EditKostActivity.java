package com.ezraaudivano.kostku;

import android.Manifest;
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
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ezraaudivano.kostku.API.KostAPI;
import com.ezraaudivano.kostku.model.Kost;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


import static com.android.volley.Request.Method.PUT;
import static com.android.volley.Request.Method.GET;

public class EditKostActivity extends AppCompatActivity {
    private Button button, editKostBtn, btnEdit, btnUnggah, btnGallery;;
    private TextInputEditText namaKost, alamatKost, size, sisa;

    private Kost kost = new Kost();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String emailFB = user.getEmail();
    String getTipeKost = "", imageStr = "", selected;;
    String idTemp;
    RadioButton radioEk, radioReg;

    int cek=0;
    private Bitmap bitmap;
    private Uri selectedImage = null;

    private static final int PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kost);

        namaKost = findViewById(R.id.kost_name);
        alamatKost = findViewById(R.id.addressKost);
        size = findViewById(R.id.SizeKost);
        sisa = findViewById(R.id.sisa_kamar);
        radioEk = findViewById(R.id.radioEks);
        radioReg = findViewById(R.id.radioReg);

        btnEdit = findViewById(R.id.editKostBtn);

        editKostBtn = findViewById(R.id.editKostBtn);

        btnUnggah = findViewById(R.id.uploadBtn);
        btnGallery = findViewById(R.id.uploadGalery);

        getBundle();
        getDataKostbyId();

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected="galeri";
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(EditKostActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
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
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {

                    if(EditKostActivity.this.checkSelfPermission(Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_DENIED ||
                            EditKostActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
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



        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = namaKost.getText().toString();
                String address = alamatKost.getText().toString();
                String siz = size.getText().toString();
                String sisaKamar = sisa.getText().toString();

                 if(radioEk.isChecked() )
                {
                    getTipeKost = "Eksklusif";
                }else if (radioReg.isChecked())
                 {
                     getTipeKost = "Reguler";
                 }


                System.out.println("tipe kost 1 : "+getTipeKost);
                kost = new Kost(emailFB, name, getTipeKost, address, Integer.parseInt(siz), Integer.parseInt(sisaKamar), imageStr);
                editKost(kost);
                finish();
            }
        });


    }

    public void editKost(Kost kostt) {
        //Tambahkan edit buku disini

        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Mengubah Data Kost");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest  stringRequest = new StringRequest(PUT, KostAPI.URL_UPDATE + idTemp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);

                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(EditKostActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(EditKostActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("tipe_kost", kostt.getTipe_kost());
                params.put("alamat_kost", kostt.getAlamat_kost());
                params.put("luas_kost", String.valueOf(kostt.getLuas_kost()));
                params.put("sisa_kamar", String.valueOf(kostt.getSisa_kamar()));
                if(cek==1) {
                    params.put("image", kostt.getImageUrl());
                }else {
                    params.put("image", "kosong");
                }

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

    public void getBundle (){
        //get Bundle from activity
        idTemp = getIntent().getStringExtra("id");
    }

    public void getDataKostbyId () {
        //Tambahkan tampil buku disini
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(this);

        //Meminta tanggapan string dari URL yang telah disediakan menggunakan method GET
        //untuk request ini tidak memerlukan parameter
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data Buku");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, KostAPI.URL_SELECT_BY_ID + idTemp
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();

                //Mengubah data jsonArray tertentu menjadi json Object
                JSONObject jsonObject = response.optJSONObject("data");

                String id           = jsonObject.optString("id");
                String nama_kost           = jsonObject.optString("nama_kost");
                String tipe_kost        = jsonObject.optString("tipe_kost");
                String alamat_kost        = jsonObject.optString("alamat_kost");
                String emailPemilik        = jsonObject.optString("emailPemilik");
                String luas_kost            = jsonObject.optString("luas_kost");
                String sisa_kamar            = jsonObject.optString("sisa_kamar");
                System.out.println("ADLAAH ASD"+nama_kost);

                if(tipe_kost.equalsIgnoreCase("Eksklusif"))
                {
                    radioEk.setChecked(true);
                }else{
                    radioReg.setChecked(true);
                }

                //Membuat objek user
                kost = new Kost(id, emailFB, nama_kost, tipe_kost, alamat_kost, Integer.parseInt(luas_kost), Integer.parseInt(sisa_kamar), imageStr);

                namaKost.setText(kost.getNama_kost());
                System.out.println("ADLAAH ASD"+nama_kost);
                alamatKost.setText(kost.getAlamat_kost());
                size.setText(String.valueOf(kost.getLuas_kost()));
                sisa.setText(String.valueOf(kost.getSisa_kamar()));

                Toast.makeText(EditKostActivity.this, response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(EditKostActivity.this, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
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
                    Toast.makeText(EditKostActivity.this ,"Permision denied",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditKostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        cek=1;
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
