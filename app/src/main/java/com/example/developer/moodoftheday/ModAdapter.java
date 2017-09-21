package com.example.developer.moodoftheday;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.R.id.parent;


public class ModAdapter extends ArrayAdapter<ModClass> {

     List<ModClass> modList;
     Context context;

    public ModAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ModClass> objects) {
        super(context, resource, objects);
        this.modList = objects;
        this.context=context;
    }

    private class ViewHolder{
        TextView modAd, modDurum;
        ImageView modRes;
    }

    @Override
    public int getCount() {
        return modList.size();
    }

    @Override
    public ModClass getItem(int position) {
        return modList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return modList.hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        final ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null){
            vi = inflater.inflate(R.layout.mod_adapter, null,false);
            holder = new ViewHolder();
            holder.modAd = (TextView) vi.findViewById(R.id.modAd);
            holder.modDurum =(TextView) vi.findViewById(R.id.modDurumu);
            holder.modRes = (ImageView) vi.findViewById(R.id.modRes);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        ModClass mod = modList.get(i);

        holder.modAd.setText(mod.getModAdi());
        holder.modDurum.setText(mod.getModDurumu());
        holder.modRes.setImageResource(mod.getModResmi());

        return vi;
    }
}