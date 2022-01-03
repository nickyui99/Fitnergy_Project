package com.rtn.fitnergy.education;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;

public class ArticlePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_page);


        TextView articlePageTitle = (TextView) findViewById(R.id.tv_articlePage_title);
        TextView articlePageDesc = (TextView) findViewById(R.id.tv_articlePage_desc);
        ImageView articlePageImg = (ImageView) findViewById(R.id.iv_articlePage_img);
        ImageButton articlePageBtn=(ImageButton) findViewById(R.id.btn_articlePage_favourite);
        ImageButton articlePageLink=(ImageButton) findViewById(R.id.btn_articlePage_browser);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String title = extras.getString("title");
            String desc = extras.getString("desc");
            int img = extras.getInt("img");
            String link= extras.getString("link");
            String imgLink= extras.getString("imgLink");

            articlePageTitle.setText(title);
            articlePageDesc.setText(desc);

            Glide.with(this).load(imgLink).into(articlePageImg);
            //articlePageImg.setImageResource(img);

            articlePageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyDatabaseHelper myDB= new MyDatabaseHelper(view.getContext());
                    myDB.addArticle(title,desc,imgLink,link);
                }
            });
            articlePageLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openBrowserIntent= new Intent(Intent.ACTION_VIEW);
                    openBrowserIntent.setData(Uri.parse(link));
                    startActivity(openBrowserIntent);
                }
            });

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            Intent favouritePageIntent = new Intent(this, FavouritePageActivity.class);
            startActivity(favouritePageIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}