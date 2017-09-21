package com.example.developer.moodoftheday;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO: kişilerin postunun nerden alınacağını sor
public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public ArrayList<Post> posts = new ArrayList<Post>();
      List<String> arkadaslar=new ArrayList<String>();
    public  MainPageAdapter adapter;
    DatabaseReference kisiRef,kisiArkadas;
    String keys;
    String alınan;
    List<modumProfil> liste=new ArrayList<modumProfil>();
    ImageView arkadasListesi,profilResmi;
    DatabaseReference dbref,refKisiId;
    private RecyclerView recycler_view;
    FirebaseUser user;
    int t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent alındı = getIntent();
        alınan = alındı.getExtras().getString("gelecekOlanKisi");
        user= FirebaseAuth.getInstance().getCurrentUser();
        kisiRef=FirebaseDatabase.getInstance().getReference("users");
        kisiArkadas=FirebaseDatabase.getInstance().getReference("ArkadasListesi").child(alınan);
        recycler_view = (RecyclerView)findViewById(R.id.RecyclePost);
        arkadaslar.clear();
        liste.clear();

       // Log.d("deneme", String.valueOf(kisiArkadas));
        //Log.d("deneme", String.valueOf(kisiArkadas.child(user.getUid())));
        //Log.d("deneme", String.valueOf(kisiArkadas.child(user.getUid()).child()));
        kisiArkadas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot obj:dataSnapshot.getChildren()){
                    arkadaslar.add(obj.getKey());


                }
                Toast.makeText(MainPage.this, ""+arkadaslar.size(), Toast.LENGTH_SHORT).show();

              for( int i=0;i<arkadaslar.size();i++){
                    dbref = FirebaseDatabase.getInstance().getReference("kullaniciModlari").child(arkadaslar.get(i));
                  t=i;
                    dbref.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapsho) {
                            for (DataSnapshot postSnapshot : dataSnapsho.getChildren()) {
                                modumProfil customer = postSnapshot.getValue(modumProfil.class);
                                liste.add(customer);
                                Collections.reverse(liste);
                            }




                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            layoutManager.scrollToPosition(0);
                            profilModAdapterr adapter_items = new profilModAdapterr(liste,getApplicationContext(),new CustomItemClickListener(){
                                @Override
                                public void onItemClick(View v, int position) {

                                }


                            },arkadaslar.get(t));
                            Collections.reverse(liste);

                            recycler_view.setLayoutManager(layoutManager);

                            recycler_view.setHasFixedSize(true);

                            recycler_view.setAdapter(adapter_items);

                            recycler_view.setItemAnimator(new DefaultItemAnimator());


//
//                durumListesi = (ListView) findViewById(R.id.profildekiModlar);
//                profilModAdapterr adapter = new profilModAdapterr(ActivityProfilSayfasi.this, liste);
//                durumListesi.setAdapter(adapter);
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

                    image.setImageResource(Integer.valueOf(dataSnapshot.child(alınan).child("kisiResmi").getValue().toString()));
                    isim.setText(dataSnapshot.child(alınan).child("name").getValue().toString());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

  /*   takip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent git=new Intent(getApplicationContext(),ActivityTakip.class);
                git.putExtra("kisiIdsi",alınan);
                startActivity(git);

            }
        });*/
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
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

        }  if (id == R.id.Message) {
            //TODO: Message PAge added

        }   if (id == R.id.share) {
            //TODO: Anahatlar belli olunca oluşturulacak
           // startActivity(new Intent(getApplicationContext(), ActivityAnaSayfa.class));

        } if (id == R.id.nav_send) {
           // startActivity(new Intent(getApplicationContext(), ActivityModumSayfasi.class));

        }  if (id == R.id.ara) {
            Intent ara=new Intent(getApplicationContext(), ActivityAra.class);
            ara.putExtra("ara","");
            startActivity(ara);

    }



        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
