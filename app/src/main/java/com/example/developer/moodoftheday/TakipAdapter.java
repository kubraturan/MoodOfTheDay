package com.example.developer.moodoftheday;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TakipAdapter extends BaseAdapter {

    List<Kisiler> takipKisi= new ArrayList<Kisiler>();
    LayoutInflater modInflater;
    Context context;
    DatabaseReference onaylanan;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    PopupMenu popup;
    public TakipAdapter(Context activity, List<Kisiler> takipKisi) {
        modInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.takipKisi = takipKisi;
        this.context = activity;
    }
    @Override
    public int getCount() {
        return takipKisi.size();
    }

    @Override
    public Object getItem(int position) {
        return takipKisi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View lineView;

        lineView = modInflater.inflate(R.layout.activity_takip_adapter, null);

        TextView takipismi=  (TextView) lineView.findViewById(R.id.takipisim);
        final ImageView takipResmi = (ImageView) lineView.findViewById(R.id.takipRes);
        Button onayla=(Button) lineView.findViewById(R.id.onayla);
        Button sil=(Button) lineView.findViewById(R.id.sil);

        final Kisiler takip = takipKisi.get(position);
        takipismi.setText(takip.getName());
        Glide.with(context).load(takip.getKisiResmi()).into(takipResmi);

        onayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("takipIstekleri")
                        .child(user.getUid())
                        .child(takip.getId())
                        .removeValue();

                FirebaseDatabase.getInstance().getReference("ArkadasListesi")
                        .child(user.getUid())
                        .child(takip.getId())
                        .setValue(true);

                takipKisi.remove(takip);
                notifyDataSetChanged();
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("takipIstekleri")
                        .child(user.getUid())
                        .child(takip.getId())
                        .removeValue();


                takipKisi.remove(takip);
                notifyDataSetChanged();
            }
        });

        return lineView;
    }
}

