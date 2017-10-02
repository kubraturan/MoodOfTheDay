package com.example.developer.moodoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class dortkisisohbet extends AppCompatActivity {


    String username;

    EditText messageText;
    Button send;
    ListView listView;
    String databaseGrup,databaseMod;
    String grupAdi;
    FirebaseUser user;

    MessagesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dortkisisohbet);

        Intent gelen=getIntent();
        String geldi=gelen.getStringExtra("mesaj");
        username=gelen.getStringExtra("kullaniciAdi");
        databaseMod=gelen.getStringExtra("gelenModSpinner");
        databaseGrup=gelen.getStringExtra("gelenSpinner");
        grupAdi=gelen.getStringExtra("grupAdi");
        user= FirebaseAuth.getInstance().getCurrentUser();

        Toast.makeText(dortkisisohbet.this, geldi, Toast.LENGTH_LONG).show();


        FirebaseDatabase.getInstance().getReference("SohbetGruplari").child(databaseMod).child(databaseGrup).child(grupAdi).child("Messages")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Message inMessage = dataSnapshot.getValue(Message.class);

                        if (!inMessage.getName().equals(username)) // Kendi mesajlarımızı iki defa göstermemek için
                            adapter.add(inMessage);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



        ArrayList<Message> listOfMessages = new ArrayList<>();
        adapter = new MessagesAdapter(this, listOfMessages);

        listView = (ListView) findViewById(R.id.message_list);
        listView.setAdapter(adapter);

        send = (Button) findViewById(R.id.send);
        messageText = (EditText) findViewById(R.id.message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = messageText.getText().toString();
                Message msg = new Message(username, text,user.getUid().toString());

                FirebaseDatabase.getInstance().getReference("SohbetGruplari").child(databaseMod).child(databaseGrup).child(grupAdi).child("Messages")
                        .push()
                        .setValue(msg);

                adapter.add(msg);

                messageText.getText().clear();
            }
        });
    }

}