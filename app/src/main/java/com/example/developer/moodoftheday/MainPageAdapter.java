package com.example.developer.moodoftheday;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rumeysal on 8/19/17.
 */

public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.ViewHolder> {
    private ArrayList<Post> post;
    private Context context;


    MainPageAdapter(Context ctx, ArrayList<Post> post){
        this.context=ctx;
        this.post=post;
    }


    @Override
    public MainPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.mainpageadaptersatir,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainPageAdapter.ViewHolder holder, int position) {
        Post posta=post.get(position);
        holder.kisiİsim.setText(posta.getName());
        holder.PostDurum.setText(posta.getPost());
        holder.kisiImage.setImageResource(posta.getImageSource());
        holder.postImage.setImageResource(posta.getImageSource());
       /* Picasso.with(context)
                .load(post.getImageUrl())
                .into(holder.itemImage);*/

    }

    @Override
    public int getItemCount() {
        return post.size();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.kisiİsim)  TextView kisiİsim;
        @BindView(R.id.PostDurum)  TextView PostDurum;
        @BindView(R.id.KisiResim) ImageView kisiImage;
        @BindView(R.id.ModResim) ImageView postImage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
