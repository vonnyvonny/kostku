package com.ezraaudivano.kostku.model;

import java.io.Serializable;

public class Pengumuman implements Serializable {
    private int id;
    private String judul, subjudul, konten;

    public Pengumuman(int id, String judul, String subjudul, String konten) {
        this.id = id;
        this.judul = judul;
        this.subjudul = subjudul;
        this.konten = konten;
    }

    public Pengumuman(String judul, String subjudul, String konten) {
        this.judul = judul;
        this.subjudul = subjudul;
        this.konten = konten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getSubjudul() {
        return subjudul;
    }

    public void setSubjudul(String subjudul) {
        this.subjudul = subjudul;
    }

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }
}
