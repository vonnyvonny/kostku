package com.ezraaudivano.kostku.model;

import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.adapter.AnggotaAdapter;

import java.util.ArrayList;

public class DaftarAnggota {
    public ArrayList<Anggota> ANGGOTA;

    public DaftarAnggota(){
        ANGGOTA = new ArrayList();
        ANGGOTA.add(EZRA);
        ANGGOTA.add(NADILA);
        ANGGOTA.add(ADI);
    }

    public Anggota EZRA = new Anggota("Ezra Audivano", 180709875, "F", R.drawable.ezra_image);
    public Anggota NADILA = new Anggota("Nadila Putri", 180709914, "F", R.drawable.nadila_image);
    public Anggota ADI = new Anggota("Adi Wijaya", 180709928, "F", R.drawable.adi_image);




}
