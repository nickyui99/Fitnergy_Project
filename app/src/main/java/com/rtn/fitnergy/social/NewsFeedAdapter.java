package com.rtn.fitnergy.social;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rtn.fitnergy.R;

import java.util.ArrayList;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>
{
    private ArrayList<Newsfeedrepeat> arraylist;
    public NewsFeedAdapter(ArrayList<Newsfeedrepeat> arraylist){
        this.arraylist = arraylist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.newsfeedlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Newsfeedrepeat feed = arraylist.get(position);

        holder.userid.setText(feed.getUserid());
        holder.caption.setText(feed.getCaption());
        holder.profileicon.setImageResource(feed.getProfileIcon());
        holder.postimage.setImageResource(feed.getPostimage());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileicon;
        ImageView postimage;
        TextView userid;
        TextView caption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileicon = itemView.findViewById(R.id.iv_image);
            postimage = itemView.findViewById(R.id.imagepost);
            userid = itemView.findViewById(R.id.username);
            caption = itemView.findViewById(R.id.caption);


        }
    }


}
