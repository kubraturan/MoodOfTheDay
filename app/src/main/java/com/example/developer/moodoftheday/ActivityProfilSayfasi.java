package com.example.developer.moodoftheday;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityProfilSayfasi extends AppCompatActivity {
    static ArrayList <String> keys=new ArrayList<>();
    ImageButton menu;
    FloatingActionButton mod;
    ModAdapter adapter;
    private static final int fotograf = 1;
    private static final int resim = 2;
    private Uri imageUri;
    DatabaseReference dbref,refKisiFoto,dbrefArkadas,refGizlilik;
    ListView durumListesi;
    List<modumProfil> liste=new ArrayList<modumProfil>();
    List<String> resLİst=new ArrayList<>();

    Kisiler kisi=new Kisiler();
    Menu menumuz;
    StorageReference storageReference,profResIcin;

    ImageView profilResmi,mainPage,arkListe;

    FloatingActionButton gizlilikAyarlari;
    String currentPhotoPath;
    private RecyclerView recycler_view,resimOlmazsaRecyler;
    TextView isim,memleket,yas;
    FrameLayout barProfile;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_sayfasi);


        user= FirebaseAuth.getInstance().getCurrentUser();

        dbref = FirebaseDatabase.getInstance().getReference("kullaniciModlari").child(user.getUid());
        refKisiFoto = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        refGizlilik = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("profilGizlilik");
        profilResmi = (ImageView) findViewById(R.id.profilResmi);
        mainPage=(ImageView) findViewById(R.id.mainpage);

        isim=(TextView) findViewById(R.id.AdSoyad) ;
        memleket=(TextView) findViewById(R.id.memleket );
        yas=(TextView) findViewById(R.id.yas);
        arkListe=(ImageView) findViewById(R.id.arkadaslar);
        barProfile = (FrameLayout) findViewById(R.id.bar_profile);




        arkListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Git=new Intent(ActivityProfilSayfasi.this,ActivityArkadasListesi.class);
                startActivity(Git);

            }
        });




        refKisiFoto.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                profilResmi.setImageResource(Integer.valueOf(dataSnapshot.child("kisiResmi").getValue().toString()));
                isim.setText(dataSnapshot.child("name").getValue().toString());
                memleket.setText(" ");
                yas.setText(" ");

//                if(dataSnapshot.child("memleket").equals(null))
//                {
//                    memleket.setText(" ");
//                }
//                else{
//                    memleket.setText(dataSnapshot.child("memleket").getValue().toString());
//                }
//
//
//                if(dataSnapshot.child("dogumTarihi").equals(null))
//                {
//                    yas.setText(" ");
//                }
//                else{
//                    yas.setText(dataSnapshot.child("dogumTarihi").getValue().toString());
//                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        mainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityProfilSayfasi.this,MainPage.class);
                startActivity(intent);
            }
        });


        profilResmi.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                AlertDialog.Builder secimDialog = new AlertDialog.Builder(ActivityProfilSayfasi.this);
                secimDialog.setTitle("Profil Resmini Görüntüle veya Profil Resmini Değiştir");
                secimDialog.setIcon(R.drawable.fotogalerii); //İkonun projedeki konumu set edelir.


                secimDialog.setPositiveButton("Profil Resmini Görüntüle ",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                Intent profResm = new Intent(getApplicationContext(),profilResmi.class);
                                profResm.putExtra("fakeRes","");
                                startActivity(profResm);
                            }
                        });

                                secimDialog.setNegativeButton("Profil Resmini Değiştir",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                AlertDialog.Builder secimDialog = new AlertDialog.Builder(ActivityProfilSayfasi.this);
                                secimDialog.setTitle("Resim Çek veya Galeriden Resim Yükle");
                                secimDialog.setIcon(R.drawable.fotogalerii); //İkonun projedeki konumu set edelir.


                                secimDialog.setPositiveButton("Resim Çek ",
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                if (ActivityCompat.checkSelfPermission(ActivityProfilSayfasi.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(ActivityProfilSayfasi.this, new String[] { android.Manifest.permission.CAMERA }, 101);
                                                    return;
                                                }
                                                dispatchTakePictureIntent();
                                            }
                                        });

                                secimDialog.setNegativeButton("Galeriden Resim Yükle",
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent galeri = new Intent(Intent.ACTION_GET_CONTENT);
                                                galeri.setType("image/*");
                                                startActivityForResult(galeri, fotograf);
                                            }
                                        });

                                secimDialog.show();
                            }
                        });

                secimDialog.show();
            }
        });


        mod = (FloatingActionButton) findViewById(R.id.Mood);

        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder modDialog = new AlertDialog.Builder(ActivityProfilSayfasi.this);
                modDialog.setTitle("Modun ne olsun istersin ?");
                modDialog.setIcon(R.drawable.fotogalerii); //İkonun projedeki konumu set edelir.
                modDialog.setPositiveButton("Modum Değişti ",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), ActivityModumSayfasi.class);
                                intent.putExtra("kisiReference", user.getUid());
                                startActivity(intent);
                                finish();
                            }
                        });

                modDialog.setNegativeButton("Modumu Gör",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                               /* Intent ac = new Intent(profilSayfasi.this,ModumSayfasi.class);
                                galeri.setType("image/*");
                                startActivityForResult(galeri, fotograf);*/
                            }
                        });

                modDialog.show();
            }
        });

