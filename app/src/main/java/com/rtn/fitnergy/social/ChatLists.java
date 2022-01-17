package com.rtn.fitnergy.social;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtn.fitnergy.R;

import java.util.ArrayList;

public class ChatLists extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlists);

        ArrayList<ChatRepeat> arrayList= new ArrayList<>();
        arrayList.add(new ChatRepeat(R.drawable.post,"Tan Yi Wee", "Hello"));
        arrayList.add(new ChatRepeat(R.drawable.pic4, "Ronald Lim", "Hello"));
        arrayList.add(new ChatRepeat(R.drawable.ronnie,"Nicholas", "Hello"));
        arrayList.add(new ChatRepeat(R.drawable.pic3,"Chee Kin", "Hello"));


        RecyclerView chatslists=(RecyclerView) findViewById(R.id.chatslists);
        chatslists.setHasFixedSize(true);

        ChatAdapter chatt = new ChatAdapter(arrayList);
        chatslists.setAdapter(chatt);
        chatslists.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }
}
