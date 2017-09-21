package com.example.developer.moodoftheday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityArkadasListesi extends AppCompatActivity {
DatabaseReference arkadaslar;
    List<Kisiler> arkadas=new ArrayList<Kisiler>();
    ListView arkadasList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadas_listesi);
        Intent gelecek=getIntent();
        final String gelecekKisi=   gelecek.getExtras().getString("kisiIdsi");
        arkadaslar= FirebaseDatabase.getInstance().getReference("ArkadasListesi").child(gelecekKisi);

        arkadaslar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Kisiler ark = postSnapshot.getValue(Kisiler.class);
                    arkadas.add(ark);
                }

                arkadasList = (ListView) findViewById(R.id.arkadasListesi);
                araAdapter adapter = new araAdapter(ActivityArkadasListesi.this, arkadas);
                arkadasList.setAdapter(adapter);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
