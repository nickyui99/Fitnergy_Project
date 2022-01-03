package com.rtn.fitnergy.education;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rtn.fitnergy.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Article> articleData;
    Context context;


    public MyAdapter(Context ct, ArrayList<Article> ad) {
        this.context = ct;
        this.articleData = ad;

    }
    public MyAdapter(){

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//TODO : read image code and display the proper image.
        holder.title.setText(articleData.get(position).getTitle());
        Glide.with(context).load(articleData.get(holder.getAdapterPosition()).getImgLink()).into(holder.img);
        //holder.img.setImageResource(articleData.get(position).getImage());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //open new article page , pass data to article activity
                Intent intent = new Intent(view.getContext(), ArticlePageActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", articleData.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("desc", articleData.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("img",articleData.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("link",articleData.get(holder.getAdapterPosition()).getLink());
                intent.putExtra("imgLink",articleData.get(holder.getAdapterPosition()).getImgLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView desc;
        CardView cv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv=itemView.findViewById(R.id.cv_row);
            title = itemView.findViewById(R.id.tv_titleText);
            img = itemView.findViewById(R.id.iv_image);

        }
    }


}
