package com.example.developer.moodoftheday;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class profilModAdapterr extends RecyclerView.Adapter<profilModAdapterr.ViewHolder> {
    List<modumProfil> modDurumlarıList= new ArrayList<modumProfil>();

    CustomItemClickListener listener;
    LayoutInflater modInflater;
    Context context;
    PopupMenu popup;
    DatabaseReference  refKisiBil;
    FirebaseAuth firebaseAuth;
    String user;




    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView PmodAdi;
        public TextView PmodDurumu;
        public TextView tarih;
        public TextView saat;
        public CardView card_view;
        public TextView KisiAdi;
        public CircleImageView kisiProfRes;
        ImageView PmodResmi;
        ImageView PmodPaylasilanResim;
        ImageButton menu;





        public ViewHolder(View view) {
            super(view);

            card_view = (CardView) view.findViewById(R.id.card_view);
            KisiAdi = (TextView) view.findViewById(R.id.profile_name);
            PmodAdi = (TextView) view.findViewById(R.id.PmodAdi);
            kisiProfRes = (CircleImageView) view.findViewById(R.id.profile_image);
            PmodResmi = (ImageView) view.findViewById(R.id.PmodResmi);
            PmodDurumu = (TextView) view.findViewById(R.id.PmodDurumu);
            tarih = (TextView) view.findViewById(R.id.tarih);
            saat = (TextView) view.findViewById(R.id.saat);




            PmodPaylasilanResim = (ImageView) view.findViewById(R.id.paylasilacakResim);
            // menu = (ImageButton) view.findViewById(R.id.menu);



    }}

    public profilModAdapterr(String user, List<modumProfil> modDurumlarıList,Context activity,CustomItemClickListener listener) {
        this.modDurumlarıList = modDurumlarıList;
        this.user=user;
        this.context=activity;
        this.listener = listener;
    }



    @Override
    public profilModAdapterr.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        final ViewHolder view_holder = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, view_holder.getPosition());
            }
        });

        return view_holder;
    }




    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

         //user=FirebaseAuth.getInstance().getCurrentUser().getUid();
        //user=modDurumlarıList.get(position).getId();

        refKisiBil=FirebaseDatabase.getInstance().getReference("users");
      //  refKisiBil.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
        refKisiBil.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {




                holder.kisiProfRes.setImageResource(Integer.valueOf(dataSnapshot.child("kisiResmi").getValue().toString()));
                holder.KisiAdi.setText(dataSnapshot.child("name").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        PmodResmi.setImageResource(mod.getProfResmi());
//        Glide.with(context).load(mod.getResimUrl()).into(PmodPaylasilanResim);
//        tarih.setText(mod.getTarih());
//        saat.setText(mod.getSaat());



        holder.PmodAdi.setText(modDurumlarıList.get(position).getModAdi());
       // holder.kisiProfRes.setImageResource();

        holder.PmodDurumu.setText(modDurumlarıList.get(position).getPaylasilanDurum());
        holder.tarih.setText(modDurumlarıList.get(position).getTarih());
        holder.saat.setText(modDurumlarıList.get(position).getSaat());
        holder.PmodResmi.setImageResource(modDurumlarıList.get(position).getProfResmi());

        if (modDurumlarıList.get(position).getResimUrl()==null) {
            holder.PmodPaylasilanResim.setImageResource(R.drawable.fake);

        } else {

            Glide.with(context).load(modDurumlarıList.get(position).getResimUrl()).into(holder.PmodPaylasilanResim);

        }
    }
    @Override
    public int getItemCount() {
        return modDurumlarıList.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}













//public class profilModAdapterr extends BaseAdapter {

//    List<modumProfil> modDurumlarıList= new ArrayList<modumProfil>();
//    LayoutInflater modInflater;
//    Context context;

 //   PopupMenu popup;
//    public profilModAdapterr(Context activity, List<modumProfil> modDurumlarıList) {
//        modInflater = (LayoutInflater) activity.getSystemService(
//                Context.LAYOUT_INFLATER_SERVICE);
//        this.modDurumlarıList = modDurumlarıList;
//        this.context = activity;
//    }
//    @Override
//    public int getCount() {
//        return modDurumlarıList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return modDurumlarıList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View lineView;
//        lineView = modInflater.inflate(R.layout.activity_profil_mod_adapterr, null);
//        EditText PmodAdi = (EditText) lineView.findViewById(R.id.PmodAdi);
//        final EditText PmodDurumu = (EditText) lineView.findViewById(R.id.PmodDurumu);
//        TextView tarih= (TextView) lineView.findViewById(R.id.tarih);
//        TextView saat=  (TextView) lineView.findViewById(R.id.saat);
//        ImageView PmodResmi = (ImageView) lineView.findViewById(R.id.PmodResmi);
//        ImageButton PmodPaylasilanResim = (ImageButton) lineView.findViewById(R.id.paylasilacakResim);
//        final ImageButton  menu = (ImageButton) lineView.findViewById(R.id.menu);
//
//
//
//
//       final modumProfil mod = modDurumlarıList.get(position);
//        PmodAdi.setText(mod.getModAdi());
//        PmodDurumu.setText(mod.getPaylasilanDurum());
//        PmodResmi.setImageResource(mod.getProfResmi());
//        Glide.with(context).load(mod.getResimUrl()).into(PmodPaylasilanResim);
//        tarih.setText(mod.getTarih());
//        saat.setText(mod.getSaat());
//
//
//        menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                PopupMenu popup = new PopupMenu(context, menu);
//
//                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
//
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.gizliligiDzenle:
//                            //   gizliliği düzenle fonsiyonu gelecek
//                                break;
//                            case R.id.sil:
//                                //sil fonksiyonu gelecek
//                                case R.id.düzenle:
//                               //     düzenle fonsiyonu gelecek
//                                break;
//                        }
//                        return false;
//                    }
//                });
//
//                popup.show();
//            }
//        });
//
//
//        return lineView;
//    }
//}



