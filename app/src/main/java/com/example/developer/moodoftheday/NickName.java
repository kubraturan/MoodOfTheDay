package com.example.developer.moodoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NickName extends AppCompatActivity {
    EditText nick;

    Button basla;

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
           nick=(EditText) findViewById(R.id.nick);
           basla=(Button) findViewById(R.id.basla);

        Intent gelen=getIntent();
        final String geldi=gelen.getStringExtra("mesaj");
        final String   kisiSayisi=gelen.getStringExtra("kisiSayisi");
        final String parent_name=gelen.getStringExtra("gelenModSpinner");
        final String   child_name=gelen.getStringExtra("gelenSpinner");
        final String  grupAdi=gelen.getStringExtra("grupAdi");
        user= FirebaseAuth.getInstance().getCurrentUser();


        basla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent git=new Intent(getApplicationContext(),uckisisohbet.class);
                git.putExtra("kisiSayisi",kisiSayisi);
                git.putExtra("mesaj", geldi.toString());
                git.putExtra("gelenModSpinner", parent_name);
                git.putExtra("gelenSpinner", child_name);
                git.putExtra("grupAdi",grupAdi);
                git.putExtra("kullaniciNick",nick.getText().toString());
                startActivity(git);
            }
        });



//        git.putExtra("mesaj", "Grupa Hoş Geldin");
//        git.putExtra("kullaniciAdi","merhaba");
    }
}
