package com.example.developer.moodoftheday;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityGirisSayfasi extends AppCompatActivity {

    FirebaseAuth mAuth;//Kimlik doğrulama (login olma) işlemleri için
    FirebaseAuth.AuthStateListener mAuthListener;
    EditText sifre, kullaniciAdi;
    TextView sifremiunuttum;
    Button giris;
    //  ActivityProfilSayfasi gelecekolankisi=new ActivityProfilSayfasi();
    public DatabaseReference dbreference;
    Kisiler kisi = new Kisiler();
    List<Kisiler> kisiler = new ArrayList<Kisiler>();

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent fake=new Intent(ActivityGirisSayfasi.this,MainPage.class);
            fake.putExtra("gelecekOlanKisi", user.getUid());
            startActivity(fake);
            finish();
    }

}

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_giris_sayfasi);

            dbreference= FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();// getInstance() metoduyla da bu sınıfın referans olduğu nesneleri kullanabilmekteyiz.
      mAuthListener = new FirebaseAuth.AuthStateListener() {//login olup olmadığımızı sürekli dinleyecek
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser  user = firebaseAuth.getCurrentUser();//get current user ile oturum açmış kullanıcıya erişin
                if (user != null) {
                    // User is signed in

                    Log.d("Kontrol", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent fake=new Intent(ActivityGirisSayfasi.this,MainPage.class);
                    fake.putExtra("fake","");
                    startActivity(fake);
                } else {
                    // User is signed out
                    Log.d("Kontrol", "onAuthStateChanged:signed_out");
                }

            } };
        kullaniciAdi = (EditText) findViewById(R.id.kullaniciAdi);
        sifre = (EditText) findViewById(R.id.sifre);



        giris = (Button) findViewById(R.id.giris);
        giris.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(kullaniciAdi.getText().toString().trim())){
                    kullaniciAdi.requestFocus();
                    sifre.setError("Lütfen Kullanıcı Adı veya Email Adresinizi Giriniz");
                    return;
                }
                else if(TextUtils.isEmpty(sifre.getText().toString())){
                    sifre.requestFocus();
                    sifre.setError("Lütfen Şifrenizi Giriniz.");
                    return;
                }
//todo:internet baglantısı olmadıgı zaman kontrol etmek için
                ConnectivityManager cm =
                        (ConnectivityManager)ActivityGirisSayfasi.this.getSystemService(Context.CONNECTIVITY_SERVICE);

                final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                mAuth.signInWithEmailAndPassword(kullaniciAdi.getText().toString().trim(), sifre.getText().toString().trim()).addOnCompleteListener(ActivityGirisSayfasi.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if( isConnected==true){
                         if (task.isSuccessful()) {

                            ActivityProfilSayfasi.setAlınan(mAuth.getCurrentUser().getUid());
                            Intent intent = new Intent(ActivityGirisSayfasi.this, MainPage.class);
                                            intent.putExtra("gelecekOlanKisi", mAuth.getCurrentUser().getUid());
                                            startActivity(intent);
                                            finish();


                         }
                         else {
                             Log.e("Giriş Hatası", task.getException().getMessage());
                             kullaniciAdi.setText("");
                             sifre.setText("");
                             Toast.makeText(getApplicationContext(), "Kullanıcı Adı veya Şifre Hatalı  Tekrar Deneyin", Toast.LENGTH_SHORT).show();

                         }}
                         else if(isConnected == false ){

                         Toast.makeText(getApplicationContext(), "Internet Bağlantınızı Kontrol Edin", Toast.LENGTH_SHORT).show();

                         }

                    }
                });

            }});


        sifremiunuttum=(TextView) findViewById(R.id.unuttum);
        sifremiunuttum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(kullaniciAdi.getText().toString().trim())) {
                    Toast.makeText(getApplication(), "Lütfen email adresinizi giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(kullaniciAdi.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ActivityGirisSayfasi.this,"yeni parola", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ActivityGirisSayfasi.this, "Mail gönderme hatası!", Toast.LENGTH_SHORT).show();
                        }
                    }
                        });
            }
        }); }


    //TODO: burada button mı var dı textview için mi tekrar kontrol edilmeli
    public void GirisClick(View view) {
        switch (view.getId()){
            case R.id.kaydol:
                Intent intent=new Intent(ActivityGirisSayfasi.this,Kaydol.class);
                startActivity(intent);
                break;
        }
    }
}