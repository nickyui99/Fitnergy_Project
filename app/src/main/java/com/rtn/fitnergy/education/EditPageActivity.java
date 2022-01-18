package com.rtn.fitnergy.education;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;


public class EditPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        EditText editTitle = findViewById(R.id.et_editArticle_title);
        EditText editDesc = findViewById(R.id.et_editArticle_desc);
        Button updateBtn=findViewById(R.id.btn_editArticle_button);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt("id");
            String title = extras.getString("title");
            String desc = extras.getString("desc");
            int img = extras.getInt("img");
            String link = extras.getString("link");
            String imgLink = extras.getString("imgLink");

            editTitle.setText(title);
            editDesc.setText(desc);

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!editTitle.getText().toString().isEmpty() && !editDesc.getText().toString().isEmpty()) {
                        MyDatabaseHelper db = new MyDatabaseHelper(EditPageActivity.this);
                        db.updateArticle(id, editTitle.getText().toString(), editDesc.getText().toString());
                        Intent favPageIntent = new Intent(EditPageActivity.this, FavouritePageActivity.class);
                        startActivity(favPageIntent);
                    }
                    else{
                        Toast.makeText(view.getContext(), "Please fill in the input fields.", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }



    }
}