package com.rtn.fitnergy.education;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;


public class FavouritePageActivity extends AppCompatActivity {

    MyDatabaseHelper myDB= new MyDatabaseHelper(FavouritePageActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        displayData();
        //TODO : Get article list from database here


        //TODO: Initialize recycler & adapter
    }

    void displayData(){
        Article favArticle = new Article();
        Cursor cursor = myDB.readFavData();
        if(cursor.getCount()==0){
            Toast.makeText(this,"No favourite articles added..",Toast.LENGTH_LONG);
        }
        else{
            while (cursor.moveToNext()) {
                favArticle.addArticle(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                Toast.makeText(this,cursor.getString(1),Toast.LENGTH_LONG);
            }
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_favourite_page);
        recyclerView.setHasFixedSize(true);
        ListAdapter listAdapter = new ListAdapter(FavouritePageActivity.this, favArticle.articleData);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavouritePageActivity.this, LinearLayoutManager.VERTICAL, false));
    }

}