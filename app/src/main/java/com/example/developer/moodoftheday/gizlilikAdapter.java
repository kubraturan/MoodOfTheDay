package com.example.developer.moodoftheday;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class gizlilikAdapter  extends BaseAdapter {

    List<gizlilikClass> gizlilik = new ArrayList<gizlilikClass>();
    LayoutInflater modInflater;
    Intent dön;

    PopupMenu popup;

    public gizlilikAdapter(Activity activity, List<gizlilikClass> gizlilik) {
        modInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.gizlilik = gizlilik;

    }

    @Override
    public int getCount() {
        return gizlilik.size();
    }

    @Override
    public Object getItem(int position) {
        return gizlilik.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View lineView;
        lineView = modInflater.inflate(R.layout.activity_gizlilik_adapter, null);
        final TextView durum = (TextView) lineView.findViewById(R.id.durum);
        ImageView image = (ImageView) lineView.findViewById(R.id.image);
        final RadioButton radioButton = (RadioButton) lineView.findViewById(R.id.radioButton);
      //  final ImageButton menu = (ImageButton) lineView.findViewById(R.id.menu);


        final gizlilikClass gizli = gizlilik.get(position);
        durum.setText(gizli.getDurum());
       image.setImageResource(gizli.getImage());
      //  radioButton.setActivated(gizli.isRadioButton());
        return lineView;

    }
}
