package com.example.developer.moodoftheday;

import android.content.Context;
import android.net.Uri;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.firebase.database.DatabaseReference;

import java.sql.Time;

/**
 * Created by developer on 9.08.2017.
 */

public class modumProfil {


    String modAdi, paylasilanDurum,saat,tarih,id;
    String resimUrl;
    int profResmi;



    public modumProfil(String modAdi, String paylasilanDurum, String resimUrl, int profResmi, String saat, String tarih) {
        this.modAdi = modAdi;
        this.paylasilanDurum = paylasilanDurum;
        this.resimUrl = resimUrl;
        this.profResmi = profResmi;
        this.saat = saat;
        this.tarih = tarih;
    }

    public modumProfil(String resimUrl) {
        this.resimUrl = resimUrl;
    }

    public modumProfil() {
    }

    public modumProfil(String modAdi, String paylasilanDurum, String saat, String tarih, int profResmi) {
        this.modAdi = modAdi;
        this.paylasilanDurum = paylasilanDurum;
        this.saat = saat;
        this.tarih = tarih;
        this.profResmi = profResmi;
    }
    public modumProfil(String modAdi, String resimUrl, int profResmi, String saat, String tarih) {
        this.modAdi = modAdi;
        this.resimUrl = resimUrl;
        this.profResmi = profResmi;
        this.saat = saat;
        this.tarih = tarih;
    }
    public modumProfil(String modAdi, int profResmi, String saat, String tarih) {
        this.modAdi = modAdi;
        this.profResmi = profResmi;
        this.saat = saat;
        this.tarih = tarih;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getModAdi() {
        return modAdi;
    }

    public void setModAdi(String modAdi) {
        this.modAdi = modAdi;
    }

    public String getPaylasilanDurum() {
        return paylasilanDurum;
    }

    public void setPaylasilanDurum(String paylasilanDurum) {
        this.paylasilanDurum = paylasilanDurum;
    }

    public String getResimUrl() {
        return resimUrl;
    }

    public void setResimUrl(String resimUrl) {
        this.resimUrl = resimUrl;
    }

    public int getProfResmi() {
        return profResmi;
    }

    public void setProfResmi(int profResmi) {
        this.profResmi = profResmi;
    }

    public String  getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }







}



