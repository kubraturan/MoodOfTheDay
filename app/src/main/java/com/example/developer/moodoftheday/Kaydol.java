package com.example.developer.moodoftheday;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.ArrayList;
import java.util.List;

public class Kaydol extends AppCompatActivity implements  Validator.ValidationListener {

    @NotEmpty
    EditText name;
    @NotEmpty
    @Password
    EditText password;
    @NotEmpty
    @ConfirmPassword
    EditText confirmPassword;
    @NotEmpty
    EditText email;
    @NotEmpty
    EditText kullanıcıAdi;
    @Checked(message = "You must agree to the terms.")
    CheckBox sozlesme;
    Validator validator;
    boolean controlpoint=false;
    DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference arkListesi=FirebaseDatabase.getInstance().getReference("ArkadasListesi");

    public String username;
    public String Password;
    public String Email;
    public String KullaniciAdi;
    String kisiResmi="2130837591";
    String id="78324623864823";
    String profilGizlilik="Herkese Açık";
    List<Kisiler> person=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydol);
        name = (EditText) findViewById(R.id.IsımEdit);
        password = (EditText) findViewById(R.id.PasswordEdit);
        confirmPassword = (EditText) findViewById(R.id.ConfirmPasswordEdit);
        sozlesme=(CheckBox) findViewById(R.id.checkBox1) ;
        email = (EditText) findViewById(R.id.EmailEdit);
        kullanıcıAdi = (EditText) findViewById(R.id.KullanıcıAdıEdit);
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    public void Onay(View view) {
        switch(view.getId()){
            case R.id.NextPage:
                validator.validate();
                if(controlpoint){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseAuth.getInstance().getCurrentUser();
                                users.child(task.getResult().getUser().getUid()).setValue(new Kisiler(id,username, KullaniciAdi, Email, Password,kisiResmi,profilGizlilik));
                                arkListesi.child(task.getResult().getUser().getUid()).setValue("");
                                Toast.makeText(Kaydol.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Kaydol.this, ActivityGirisSayfasi.class);
                                startActivity(intent);
                            }
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Kaydol.this, "kişi eklenemedi", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;}
    }

    @Override
    public void onValidationSucceeded() {
        username = name.getText().toString().trim();
        Password = password.getText().toString().trim();
        Email = email.getText().toString().trim();
        KullaniciAdi = kullanıcıAdi.getText().toString().trim();
        controlpoint=true;


    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

    }
}
