package com.example.developer.moodoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//TODO: kişilerin postunun nerden alınacağını sor
public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public ArrayList<Post> posts = new ArrayList<Post>();
    List<String> arkadaslar=new ArrayList<String>();
    public  MainPageAdapter adapter;
    modumProfil customer;
    DatabaseReference kisiRef,kisiArkadas;
    String alınan;
    List<modumProfil> liste=new ArrayList<modumProfil>();
    DatabaseReference dbref;
    private RecyclerView recycler_view;
    FirebaseUser user;


    int t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        user= FirebaseAuth.getInstance().getCurrentUser();
        alınan=user.getUid();
        kisiRef=FirebaseDatabase.getInstance().getReference("users");
        kisiArkadas=FirebaseDatabase.getInstance().getReference("ArkadasListesi").child(alınan);
        recycler_view = (RecyclerView)findViewById(R.id.RecyclePost);
        arkadaslar.clear();
        liste.clear();

        kisiArkadas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot obj:dataSnapshot.getChildren()){
                    arkadaslar.add(obj.getKey());

                }
                for( int i=0;i<arkadaslar.size();i++){
                    dbref = FirebaseDatabase.getInstance().getReference("kullaniciModlari").child(arkadaslar.get(i).toString());


                    t=i;
                    dbref.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapsho) {
                            for (DataSnapshot postSnapshot : dataSnapsho.getChildren()) {
                                customer = postSnapshot.getValue(modumProfil.class);
                                liste.add(customer);



                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            layoutManager.scrollToPosition(0);
                            profilModAdapterr adapter_items = new profilModAdapterr(arkadaslar.get(t).toString(),liste,getApplicationContext(),new CustomItemClickListener(){
                                @Override
                                public void onItemClick(View v, int position) {

                                }


                            });
                         //   Collections.reverse(liste);

                            recycler_view.setLayoutManager(layoutManager);

                            recycler_view.setHasFixedSize(true);

                            recycler_view.setAdapter(adapter_items);

                            recycler_view.setItemAnimator(new DefaultItemAnimator());


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




/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);

        //  Button takip=(Button) findViewById(R.id.takip);
        final ImageView image=(ImageView)hView.findViewById(R.id.ProfilPhoto);
        final TextView isim=(TextView) hView.findViewById(R.id.Name);
        kisiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Glide.with(getApplicationContext()).load(dataSnapshot.child(alınan).child("kisiResmi").getValue().toString()).into(image);
                isim.setText(dataSnapshot.child(alınan).child("name").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//     takipIstegi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent git=new Intent(getApplicationContext(),ActivityTakip.class);
//                git.putExtra("kisiIdsi",alınan);
//                startActivity(git);
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);




        if (id == R.id.GotoProfile) {
            Intent aa=new Intent(getApplicationContext(),ActivityProfilSayfasi.class);
            aa.putExtra("gelecekOlanKisi",alınan);
            startActivity(aa);

        }  if (id == R.id.Arkadaslar) {
            Intent Git=new Intent(MainPage.this,ActivityArkadasListesi.class);
            Git.putExtra("ara","");
            startActivity(Git);

        }
      if (id == R.id.Message) {
            //TODO: Message PAge added

        }   if (id == R.id.share) {
            //TODO: Anahatlar belli olunca oluşturulacak
            // startActivity(new Intent(getApplicationContext(), ActivityAnaSayfa.class));

        } if (id == R.id.mod) {
            Intent mod=new Intent(getApplicationContext(), ActivityModumSayfasi.class);

            startActivity(mod);

        }  if (id == R.id.ara) {
            Intent ara=new Intent(getApplicationContext(), ActivityAra.class);
            ara.putExtra("ara","");
            startActivity(ara);

        }  if (id == R.id.Cikis) {

            Intent cikis=new Intent(getApplicationContext(),ActivityGirisSayfasi.class);
            startActivity(cikis);


        }

        if (id == R.id.sohbet) {

            Intent sohbetegit=new Intent(getApplicationContext(),sohbetBaslangisSayfasi.class);
            startActivity(sohbetegit);


        }





        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
