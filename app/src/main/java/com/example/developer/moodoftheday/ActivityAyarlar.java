package com.example.developer.moodoftheday;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

public class ActivityAyarlar extends AppCompatActivity {
Button gizlilikAyarlari;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        //gizlilikAyarlari=(Button) findViewById(R.id.gizlilikAyarlari);
//        gizlilikAyarlari.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                PopupMenu popup = new PopupMenu(context, gizlilikAyarlari);
////
////                popup.getMenuInflater().inflate(R.menu.gizlilikmenu, popup.getMenu());
////
////                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.sadeceBen:
//                                //   gizliliği düzenle fonsiyonu gelecek
//                                break;
//                            case R.id.arkadaslar:
//                                //sil fonksiyonu gelecek
//                            case R.id.herkes:
//                                //     düzenle fonsiyonu gelecek
//                                break;
//                        }
//                        return false;
//                    }
//                });
//
//               popup.show();
//            }
//        });
    }
}
