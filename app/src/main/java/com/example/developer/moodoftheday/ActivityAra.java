package com.example.developer.moodoftheday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityAra extends AppCompatActivity {
DatabaseReference dbref,ArkadasListesi;
    List<Kisiler> kisi=new ArrayList<Kisiler>();
    List<String> elemanlar=new ArrayList<>();
    ListView araListe;
    Kisiler ara;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ara);
        Log.d("deneme","geldi");
         Intent araFake=getIntent();
         araFake.getExtras().getString("ara");

        Log.d("deneme","geldi");
       // search=(SearchView)findViewById(R.id.araKisileri);
        dbref = FirebaseDatabase.getInstance().getReference("users");
        ArkadasListesi=FirebaseDatabase.getInstance().getReference("ArkadasListesi");
        user = firebaseAuth.getInstance().getCurrentUser();


        Log.d("deneme","geldi");
        dbref.addValueEventListener(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                ara = postSnapshot.getValue(Kisiler.class);
                                                Log.d("deneme","geldi");
                                                ara.getId();
//                                                ara.getName();
//                                                ara.getKisiResmi();
//                                                ara.getProfilGizlilik();
//                                                Log.d("deneme",user.getUid());
//                                                Log.d("deneme",ara.getId());
//                                               if (ara.getId().equals(user.getUid())) {
//
//                                                } else {
//                                                   kisi.add(ara);
//                                                }
//                                                Log.d("deneme","geldi");

                                                kisi.add(ara);
                                            }



                                            Log.d("deneme","geldi");
                araListe = (ListView) findViewById(R.id.araListe);

                araAdapter adapter = new araAdapter(ActivityAra.this, kisi);
                araListe.setAdapter(adapter);
                                            Log.d("deneme","geldi");

                araListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        if(kisi.get(position).getProfilGizlilik().equals("Arkadaşlar")){


                                ArkadasListesi.child(kisi.get(position).getId()).addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        elemanlar.add(postSnapshot.getKey());


                                    }
                                    Log.d("deneme","geldi");
                                        if (elemanlar.contains(user.getUid())) {

                                            Intent git = new Intent(ActivityAra.this, ActivityArkadaslar.class);
                                            git.putExtra("takipEtmeDurumu", true);
                                            startActivity(git);

                                        } else if(!elemanlar.contains(user.getUid())) {
                                            Intent git = new Intent(ActivityAra.this, ActivityArkadasDegil.class);
                                            git.putExtra("profResmi", kisi.get(position).getKisiResmi());
                                            git.putExtra("kisiAdi", kisi.get(position).getName());
                                            git.putExtra("kisiId", kisi.get(position).getId());
                                            git.putExtra("gönderenKisininId", user.getUid());


                                            startActivity(git);

                                        }
                                            else if(elemanlar.isEmpty()){

                                            Intent git = new Intent(ActivityAra.this, ActivityArkadasDegil.class);
                                            git.putExtra("profResmi", kisi.get(position).getKisiResmi());
                                            git.putExtra("kisiAdi", kisi.get(position).getName());
                                            git.putExtra("kisiId", kisi.get(position).getId());
                                            git.putExtra("suandakiKisininId", user.getUid());
                                            startActivity(git);
                                        }
                                    }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                       }

                  else if(kisi.get(position).getProfilGizlilik().equals("Herkese Açık")){

                            ArkadasListesi.child(kisi.get(position).getId()).addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        elemanlar.add(postSnapshot.getKey());


                                    }
                                    if (elemanlar.contains(user.getUid())) {

                                        Intent git = new Intent(ActivityAra.this, ActivityArkadaslar.class);
                                        git.putExtra("takipEtmeDurumu", true);
                                        startActivity(git);

                                    } else if(!elemanlar.contains(user.getUid())) {
                                        Intent git = new Intent(ActivityAra.this, ActivityArkadaslar.class);
                                        git.putExtra("takipEtmeDurumu", false);
                                        startActivity(git);

                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });




                    }


                    else if( kisi.get(position).getProfilGizlilik().equals("Sadece Ben")){}





                    }
                });


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
