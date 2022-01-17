package com.rtn.fitnergy.social;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.rtn.fitnergy.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>
{

        private ArrayList<ChatRepeat> arraylist;
        public ChatAdapter(ArrayList<ChatRepeat> arraylist){
            this.arraylist = arraylist;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.examplelistchat,parent,false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position){
            ChatRepeat chat = arraylist.get(position);

            holder.line1.setText(chat.getLine1());
            holder.line2.setText(chat.getLine2());
            holder.profileicon.setImageResource(chat.getProfileIcon());
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView profileicon;
            TextView line1;
            TextView line2;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                profileicon = itemView.findViewById(R.id.imageView);
                line1 = itemView.findViewById(R.id.textView);
                line2 = itemView.findViewById(R.id.textView2);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent(v.getContext(), Chatin.class);
                        a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        v.getContext().startActivity(a);
                    }
                });
            }}
        }

