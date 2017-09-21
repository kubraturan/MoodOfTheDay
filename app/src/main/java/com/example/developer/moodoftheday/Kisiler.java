package com.example.developer.moodoftheday;

import android.net.Uri;

/**
 * Created by rumeysal on 8/9/17.
 */

public class Kisiler {

    String id;
    String name;
    String kullaniciadi;
    String email;
    String password;
    String profilGizlilik;

    String kisiResmi;



    public Kisiler(String id, String name, String kullaniciadi, String email, String password,String kisiResmi,String profilGizlilik) {
        this.id=id;
        this.name = name;
        this.kisiResmi=kisiResmi;
        this.kullaniciadi = kullaniciadi;
        this.email = email;
        this.password = password;
        this.kisiResmi=kisiResmi;
        this.profilGizlilik=profilGizlilik;

    }


    public Kisiler(String name, String kullaniciadi, String email, String password) {
        this.name = name;
        this.kullaniciadi = kullaniciadi;
        this.email = email;
        this.password = password;
    }

    public Kisiler(String id, String name, String kisiResmi) {
        this.id = id;
        this.name = name;
        this.kisiResmi = kisiResmi;
    }
//
//    public Kisiler(String kisiResmi) {
//        this.kisiResmi = kisiResmi;
//    }


    public Kisiler(String profilGizlilik) {
        this.profilGizlilik = profilGizlilik;
    }

    public String getProfilGizlilik() {
        return profilGizlilik;
    }

    public void setProfilGizlilik(String profilGizlilik) {
        this.profilGizlilik = profilGizlilik;
    }

//            public Kisiler(String name, String yas, String memleket, String  kisiResmi) {
//        this.name = name;
//        this.yas = yas;
//        this.memleket = memleket;
//        this.kisiResmi = kisiResmi;
//    }




    public Kisiler() {
    }


////    public Kisiler(String kisiResmi) {
////
////    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKullaniciadi() {
        return kullaniciadi;
    }

    public void setKullaniciadi(String kullaniciadi) {
        this.kullaniciadi = kullaniciadi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String  getKisiResmi() {
        return kisiResmi;
    }

    public void setKisiResmi(String kisiResmi) {
        this.kisiResmi = kisiResmi;
    }
}