//        Intent alinanGizlilikResmi=getIntent();
//        final int alinanGizlilik=alinanGizlilikResmi.getExtras().getInt("gizlilik esası");

        dbref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    modumProfil customer = postSnapshot.getValue(modumProfil.class);
                    liste.add(customer);
                    Collections.reverse(liste);
                    Toast.makeText(getApplicationContext(),customer.getModAdi(),Toast.LENGTH_LONG).show();
                }


//if(liste.size()==0){
//    barProfile.setBackgroundResource(R.drawable.ovalbeyaz);
//
//
//}
//
//                if( liste.get(0).getModAdi().equals("Kırmızı:Hareketli Canlı Hissediyorum")) {
//                    barProfile.setBackgroundResource(R.drawable.ovalkirmizi);
//
//
//                }
//               else if( liste.get(0).getModAdi().equals("Gri:Bunalmış Hissediyorum"))
//                {
//                    barProfile.setBackgroundResource(R.drawable.ovalgri);}
//
//             else   if( liste.get(0).getModAdi().equals("Mavi:Sakin,Rahat ve Özgür Hissediyorum"))
//                {
//                    barProfile.setBackgroundResource(R.drawable.ovalmavi);}
//                else   if( liste.get(0).getModAdi().equals("Kahverengi:Duygusal Hissediyorum"))
//                {
//                    barProfile.setBackgroundResource(R.drawable.ovalkahverengi);}
//                else   if( liste.get(0).getModAdi().equals("Mor:Umutlu Hissediyorum"))
//                {
//                    barProfile.setBackgroundResource(R.drawable.ovalmor);}
//                else   if( liste.get(0).getModAdi().equals("Pembe:Neşeli Hissediyorum"))
//                {
//                    barProfile.setBackgroundResource(R.drawable.ovalpembe);}
//                else   if( liste.get(0).getModAdi().equals("Turuncu:Cesaretli ve Güven Dolu Hissediyorum"))
//                {
//                    barProfile.setBackgroundResource(R.drawable.ovalturuncu);}
//                else   if( liste.get(0).getModAdi().equals("Yeşil:Huzurlu Hissediyorum"))
//                {
//                    barProfile.setBackgroundResource(R.drawable.ovalyesil);}
//                else   if( liste.get(0).getModAdi().equals("Sarı:Hüzünlü ve Özlemiş Hissediyorum"))
//                {
//                    barProfile.setBackgroundResource(R.drawable.ovalsari);}
//                else   if( liste.get(0).getModAdi().equals("Siyah:Üzgün ve Mutsuz Hissediyorum"))
//                {
//                    barProfile.setBackgroundResource(R.drawable.ovalsiyah);}
//                else
//                {
//
//
//
//                    barProfile.setBackgroundResource(R.drawable.ovalbeyaz);}
//


                recycler_view = (RecyclerView)findViewById(R.id.profildekiModlar);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                layoutManager.scrollToPosition(0);


                profilModAdapterr adapter_items = new profilModAdapterr(dbref.getKey(),liste,getApplicationContext(),new CustomItemClickListener(){




                @Override
                public void onItemClick(View v, int position) {

                }
            });
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




        gizlilikAyarlari = (FloatingActionButton) findViewById(R.id.gizlilikAyarlari);
        gizlilikAyarlari.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                android.widget.PopupMenu popup = new android.widget.PopupMenu(ActivityProfilSayfasi.this, gizlilikAyarlari);
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(final MenuItem item) {
                        refGizlilik.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                List<Kisiler> post = new ArrayList<Kisiler>();

                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Kisiler gel=postSnapshot.getValue(Kisiler.class);
                                    gel.getProfilGizlilik();
                                    Log.d("heyyo", gel.getProfilGizlilik());
                                    // postValues.add(postSnapshot.getValue().toString());
                                    post.add(gel);
                                    Log.d("heyyo", gel.getProfilGizlilik());

                                 if(postSnapshot.getValue().toString().equals("Herkese Açık")){
                                     switch (item.getItemId()) {
                                         case R.id.herkes: {
                                             Log.d("deneme", String.valueOf(item.getItemId()));

                                             item.setChecked(true);
                                         }

                                 }


                                }
                                 else   if(postSnapshot.getValue().equals("Arkadaslar")){
                                        switch (item.getItemId()) {
                                            case R.id.arkadaslar: {

                                                item.setChecked(true);
                                            }

                                        }


                                    }
                                 else   if(postSnapshot.getValue().equals("Sadece Ben")){
                                        switch (item.getItemId()) {
                                            case R.id.sadeceben: {

                                                item.setChecked(true);
                                            }

                                        }


                                    }


                            } }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });












                        switch (item.getItemId()) {
                            case R.id.herkes:
                            {

                              //  Intent git=new Intent(ActivityProfilSayfasi.this,ActivityGizlilik.class);
                                //   git.putExtra("kisiIdsiGönder","fake");
                               // startActivity(git);
                                refKisiFoto.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Map<String, Object> postValues = new HashMap<String,Object>();

                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            postValues.put(postSnapshot.getKey(),postSnapshot.getValue());

                                            postValues.put("profilGizlilik","Herkese Açık");

                                            refKisiFoto.updateChildren(postValues);

                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                               break;
                            case R.id.arkadaslar:
                            {

                                refKisiFoto.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Map<String, Object> postValues = new HashMap<String,Object>();
                                        Log.d("deneme","hata");

                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            postValues.put(postSnapshot.getKey(),postSnapshot.getValue());
                                            Log.d("deneme","hata");

                                            postValues.put("profilGizlilik","Arkadaşlar");

                                            refKisiFoto.updateChildren(postValues);

                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }
                            break;
                            case R.id.sadeceben:
                            {

                                refKisiFoto.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Map<String, Object> postValues = new HashMap<String,Object>();

                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            postValues.put(postSnapshot.getKey(),postSnapshot.getValue());

                                            postValues.put("profilGizlilik","Sadece Ben");

                                            refKisiFoto.updateChildren(postValues);

                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                        }
                       return true;
                    }
                });

                popup.show();


            }
        });}

     //  gizlilikAyarlari.setBackgroundResource(alinanGizlilik);







    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101: {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "İzin reddedildi!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, resim);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        SharedPreferences shared = getSharedPreferences("myprefs", MODE_PRIVATE);
        shared.edit().putString("image_path", image.getAbsolutePath()).apply();
        return image;
    }


    String id;
    Kisiler res;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resim && resultCode == RESULT_OK) {

            SharedPreferences shared = getSharedPreferences("myprefs", MODE_PRIVATE);
            File f = new File(shared.getString("image_path", null));
            imageUri = Uri.fromFile(f);
            //profilresmi.setImageURI(imageUri);
            storageReference= FirebaseStorage.getInstance().getReference("profilResmi");
            storageReference.child(imageUri.getLastPathSegment()).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

              //      String alll = taskSnapshot.getDownloadUrl().toString();
                    //id = refKisiFoto.push().getKey();
                    //res = new Kisiler(alll);
                    //  paylasilacakResim.setVisibility(View.INVISIBLE);

              //      Glide.with(getApplicationContext()).load(alll).thumbnail(0.7f).into(profilresmi);
               //     refKisiFoto.child("kisiResmi").setValue(alll);
                    Toast.makeText(ActivityProfilSayfasi.this, "Upload Done", Toast.LENGTH_LONG).show();

                }});



        } else if (requestCode == fotograf && resultCode == RESULT_OK) {

            imageUri = data.getData();
            profilResmi.setImageURI(imageUri);

            storageReference= FirebaseStorage.getInstance().getReference("profilResmi");
            final StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = storageReference.child(imageUri.getLastPathSegment()).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") String alll = taskSnapshot.getDownloadUrl().toString();

                    id = refKisiFoto.push().getKey();
                    res = new Kisiler(alll);
                    //  paylasilacakResim.setVisibility(View.INVISIBLE);

                    refKisiFoto.child(id).setValue(res);

                    Toast.makeText(ActivityProfilSayfasi.this, "Upload Done", Toast.LENGTH_LONG).show();

                }});



        }

    }


//    private void düzenle(modumProfil modum) {
//             Intent git=new Intent(getApplicationContext(),ActivityModumSayfasi.class);
//              startActivity(git);
//    }







}
