package com.rtn.fitnergy.education;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;


public class AddArticleActivity extends AppCompatActivity {

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
                myDB.addArticle(addArticleTitle.getText().toString(), addArticleDesc.getText().toString(), "none", "none");
            }
        });
    }
}