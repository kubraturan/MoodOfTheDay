package com.example.developer.moodoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GrupOlustur extends AppCompatActivity {

    //Spinner içerisine koyacağımız verileri tanımlıyoruz.
    private String[] iller={"3 Kişilik Gruplar","4 Kişilik Gruplar","5 Kişilik Gruplar","6 Kişilik Gruplar"};
    private String[] modSpinner={"Hareketli Canlı Hissediyorum","Bunalmış Hissediyorum","Sakin,Rahat ve Özgür Hissediyorum","Duygusal Hissediyorum","Umutlu Hissediyorum","Neşeli Hissediyorum","Cesaretli ve Güven Dolu Hissediyorum","Huzurlu Hissediyorum","Hüzünlü ve Özlemiş Hissediyorum","Üzgün ve Mutsuz Hissediyorum","Mutlu Hissediyorum"};
    List<String> liste=new ArrayList<String>();
    DatabaseReference dbref,reference;
    Button kaydet;
    EditText grupAdi,nickname;
    String id;
    FirebaseUser user;

    //Spinner'ları ve Adapter'lerini tanımlıyoruz.
    private Spinner spinnerIller,spinnerModlar;
    private ArrayAdapter<String> dataAdapterForIller,dataAdapterForModSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grup_olustur);

        kaydet=(Button) findViewById(R.id.kaydet);

        dbref= FirebaseDatabase.getInstance().getReference("SohbetGruplari");
        grupAdi=(EditText) findViewById(R.id.grupAdi);
        nickname=(EditText) findViewById(R.id.nickname);
        user= FirebaseAuth.getInstance().getCurrentUser();
        //xml kısmında hazırladığımğız spinnerları burda tanımladıklarımızla eşleştiriyoruz.
        spinnerIller = (Spinner) findViewById(R.id.spinner2);

        //Spinner'lar için adapterleri hazırlıyoruz.
        dataAdapterForIller = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, iller);

        //Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterForIller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        spinnerIller.setAdapter(dataAdapterForIller);

        // Listelerden bir eleman seçildiğinde yapılacakları tanımlıyoruz.
        spinnerIller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                // Toast.makeText(getBaseContext(), ""+spinnerIller.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //TODO:modSpinner
        spinnerModlar = (Spinner) findViewById(R.id.modSpinner);

        //Spinner'lar için adapterleri hazırlıyoruz.
        dataAdapterForModSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, modSpinner);

        //Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterForModSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        spinnerModlar.setAdapter(dataAdapterForModSpinner);

        // Listelerden bir eleman seçildiğinde yapılacakları tanımlıyoruz.
        spinnerModlar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                // Toast.makeText(getBaseContext(), ""+spinnerIller.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


               kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbref.child(spinnerModlar.getSelectedItem().toString()).child(spinnerIller.getSelectedItem().toString()).child(grupAdi.getText().toString()).child(user.getUid()).setValue(nickname.getText().toString());


                dbref.child(spinnerModlar.getSelectedItem().toString()).child(spinnerIller.getSelectedItem().toString()).child(grupAdi.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                            liste.add(postSnapshot.getValue().toString());
                            Log.d("deneme", postSnapshot.getValue().toString());

                        }

                        if (spinnerIller.getSelectedItem().toString() == "3 Kişilik Gruplar") {

                            if (liste.size() <= 3) {
                                dbref.child(spinnerModlar.getSelectedItem().toString()).child(spinnerIller.getSelectedItem().toString()).child(grupAdi.getText().toString()).child(user.getUid().toString()).setValue(nickname.getText().toString());
                                Intent git = new Intent(getApplicationContext(), uckisisohbet.class);
                                git.putExtra("mesaj", "Grupa Hoş Geldin");
                                git.putExtra("kullaniciAdi", nickname.getText().toString());
                                git.putExtra("gelenModSpinner", spinnerModlar.getSelectedItem().toString());
                                git.putExtra("gelenSpinner", spinnerIller.getSelectedItem().toString());
                                git.putExtra("grupAdi", grupAdi.getText().toString());
                                startActivity(git);

                            } else {

                                Toast.makeText(getBaseContext(), "Grup Doludur Başka Grup Deneyiniz", Toast.LENGTH_SHORT).show();
                            }

                            liste.clear();

                        }
                        if (spinnerIller.getSelectedItem().toString() == "4 Kişilik Gruplar") {

                            if (liste.size() <= 4) {
                                dbref.child(spinnerModlar.getSelectedItem().toString()).child(spinnerIller.getSelectedItem().toString()).child(grupAdi.getText().toString()).child(user.getUid().toString()).setValue(nickname.getText().toString());
                                Intent git = new Intent(getApplicationContext(), dortkisisohbet.class);
                                git.putExtra("mesaj", "Grupa Hoş Geldin");
                                git.putExtra("kullaniciAdi", nickname.getText().toString());
                                git.putExtra("gelenModSpinner", spinnerModlar.getSelectedItem().toString());
                                git.putExtra("gelenSpinner", spinnerIller.getSelectedItem().toString());
                                git.putExtra("grupAdi", grupAdi.getText().toString());
                                startActivity(git);

                            } else {

                                Toast.makeText(getBaseContext(), "Grup Doludur Başka Grup Deneyiniz", Toast.LENGTH_SHORT).show();
                            }

                            liste.clear();

                        }
                        if (spinnerIller.getSelectedItem().toString() == "5 Kişilik Gruplar") {

                            if (liste.size() <= 5) {
                                dbref.child(spinnerModlar.getSelectedItem().toString()).child(spinnerIller.getSelectedItem().toString()).child(grupAdi.getText().toString()).child(user.getUid().toString()).setValue(nickname.getText().toString());
                                Intent git = new Intent(getApplicationContext(), beskisisohbet.class);
                                git.putExtra("mesaj", "Grupa Hoş Geldin");
                                git.putExtra("kullaniciAdi", nickname.getText().toString());
                                git.putExtra("gelenModSpinner", spinnerModlar.getSelectedItem().toString());
                                git.putExtra("gelenSpinner", spinnerIller.getSelectedItem().toString());
                                git.putExtra("grupAdi", grupAdi.getText().toString());
                                startActivity(git);

                            } else {

                                Toast.makeText(getBaseContext(), "Grup Doludur Başka Grup Deneyiniz", Toast.LENGTH_SHORT).show();
                            }

                            liste.clear();

                        }
                        if (spinnerIller.getSelectedItem().toString() == "6 Kişilik Gruplar") {

                            if (liste.size() <= 6) {
                                dbref.child(spinnerModlar.getSelectedItem().toString()).child(spinnerIller.getSelectedItem().toString()).child(grupAdi.getText().toString()).child(user.getUid().toString()).setValue(nickname.getText().toString());
                                Intent git = new Intent(getApplicationContext(), altikisisohbet.class);
                                git.putExtra("mesaj", "Grupa Hoş Geldin");
                                git.putExtra("kullaniciAdi", nickname.getText().toString());
                                git.putExtra("gelenModSpinner", spinnerModlar.getSelectedItem().toString());
                                git.putExtra("gelenSpinner", spinnerIller.getSelectedItem().toString());
                                git.putExtra("grupAdi", grupAdi.getText().toString());
                                startActivity(git);

                            } else {

                                Toast.makeText(getBaseContext(), "Grup Doludur Başka Grup Deneyiniz", Toast.LENGTH_SHORT).show();
                            }

                            liste.clear();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

          }
        });


    }
}

