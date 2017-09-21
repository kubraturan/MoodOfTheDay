package com.example.developer.moodoftheday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityGizlilik extends AppCompatActivity {
    public static List<gizlilikClass> gizlilik = new ArrayList<gizlilikClass>();
    ListView gizlilikList;
    RadioButton radioButton;
    DatabaseReference refKisiFoto;
    Kisiler  al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gizlilik);

        Intent gelen=getIntent();
        final   String gelenKisiIdsi=gelen.getExtras().getString("kisiIdsiGönder");

        refKisiFoto = FirebaseDatabase.getInstance().getReference("users").child(gelenKisiIdsi);



        //gizlilik.clear();
        gizlilik.add(new gizlilikClass(false,R.drawable.ic_public_black_24dp, "Herkese Açık"));
        gizlilik.add(new gizlilikClass(false,R.drawable.ic_people_outline_black_24dp, "Arkadaşlar"));
        gizlilik.add(new gizlilikClass(false,R.drawable.ic_person_black_24dp, "Sadece Ben "));



        gizlilikList = (ListView) findViewById(R.id.gizlilikListe);
        gizlilikAdapter adapter = new gizlilikAdapter(ActivityGizlilik.this, gizlilik);
        gizlilikList.setAdapter(adapter);






        gizlilikList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent don = new Intent(ActivityGizlilik.this, ActivityProfilSayfasi.class);
                don.putExtra("gizlilik esası", gizlilik.get(position).getImage());
                refKisiFoto.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> postValues = new HashMap<String,Object>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                 postValues.put(postSnapshot.getKey(),postSnapshot.getValue());
                 postValues.put("profilGizlilik",gizlilik.get(position).getDurum());

                            refKisiFoto.updateChildren(postValues);

                            }


                        Toast.makeText(ActivityGizlilik.this , "Profil Gizliliği"+" "+gizlilik.get(position).getDurum().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                startActivity(don);


            }  });
    }}