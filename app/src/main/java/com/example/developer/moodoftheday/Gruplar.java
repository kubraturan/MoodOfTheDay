package com.example.developer.moodoftheday;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Gruplar extends AppCompatActivity {
    public List<String> list_parent;
    List<String> liste=new ArrayList<String>();
    DatabaseReference dbrefParent;
    public ExpandListViewAdapter expand_adapter;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView expandlist_view;
    public List<String> gs_list;
    public List<String> fb_list;
    public int last_position = -1;
    private ArrayAdapter<String> dataAdapterForGruplar;
    String child_name,parent_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruplar);

        dbrefParent=FirebaseDatabase.getInstance().getReference("SohbetGruplari");
        expandlist_view = (ExpandableListView)findViewById(R.id.expand_listview);

        Hazırla(); // expandablelistview içeriğini hazırlamak için

        // Adapter sınıfımızı oluşturmak için başlıklardan oluşan listimizi ve onlara bağlı olan elemanlarımızı oluşturmak için HaspMap türünü yolluyoruz
        expand_adapter = new ExpandListViewAdapter(getApplicationContext(), list_parent, list_child);
        expandlist_view.setAdapter(expand_adapter);  // oluşturduğumuz adapter sınıfını set ediyoruz
        expandlist_view.setClickable(true);

        expandlist_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
              parent_name=(String) expand_adapter.getGroup(groupPosition).toString();
                child_name = (String)expand_adapter.getChild(groupPosition, childPosition);

                 liste.clear();
                dbrefParent.child(parent_name).child(child_name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                            liste.add(postSnapshot.getKey().toString());


                        }

                     creatCustomDialog(Gruplar.this);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



               return false;
            }
        });



        expandlist_view.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                if(last_position != -1 && last_position != groupPosition)
                {
                    expandlist_view.collapseGroup(last_position);
                }
                last_position = groupPosition;

            }
        });


    }
    public void Hazırla()
    {
        list_parent = new ArrayList<String>();  // başlıklarımızı listemelek için oluşturduk
        list_child = new HashMap<String, List<String>>(); // başlıklara bağlı elemenları tutmak için oluşturduk

        //  başlığı giriyoruz
        list_parent.add("Hareketli Canlı Hissediyorum");
        list_parent.add("Bunalmış Hissediyorum");
        list_parent.add("Sakin,Rahat ve Özgür Hissediyorum");
        list_parent.add("Duygusal Hissediyorum");
        list_parent.add("Umutlu Hissediyorum");
        list_parent.add("Neşeli Hissediyorum");
        list_parent.add("Cesaretli ve Güven Dolu Hissediyorum");
        list_parent.add("Huzurlu Hissediyorum");
        list_parent.add("Hüzünlü ve Özlemiş Hissediyorum");
        list_parent.add("Üzgün ve Mutsuz Hissediyorum");
        list_parent.add("Mutlu Hissediyorum");

        gs_list = new ArrayList<String>();  // ilk başlık için alt elemanları tanımlıyoruz
        gs_list.add("3 Kişilik Gruplar");
        gs_list.add("4 Kişilik Gruplar");
        gs_list.add("5 Kişilik Gruplar");
        gs_list.add("6 Kişilik Gruplar");



        list_child.put(list_parent.get(0),gs_list);// ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(1),gs_list);
        list_child.put(list_parent.get(2),gs_list);
        list_child.put(list_parent.get(3),gs_list);
        list_child.put(list_parent.get(4),gs_list);
        list_child.put(list_parent.get(5),gs_list);
        list_child.put(list_parent.get(6),gs_list);
        list_child.put(list_parent.get(7),gs_list);
        list_child.put(list_parent.get(8),gs_list);
        list_child.put(list_parent.get(9),gs_list);
        list_child.put(list_parent.get(10),gs_list);



    }


    public void creatCustomDialog(Context context) {
        View dialogView = View.inflate(context, R.layout.modlarim, null);
        ListView modList = (ListView) dialogView.findViewById(R.id.ListlerimMod);
        dataAdapterForGruplar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, liste);

      //  Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterForGruplar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        modList.setAdapter(dataAdapterForGruplar);

        final AlertDialog.Builder builder=new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(dialogView);


        final  AlertDialog dialog=builder.create();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);


        // Listelerden bir eleman seçildiğinde yapılacakları tanımlıyoruz.

        modList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (child_name == "3 Kişilik Gruplar") {

                    if (liste.size() <= 3) {
                        Intent git = new Intent(getApplicationContext(), NickName.class);
                        git.putExtra("kisiSayisi",liste.size());
                        git.putExtra("mesaj", "Grupa Hoş Geldin");
                        git.putExtra("gelenModSpinner", parent_name);
                        git.putExtra("gelenSpinner", child_name);
                        git.putExtra("grupAdi",liste.get(position).toString() );
                        startActivity(git);

                    } else {

                        Toast.makeText(getBaseContext(), "Grup Doludur Başka Grup Deneyiniz", Toast.LENGTH_SHORT).show();
                    }

                    liste.clear();

                }
                if (child_name == "4 Kişilik Gruplar") {

                    if (liste.size() <= 4) {
                        Intent git = new Intent(getApplicationContext(), NickName.class);
                        git.putExtra("kisiSayisi",liste.size());
                        git.putExtra("mesaj", "Grupa Hoş Geldin");
                        git.putExtra("gelenModSpinner", parent_name);
                        git.putExtra("gelenSpinner", child_name);
                        git.putExtra("grupAdi",liste.get(position).toString() );
                        startActivity(git);

                    } else {

                        Toast.makeText(getBaseContext(), "Grup Doludur Başka Grup Deneyiniz", Toast.LENGTH_SHORT).show();
                    }

                    liste.clear();

                }
                if (child_name == "5 Kişilik Gruplar") {

                    if (liste.size() <= 5) {
                        Intent git = new Intent(getApplicationContext(), NickName.class);
                        git.putExtra("kisiSayisi",liste.size());
                        git.putExtra("mesaj", "Grupa Hoş Geldin");
                        git.putExtra("gelenModSpinner", parent_name);
                        git.putExtra("gelenSpinner", child_name);
                        git.putExtra("grupAdi",liste.get(position).toString() );
                        startActivity(git);

                    } else {

                        Toast.makeText(getBaseContext(), "Grup Doludur Başka Grup Deneyiniz", Toast.LENGTH_SHORT).show();
                    }

                    liste.clear();

                }
                if (child_name == "6 Kişilik Gruplar") {

                    if (liste.size() <= 6) {
                        Intent git = new Intent(getApplicationContext(), NickName.class);
                        git.putExtra("kisiSayisi",liste.size());
                        git.putExtra("mesaj", "Grupa Hoş Geldin");
                        git.putExtra("gelenModSpinner", parent_name);
                        git.putExtra("gelenSpinner", child_name);
                        git.putExtra("grupAdi",liste.get(position).toString() );
                        startActivity(git);

                    } else {

                        Toast.makeText(getBaseContext(), "Grup Doludur Başka Grup Deneyiniz", Toast.LENGTH_SHORT).show();
                    }

                    liste.clear();

                }




            } } );
        dialog.show(); } }





