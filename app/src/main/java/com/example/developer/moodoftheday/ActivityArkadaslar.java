package com.example.developer.moodoftheday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

public class ActivityArkadaslar extends AppCompatActivity {
CheckBox takplesme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadaslar);
        takplesme=(CheckBox) findViewById(R.id.takipEtme);

        Intent aldim=getIntent();
        Boolean takipDurumu=aldim.getExtras().getBoolean("takipEtmeDurumu");
        if(takipDurumu.equals(true)){
            takplesme.isChecked();
        }
    }
}
