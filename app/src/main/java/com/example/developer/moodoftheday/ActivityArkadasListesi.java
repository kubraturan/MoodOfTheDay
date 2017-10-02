package com.example.developer.moodoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class ActivityArkadasListesi extends AppCompatActivity {
DatabaseReference arkadaslar,arkadasBilCek;
    List<String> arkadas=new ArrayList<>();
    List<Kisiler> bilCek=new ArrayList<>();
    ListView arkadasList;
    FirebaseUser user;
    private araAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadas_listesi);
        Log.d("deneme","Burda");

        user= FirebaseAuth.getInstance().getCurrentUser();
        Log.d("deneme","Burda");
        Intent gelenFake=getIntent();
        gelenFake.getStringExtra("ara");
//        Intent gelecek=getIntent();
//        final String gelecekKisi=   gelecek.getExtras().getString("kisiIdsi");
        arkadasList = (ListView) findViewById(R.id.arkadasListesi);

        adapter = new araAdapter(this, bilCek);
        arkadasList.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("ArkadasListesi").child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Log.d("deneme","Burda");
                            arkadas.add(postSnapshot.getKey().toString());
                            Log.d("deneme",postSnapshot.getKey().toString());
                        }
                        for(int i=0;i<arkadas.size();i++){
                            FirebaseDatabase.getInstance().getReference("users").child(String.valueOf(arkadas.get(i))).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Kisiler ks = dataSnapshot.getValue(Kisiler.class);
                                    if (ks != null)
                                        bilCek.add(ks);
                                    adapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
