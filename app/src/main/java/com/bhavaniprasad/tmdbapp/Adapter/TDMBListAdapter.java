package com.bhavaniprasad.tmdbapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavaniprasad.tmdbapp.Model.ListModel;
import com.bhavaniprasad.tmdbapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TDMBListAdapter extends RecyclerView.Adapter<TDMBListAdapter.Customview> {
    private Context cnt;
    private ArrayList<ListModel> arraylist;
    private String posterpath;
    private LayoutInflater layoutInflater;

    public TDMBListAdapter(Context context, ArrayList<ListModel> listModels) {
        this.arraylist=listModels;
        this.cnt=context;
    }

    @NonNull
    @Override
    public TDMBListAdapter.Customview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }
        View view =layoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
        return new Customview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TDMBListAdapter.Customview holder, int position) {
        final Customview customviewholder=(Customview) holder;
        posterpath="https://image.tmdb.org/t/p/w500";
        customviewholder.name.setText(arraylist.get(position).getTitle());
        customviewholder.date.setText(arraylist.get(position).getRelease_date());
        customviewholder.description.setText(arraylist.get(position).getOverview());
        customviewholder.voteaverage.setText(arraylist.get(position).getVote_average());
        posterpath=posterpath.concat(arraylist.get(position).getPoster_path());
        Picasso.with(cnt).load(posterpath).into(customviewholder.imageView);
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class Customview extends RecyclerView.ViewHolder{
        TextView name,description,date,voteaverage;
        ImageView imageView;
        public Customview(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            date=itemView.findViewById(R.id.datedetail);
            description=itemView.findViewById(R.id.description);
            imageView=itemView.findViewById(R.id.avatar);
            voteaverage=itemView.findViewById(R.id.voteaverage);
        }
    }
}
