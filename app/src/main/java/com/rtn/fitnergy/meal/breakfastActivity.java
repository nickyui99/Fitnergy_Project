package com.rtn.fitnergy.meal;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.rtn.fitnergy.R;

public class breakfastActivity extends AppCompatActivity {
ImageView imageView3, imageView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);


        ImageView imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(breakfastActivity.this,break1Activity.class);
                        startActivity(i);
                    }         }
        );
                    ImageView imageView4 = findViewById(R.id.imageView4);
        imageView4.setOnClickListener(
                new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(breakfastActivity.this,break2Activity.class);
                            startActivity(i);
                        }

                }
        );
    }
}