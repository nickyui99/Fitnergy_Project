package com.rtn.fitnergy.education;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.model.UserModel;


public class FavouritePageActivity extends AppCompatActivity {

    MyDatabaseHelper myDB= new MyDatabaseHelper(FavouritePageActivity.this);
    SharedPreferences sharedPreferences;
    UserModel userData;
    String spEmail, spPassword;
    final String SHARED_PREFS = "user_session";
    final String EMAIL_KEY = "email_key";
    final String PASSWORD_KEY = "password_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        displayData();
        //TODO : Get article list from database here


        //TODO: Initialize recycler & adapter
    }

    public String getUserEmail (MyDatabaseHelper db){
        sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        spEmail = sharedPreferences.getString(EMAIL_KEY, null);
        spPassword = sharedPreferences.getString(PASSWORD_KEY, null);

        userData = db.getUserData(spEmail, spPassword);
        String userEmail = userData.getUserEmail();
        Log.d("HOHOHO","getUserEmail: "+userEmail);
        return userEmail;
    }

    void displayData(){
        Article favArticle = new Article();
        Cursor cursor = myDB.readFavData(getUserEmail(myDB));
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