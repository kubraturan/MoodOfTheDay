package com.example.developer.moodoftheday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class profilResmi extends AppCompatActivity {
    ImageView profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_resmi);
        Intent fakeRes = getIntent();
        final String fakeRess = fakeRes.getExtras().getString("fakeRes");
        profil = (ImageView) findViewById(R.id.prof);
        Glide.with(getApplicationContext()).load(fakeRess).into(profil);
    }
}
