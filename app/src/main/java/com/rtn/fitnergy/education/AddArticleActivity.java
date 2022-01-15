package com.rtn.fitnergy.education;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.model.UserModel;


public class AddArticleActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    UserModel userData;
    String spEmail, spPassword;
    final String SHARED_PREFS = "user_session";
    final String EMAIL_KEY = "email_key";
    final String PASSWORD_KEY = "password_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        EditText addArticleTitle = (EditText) findViewById(R.id.et_addArticle_title);
        EditText addArticleDesc = (EditText) findViewById(R.id.et_addArticle_desc);
        Button addArticleButton = (Button) findViewById(R.id.btn_addArticle_button);


        addArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(view.getContext());
                myDB.addArticle(addArticleTitle.getText().toString(), addArticleDesc.getText().toString(), "https://sm.pcmag.com/t/pcmag_ap/news/t/the-best-f/the-best-fitness-apps-for-2020_ycf7.1200.jpg", "none",getUserEmail(myDB));
            }
        });


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

}