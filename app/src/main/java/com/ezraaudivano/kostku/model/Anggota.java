package com.ezraaudivano.kostku.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ezraaudivano.kostku.R;

public class Anggota  {

    private static final String TAG = "Anggota";

    public String nama;
    public int npm;
    public int image;
    public String kelas;

    public Anggota (){ }

    public Anggota(String nama, int npm, String kelas, int image) {
        this.nama = nama;
        this.npm = npm;
        this.image = image;
        this.kelas = kelas;
    }

    public static String getTAG() {
        return TAG;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getNpm() {
        return npm;
    }

    public void setNpm(int npm) {
        this.npm = npm;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    @BindingAdapter("showProf")
    public static void showImg (ImageView view, int imageUrl) {
            view.setImageDrawable(view.getContext().getDrawable(imageUrl));
    }
}
