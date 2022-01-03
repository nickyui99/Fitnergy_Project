package com.rtn.fitnergy.education;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private ArrayList<Article> favouriteData;
    Context context;


    public ListAdapter(Context ct, ArrayList<Article> ad) {
        this.context = ct;
        this.favouriteData = ad;

    }

    public ListAdapter() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.my_fav_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//TODO : read image code and display the proper image.
        holder.title.setText(favouriteData.get(position).getTitle());


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //open new article page , pass data to article activity
                Intent intent = new Intent(view.getContext(), ArticlePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", favouriteData.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("desc", favouriteData.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("img", favouriteData.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("link", favouriteData.get(holder.getAdapterPosition()).getLink());
                intent.putExtra("imgLink", favouriteData.get(holder.getAdapterPosition()).getImgLink());
                context.startActivity(intent);
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditPageActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("id", favouriteData.get(holder.getAdapterPosition()).getId());
                intent.putExtra("title", favouriteData.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("desc", favouriteData.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("img", favouriteData.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("link", favouriteData.get(holder.getAdapterPosition()).getLink());
                intent.putExtra("imgLink", favouriteData.get(holder.getAdapterPosition()).getImgLink());
                context.startActivity(intent);
            }
        });

        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper db = new MyDatabaseHelper(view.getContext());
                db.deleteArticle(favouriteData.get(holder.getAdapterPosition()).getId());
                Intent intent = new Intent(view.getContext(), FavouritePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return favouriteData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView editButton, delButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            editButton = itemView.findViewById(R.id.iv_favPage_edit);
            delButton = itemView.findViewById(R.id.iv_favPage_del);
            title = itemView.findViewById(R.id.tv_favPage_title);
        }
    }


}
