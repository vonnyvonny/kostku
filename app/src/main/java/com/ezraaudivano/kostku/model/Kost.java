package com.ezraaudivano.kostku.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ezraaudivano.kostku.R;

import java.io.Serializable;
import java.util.List;

@Entity
public class Kost extends BaseObservable {

    private String id, email_pemilik, nama_kost, tipe_kost, alamat_kost, imageUrl;
    private int luas_kost, sisa_kamar;

    public Kost(String id, String email_pemilik, String nama_kost, String tipe_kost, String alamat_kost, int luas_kost, int sisa_kamar, String imageUrl) {
        this.id = id;
        this.email_pemilik = email_pemilik;
        this.nama_kost = nama_kost;
        this.tipe_kost = tipe_kost;
        this.alamat_kost = alamat_kost;
        this.luas_kost = luas_kost;
        this.sisa_kamar = sisa_kamar;
        this.imageUrl = imageUrl;
    }

    public Kost(String email_pemilik, String nama_kost, String tipe_kost, String alamat_kost, int luas_kost, int sisa_kamar, String imageUrl) {
        this.email_pemilik = email_pemilik;
        this.nama_kost = nama_kost;
        this.tipe_kost = tipe_kost;
        this.alamat_kost = alamat_kost;
        this.luas_kost = luas_kost;
        this.sisa_kamar = sisa_kamar;
        this.imageUrl = imageUrl;
    }

    public Kost() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail_pemilik() {
        return email_pemilik;
    }

    public void setEmail_pemilik(String email_pemilik) {
        this.email_pemilik = email_pemilik;
    }

    public String getNama_kost() {
        return nama_kost;
    }

    public void setNama_kost(String nama_kost) {
        this.nama_kost = nama_kost;
    }

    public String getTipe_kost() {
        return tipe_kost;
    }

    public void setTipe_kost(String tipe_kost) {
        this.tipe_kost = tipe_kost;
    }

    public String getAlamat_kost() {
        return alamat_kost;
    }

    public void setAlamat_kost(String alamat_kost) {
        this.alamat_kost = alamat_kost;
    }

    public int getLuas_kost() {
        return luas_kost;
    }

    public void setLuas_kost(int luas_kost) {
        this.luas_kost = luas_kost;
    }

    public int getSisa_kamar() {
        return sisa_kamar;
    }

    public void setSisa_kamar(int sisa_kamar) {
        this.sisa_kamar = sisa_kamar;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @BindingAdapter("showImg")
    public static void showImg (ImageView view, String imageUrl) {

        if(imageUrl != "null"){
            System.out.println("di glide "+imageUrl);
            Glide.with(view.getContext())
                    .load("https://kostkudb.ezraaudivano.com/public/pictures/"+imageUrl)
                    .apply(new RequestOptions().override(0,172))
                    .into(view);

        }else{
            view.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_broken_image_24));
        }

    }


}
