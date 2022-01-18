package com.rtn.fitnergy.meal;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.rtn.fitnergy.R;

public class lunchActivity extends AppCompatActivity {
    ImageView imageView5, imageView6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        ImageView imageView5 = findViewById(R.id.imageView5);
        imageView5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(lunchActivity.this,lunch1Activity.class);
                        startActivity(i);
                    }         }
        );
        ImageView imageView6 = findViewById(R.id.imageView6);
        imageView6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(lunchActivity.this,lunch2Activity.class);
                        startActivity(i);
                    }

                }
        );
    }
}