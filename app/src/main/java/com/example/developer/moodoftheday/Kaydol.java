package com.example.developer.moodoftheday;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;


import java.util.ArrayList;
import java.util.List;

public class Kaydol extends AppCompatActivity implements  Validator.ValidationListener {

    @NotEmpty
    EditText name;
    @NotEmpty
    EditText password;

    @NotEmpty
    EditText confirmPassword;
    @NotEmpty
    EditText email;
    @NotEmpty
    EditText kullanıcıAdi;
    Validator validator;
    boolean controlpointtwo=true;

    DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference arkadasList = FirebaseDatabase.getInstance().getReference("ArkadasListesi");

    ImageButton kisiResmi;
    public String username;
    public String Password;
    public String Email;
    public String KullaniciAdi;

    public String kisiResmiii;
    private static final int fotograf = 1;
    private static final int resim = 2;
    StorageReference storageReference;
    Uri imageUri;


    List<Kisiler> person=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydol);
        name = (EditText) findViewById(R.id.IsımEdit);
        password = (EditText) findViewById(R.id.PasswordEdit);
        confirmPassword = (EditText) findViewById(R.id.ConfirmPasswordEdit);
        email = (EditText) findViewById(R.id.EmailEdit);
        kullanıcıAdi = (EditText) findViewById(R.id.KullanıcıAdıEdit);
        //kisiResmi=(ImageButton) findViewById(R.id.KisiResmi) ;

        validator = new Validator(this);
        validator.setValidationListener(this);


       /* kisiResmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder secimDialog = new AlertDialog.Builder(Kaydol.this);
                secimDialog.setTitle("Resim Çek veya Galeriden Resim Yükle");
                secimDialog.setIcon(R.drawable.fotogalerii); //İkonun projedeki konumu set edelir.


                secimDialog.setPositiveButton("Resim Çek ",
                        new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int which) {

                                Intent kamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(kamera, resim);

//                                users.child(task.getResult().getUser().getUid()).setValue(new Kisiler(username,KullaniciAdi, Email, Password,image));
//                                Toast.makeText(Kaydol.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(Kaydol.this, ActivityGirisSayfasi.class);
//                                startActivity(intent);

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
        });*/
    }
    @Override
    public void onValidationSucceeded() {
        username = name.getText().toString().trim();
        Password = password.getText().toString().trim();
        Email = email.getText().toString().trim();
        KullaniciAdi = kullanıcıAdi.getText().toString().trim();
        storageReference = FirebaseStorage.getInstance().getReference("profilResmi");
      }



      public void Onay(View view) {
                switch (view.getId()) {
                    case R.id.NextPage:
                        validator.validate();
                        if (Password.equals(confirmPassword.getText().toString())) {
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseAuth.getInstance().getCurrentUser();
                                        if(imageUri==null){

                                            users.child(task.getResult().getUser().getUid()).setValue(new Kisiler(task.getResult().getUser().getUid(),username, KullaniciAdi, Email, Password,String.valueOf(R.drawable.ayarlar),"Herkese Açık"));
                                            arkadasList.child(task.getResult().getUser().getUid()).setValue(task.getResult().getUser().getEmail());

                                            Toast.makeText(Kaydol.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Kaydol.this, ActivityGirisSayfasi.class);
                                            startActivity(intent);

                                        }

                                        else{
                                        final StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = storageReference.child(imageUri.getLastPathSegment()).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                @SuppressWarnings("VisibleForTests") String alll = taskSnapshot.getDownloadUrl().toString();


                                        users.child(task.getResult().getUser().getUid()).setValue(new Kisiler(task.getResult().getUser().getUid(),username, KullaniciAdi, Email, Password,String.valueOf(R.drawable.ayarlar),"Herkese Açık"));
                                                arkadasList.child(task.getResult().getUser().getUid()).setValue(task.getResult().getUser().getEmail());
                                                Toast.makeText(Kaydol.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Kaydol.this, ActivityGirisSayfasi.class);
                                        startActivity(intent);
                                            }});  }}
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Kaydol.this, "kişi eklenemedi", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(Kaydol.this);
                            dialog.setCancelable(true)
                                    .setMessage("Şifre Eşleşmiyor");
                            dialog.show();


                        }

                        break;
                }


            }



    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String mesaj = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(mesaj);
            }

        }
   }

   /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resim && resultCode == RESULT_OK) {
            imageUri = data.getData();
            kisiResmi.setImageURI(imageUri);
        } else if (requestCode == fotograf && resultCode == RESULT_OK) {
            imageUri = data.getData();
            kisiResmi.setImageURI(imageUri);
        }

    }*/



















}


