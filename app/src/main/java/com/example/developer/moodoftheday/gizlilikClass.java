package com.example.developer.moodoftheday;

/**
 * Created by developer on 14.08.2017.
 */

public class gizlilikClass {
    boolean radioButton;
    int image;
    String durum;


    public gizlilikClass() {
    }

    public gizlilikClass(boolean radioButton, int image, String durum) {
        this.radioButton = radioButton;
        this.image = image;
        this.durum = durum;
    }

    public gizlilikClass(int image, String durum) {
        this.image = image;
        this.durum = durum;
    }

    public gizlilikClass(String durum) {
        this.durum = durum;
    }

    public boolean isRadioButton() {
        return radioButton;
    }

    public void setRadioButton(boolean radioButton) {
        this.radioButton = radioButton;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
