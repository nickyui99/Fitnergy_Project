package com.rtn.fitnergy.meal;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.rtn.fitnergy.R;

public class dinnerActivity extends AppCompatActivity {
    ImageView imageView7, imageView8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);

        ImageView imageView7 = findViewById(R.id.imageView7);
        imageView7.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(dinnerActivity.this,dinner1Activity.class);
                        startActivity(i);
                    }         }
        );
        ImageView imageView8 = findViewById(R.id.imageView8);
        imageView8.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(dinnerActivity.this,dinner2Activity.class);
                        startActivity(i);
                    }

                }
        );
    }
}