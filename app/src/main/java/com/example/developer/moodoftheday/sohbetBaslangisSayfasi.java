package com.example.developer.moodoftheday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class sohbetBaslangisSayfasi extends AppCompatActivity {

    Button sohbeteBasla,grupOlustur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sohbet_baslangis_sayfasi);

        sohbeteBasla =(Button) findViewById(R.id.sohbeteBasla);
        grupOlustur =(Button) findViewById(R.id.grupOlustur);


        sohbeteBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent git=new Intent(getApplicationContext(),Gruplar.class);
                startActivity(git);

            }
        });

        grupOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent olustur=new Intent(getApplicationContext(),GrupOlustur.class);
                startActivity(olustur);
            }
        });

    }
}
