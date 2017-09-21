package com.example.developer.moodoftheday;

import android.graphics.drawable.Drawable;

/**
 * Created by developer on 5.08.2017.
 */

public class ModClass {

    String modAdi;
    String modDurumu;
    int modResmi;

    public ModClass(String modAdi, String modDurumu,int modResmi ) {
        this.modAdi = modAdi;
        this.modResmi = modResmi;
        this.modDurumu = modDurumu;
    }






    public String getModAdi() {
        return modAdi;
    }

    public void setModAdi(String modAdi) {
        this.modAdi = modAdi;
    }

    public int getModResmi() {
        return modResmi;
    }

    public int setModResmi(int modResmi) {
        this.modResmi = modResmi;
        return modResmi;
    }

    public String getModDurumu() {
        return modDurumu;
    }

    public void setModDurumu(String modDurumu) {
        this.modDurumu = modDurumu;
    }
}
