package com.example.developer.moodoftheday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityArkadasDegil extends AppCompatActivity {
Button takipEt;
    ImageButton proffRes;
    TextView kisiİsmi;
    Button takipİstegi;
    DatabaseReference takipistegi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadas_degil);
        proffRes=(ImageButton) findViewById(R.id.proffRes) ;
        kisiİsmi=(TextView) findViewById(R.id.kisiAdi);
        takipEt=(Button) findViewById(R.id.takipEt);
        takipistegi= FirebaseDatabase.getInstance().getReference("takipIstekleri");

        Intent gelen=getIntent();
        final String prof=  gelen.getExtras().getString("profResmi");
        final String ad=gelen.getExtras().getString("kisiAdi");
        Glide.with(this).load(prof).into(proffRes);
        final String id=gelen.getExtras().getString("kisiId");
        final String suandakiKisininidsi=gelen.getExtras().getString("gönderenKisininId");
        kisiİsmi.setText(ad);

        takipistegi.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.getKey().equals(suandakiKisininidsi)){

                        takipİstegi.setText("İstek Gönderildi");

                    }


                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        takipİstegi=(Button) findViewById(R.id.takipEt);

        takipİstegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takipistegi.child(id).child(suandakiKisininidsi).setValue("İstek Gönderdi");

                            }
        });



    }
}
