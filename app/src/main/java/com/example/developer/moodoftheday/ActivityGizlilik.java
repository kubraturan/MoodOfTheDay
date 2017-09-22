package com.example.developer.moodoftheday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseUser user;
    Kisiler  al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gizlilik);

       // Intent gelen=getIntent();
        //final   String gelenKisiIdsi=gelen.getExtras().getString("kisiIdsiGönder");
        user= FirebaseAuth.getInstance().getCurrentUser();

        refKisiFoto = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());



        //gizlilik.clear();
        gizlilik.add(new gizlilikClass(false,R.drawable.ic_public_black_24dp, "Herkese Açık"));
        gizlilik.add(new gizlilikClass(false,R.drawable.ic_people_outline_black_24dp, "Arkadaşlar"));
        gizlilik.add(new gizlilikClass(false,R.drawable.ic_person_black_24dp, "Sadece Ben "));



        gizlilikList = (ListView) findViewById(R.id.gizlilikListe);
        gizlilikAdapter adapter = new gizlilikAdapter(ActivityGizlilik.this, gizlilik);
        gizlilikList.setAdapter(adapter);



//        Intent databaseDurum=getIntent();
//        final String aaa=databaseDurum.getExtras().getString("kisiIdsiGönder");
//
//

        gizlilikList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               // Intent don = new Intent(ActivityGizlilik.this, ActivityProfilSayfasi.class);
//                Log.d("deneme","hata");
//               don.putExtra("gizlilik esası", "fake");
                refKisiFoto.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> postValues = new HashMap<String,Object>();
                        Log.d("deneme","hata");

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            postValues.put(postSnapshot.getKey(),postSnapshot.getValue());
                            Log.d("deneme","hata");

                            postValues.put("profilGizlilik",gizlilik.get(position).getDurum());

                            refKisiFoto.updateChildren(postValues);

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
               //startActivity(don);


            }  });
    }}