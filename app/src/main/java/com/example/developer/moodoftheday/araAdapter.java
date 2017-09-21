package com.example.developer.moodoftheday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class araAdapter extends BaseAdapter {


    List<Kisiler> aranacak= new ArrayList<Kisiler>();
    LayoutInflater modInflater;
    Context context;

    public araAdapter(Context activity, List<Kisiler> aranacak) {
        modInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.aranacak = aranacak;
        this.context = activity;
    }
    @Override
    public int getCount() {
        return aranacak.size();
    }

    @Override
    public Object getItem(int position) {
        return aranacak.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View lineView;
        lineView = modInflater.inflate(R.layout.activity_ara_adapter, null);
        TextView ad= (TextView) lineView.findViewById(R.id.isim);
        ImageButton profResmi = (ImageButton) lineView.findViewById(R.id.profResmii);

        final Kisiler ara = aranacak.get(position);
        ad.setText(ara.getName());
        Glide.with(context).load(ara.getKisiResmi()).into(profResmi);

        return lineView;
    }
}

