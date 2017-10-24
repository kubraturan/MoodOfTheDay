package com.example.developer.moodoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityTakip extends AppCompatActivity {
    final List<Kisiler> users = new ArrayList<Kisiler>();
    final List<Kisiler> sirala = new ArrayList<Kisiler>();
    final List<String> isteklerTakip = new ArrayList<String>();
    DatabaseReference alinanKisi,istekler;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip);

       Intent alinanKisiid=getIntent();
       final String alinanKisiidsi=alinanKisiid.getExtras().getString("kisiIdsi");
        istekler= FirebaseDatabase.getInstance().getReference("takipIstekleri").child(alinanKisiidsi);
        alinanKisi= FirebaseDatabase.getInstance().getReference("users");

        istekler.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    isteklerTakip.add(postSnapshot.getKey());
                    Log.d("kbrapost",postSnapshot.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


        }});

        alinanKisi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Kisiler kisi=postSnapshot.getValue(Kisiler.class);
                    users.add(kisi);}


                   for(int i=0;i<isteklerTakip.size();i++) {
                       for (int j = 0; j < users.size(); j++) {
                           if (isteklerTakip.get(i).equals(users.get(j).getId())) {
                               sirala.add(new Kisiler(users.get(j).getId(), users.get(j).getName(), users.get(j).getKisiResmi()));
                           }

                       }
                   }
                listView = (ListView) findViewById(R.id.takipliste);
                TakipAdapter adapter = new TakipAdapter(ActivityTakip.this, sirala);
                listView.setAdapter(adapter);


//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                    @Override
//
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Log.d("kbra","");
//                        if(sirala.get(position).getProfilGizlilik().equals("Arkadaşlar")){
//                            Log.d("kbra","");
//                            Intent git = new Intent(ActivityTakip.this, ActivityArkadasDegil.class);
//                            startActivity(git);
//
//                        }
//                        else if(sirala.get(position).getProfilGizlilik().equals("Herkese Açık")){
//                            Intent git = new Intent(ActivityTakip.this, ActivityArkadaslar.class);
//                            git.putExtra("takipEtmeDurumu", false);
//                            startActivity(git);
//                    }}
//                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
