package com.example.developer.moodoftheday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.tooltip.Tooltip;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityModumSayfasi extends AppCompatActivity {
    ImageView resEkle;
    Button modEkle;
    ImageView paylasilacakResim;
    EditText sonDurum;
    TextView modunDurumu;
    String gelenData;
    Intent intent;
    private static final int fotograf = 1;
    private static final int resim = 2;
    private Uri image;
    Button paylas;
    Bitmap Resim;
    ListView modList;
    ModAdapter adapter;
    DatabaseReference dbref,databaseReference,refKisiBil;
    modumProfil al,users;
    StorageReference storageReference,resimlerReference;
    String id;
    ImageView profileGit;
    int profResmi;
    Calendar mcurrentTime = Calendar.getInstance();
    String tarih,saat;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    //todo:mod seim sayfasındaki modlarımızın aktarılması
    public static List<ModClass> modlar=new ArrayList<ModClass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modum_sayfasi);

        modlar.add(new ModClass("Kırmızı","Hareketli Canlı Hissediyorum",R.drawable.kirmizi));
        modlar.add(new ModClass("Gri","Bunalmış Hissediyorum",R.drawable.gri));
        modlar.add(new ModClass("Mavi","Sakin,Rahat ve Özgür Hissediyorum",R.drawable.mavi));
        modlar.add(new ModClass("Kahverengi","Duygusal Hissediyorum",R.drawable.kahaverengi));
        modlar.add(new ModClass("Mor","Umutlu Hissediyorum",R.drawable.mor));
        modlar.add(new ModClass("Pembe","Neşeli Hissediyorum",R.drawable.pembe));
        modlar.add(new ModClass("Turuncu","Cesaretli ve Güven Dolu Hissediyorum",R.drawable.turuncu));
        modlar.add(new ModClass("Yeşil","Huzurlu Hissediyorum",R.drawable.yesil));
        modlar.add(new ModClass("Sarı","Hüzünlü ve Özlemiş Hissediyorum",R.drawable.sari));
        modlar.add(new ModClass("Siyah","Üzgün ve Mutsuz Hissediyorum",R.drawable.siyah));
        modlar.add(new ModClass("Beyaz","Mutlu Hissediyorum",R.drawable.beyaz));




                //todo:kameradan resim çekerke resim yan dönmesi sorunu ve resim boyutu ayarlaması nasıl olacak
        resEkle = (ImageView) findViewById(R.id.resDurEkle);
        resEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tooltip tooltip = new Tooltip.Builder(v)
                        .setText("Resim Ekle")
                        .setTextColor(Color.BLUE)
                        .setGravity(Gravity.BOTTOM)
                        .setCornerRadius(8f)
                        .setDismissOnClick(true)
                        .show();


                AlertDialog.Builder secimDialog = new AlertDialog.Builder(ActivityModumSayfasi.this);
                secimDialog.setTitle("Resim Çek veya Galeriden Resim Yükle");
                secimDialog.setIcon(R.drawable.fotogalerii); //İkonun projedeki konumu set edelir.


                secimDialog.setPositiveButton("Resim Çek ",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                if (ActivityCompat.checkSelfPermission(ActivityModumSayfasi.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(ActivityModumSayfasi.this, new String[] { android.Manifest.permission.CAMERA }, 101);
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
                Log.d("deneme","burdayim");
            }
        });


        //todo:Image buttona seçtiğimiz modun eklenmesi creatCustomDialog ile
        modEkle=(Button) findViewById(R.id.modEkle);
        modEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



              creatCustomDialog(ActivityModumSayfasi.this);

            }
        });
        //todo:database ekleme işlemlerini yapalım
        modunDurumu = (TextView) findViewById(R.id.modunDurumu);
        sonDurum = (EditText) findViewById(R.id.sonDurum);
        paylasilacakResim=(ImageView) findViewById(R.id.paylasilacakResim) ;
        intent=getIntent();
        dbref= FirebaseDatabase.getInstance().getReference("kullaniciModlari").child(intent.getStringExtra("kisiReference"));
         tarih= String.valueOf(mcurrentTime.get(Calendar.DAY_OF_MONTH))+"."+String.valueOf(mcurrentTime.get(Calendar.MONTH))+"."+String.valueOf(mcurrentTime.get(Calendar.YEAR));
         saat= String.valueOf(mcurrentTime.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf( mcurrentTime.get(Calendar.MINUTE));

        paylas=(Button) findViewById(R.id.paylas);
        paylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                          if(image == null) {

                              id = dbref.push().getKey();
                                al = new modumProfil(modunDurumu.getText().toString(), sonDurum.getText().toString(),saat,tarih, profResmi);
                                //  paylasilacakResim.setVisibility(View.INVISIBLE);

                                dbref.child(id).setValue(al);

                                Toast.makeText(ActivityModumSayfasi.this, "Upload Done", Toast.LENGTH_LONG).show();}

                                else if(sonDurum.getText().toString()==null){
                                    storageReference= FirebaseStorage.getInstance().getReference("resimler");
                                    final StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = storageReference.child(image.getLastPathSegment()).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            @SuppressWarnings("VisibleForTests") String alll = taskSnapshot.getDownloadUrl().toString();

                                            id = dbref.push().getKey();
                                            al = new modumProfil(modunDurumu.getText().toString(), alll, profResmi, saat, tarih);
                                            //  paylasilacakResim.setVisibility(View.INVISIBLE);

                                            dbref.child(id).setValue(al);

                                            Toast.makeText(ActivityModumSayfasi.this, "Upload Done", Toast.LENGTH_LONG).show();

                                        }});}
                                else if(image==null && sonDurum.getText().toString()==null){



                                    id = dbref.push().getKey();
                                    al = new modumProfil(modunDurumu.getText().toString(), profResmi, saat, tarih);
                                    //  paylasilacakResim.setVisibility(View.INVISIBLE);

                                    dbref.child(id).setValue(al);

                                    Toast.makeText(ActivityModumSayfasi.this, "Upload Done", Toast.LENGTH_LONG).show();


                                }

                                else {
                                    storageReference= FirebaseStorage.getInstance().getReference("resimler");
                                    final StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = storageReference.child(image.getLastPathSegment()).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            @SuppressWarnings("VisibleForTests") String alll = taskSnapshot.getDownloadUrl().toString();

                                            id = dbref.push().getKey();
                                            al = new modumProfil(modunDurumu.getText().toString(), sonDurum.getText().toString(), alll, profResmi,saat,tarih);
                                            dbref.child(id).setValue(al);

                                            Toast.makeText(ActivityModumSayfasi.this, "Upload Done", Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }



                }

        }); }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resim && resultCode == RESULT_OK ) {
            image = data.getData();
            resEkle.setImageURI(image);


        } else if (requestCode == fotograf && resultCode == RESULT_OK) {
            Log.d("aaaa", String.valueOf(requestCode));
            image = data.getData();
            resEkle.setImageURI(image);
        }

    }
    public void creatCustomDialog(Context context){
        View dialogView = View.inflate(context,R.layout.modlarim, null);
        modList= (ListView) dialogView.findViewById(R.id.ListlerimMod);
        adapter = new ModAdapter(context, R.layout.mod_adapter, modlar);
        modList.setAdapter(adapter);
        final AlertDialog.Builder builder=new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(dialogView);


        final  AlertDialog dialog=builder.create();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

         builder.create().show();
        modList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.hide();
                ((ImageView) findViewById(R.id.modGosterge)).setImageResource(modlar.get(i).getModResmi());
                modunDurumu.setText((modlar.get(i).getModAdi())+":"+modlar.get(i).getModDurumu());
                profResmi =  modlar.get(i).setModResmi(modlar.get(i).getModResmi());
            }
        });
        dialog.show();


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



}
