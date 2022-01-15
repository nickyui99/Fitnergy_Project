package com.rtn.fitnergy.education;


import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class EduFragment extends Fragment {

    SharedPreferences sharedPreferences;
    UserModel userData;
    String spEmail, spPassword;
    public static final String SHARED_PREFS = "user_session";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    Article article1 = new Article();
    Article cardioArticle = new Article();
    Article strArticle = new Article();
    Article armArticle = new Article();


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edu, container, false);
        MyDatabaseHelper db = new MyDatabaseHelper(view.getContext());
        Button favButton = view.findViewById(R.id.btn_eduFrag_fav);
        Button addButton = view.findViewById(R.id.btn_eduFrag_add);
        initButton(view, favButton, addButton);
        initArticle(db);
        enableOfflineRecycler(view);
        volleyTest(view, article1, "healthy%20eating", true);
        return view;
    }

    public void initArticle(MyDatabaseHelper db) {
        armArticle = getDBData(db, "arm", "https://www.muscleandfitness.com/wp-content/uploads/2014/09/Muscular-Bodybuilder-Doing-Biceps-Workout-With-A-Bicep-Curl.jpg?w=1109&h=614&crop=1&quality=86&strip=all");
        strArticle = getDBData(db, "strength", "https://assets.sweat.com/html_body_blocks/images/000/016/860/original/CardioAtHome_en198c593a6da46c6f07deb08e9ee740fc.jpg?1623214037");
        cardioArticle = getDBData(db, "cardio", "https://cdn2.coachmag.co.uk/sites/coachmag/files/styles/16x9_480/public/2017/10/snatch-grip-deadlift.jpg?itok=t4sjbN_A&timestamp=1507546192");
    }

    public void initButton(View view, Button btn1, Button btn2) {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favPageIntent = new Intent(view.getContext(), FavouritePageActivity.class);
                favPageIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(favPageIntent);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addArticleIntent = new Intent(view.getContext(), AddArticleActivity.class);
                addArticleIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(addArticleIntent);
            }
        });
    }

    public void getUserEmail(MyDatabaseHelper db) {
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        spEmail = sharedPreferences.getString(EMAIL_KEY, null);
        spPassword = sharedPreferences.getString(PASSWORD_KEY, null);

        userData = db.getUserData(spEmail, spPassword);
        String userEmail = userData.getUserEmail();
        Log.d("HAHAHA", "getUserEmail: " + userEmail);
    }

    public void volleyTest(View view, Article newArticle, String keyword, boolean completeArray) {
        //String url="https://newsdata.io/api/1/news?apikey=pub_2628cb71c0228d9f2bb09214caae1b7eab7e&q=natural%20disasters";
        String url = "https://gnews.io/api/v4/search?q=" + keyword + "&token=48acd5fa39eb227e179f544e427744c9&lang=en";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("articles");

                    //jsonArray.length()
                    for (int i = 0; i < 6; i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        newArticle.addArticle(i, jo.getString("title"), jo.getString("content"), jo.getString("image"), jo.getString("url"));
                        Log.e("BOOM", newArticle.articleData.get(i).getTitle());
                    }
                    if (completeArray) {
                        enableOnlineRecycler(view, article1);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "No online articles for now..", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public Article getDBData(MyDatabaseHelper db, String cat, String imgLink) {
        Article myArticle = new Article();
        Cursor cursor = db.readArticleStoreData(cat);
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No articles available..", Toast.LENGTH_LONG);
        } else {
            while (cursor.moveToNext()) {
                myArticle.addArticle(cursor.getInt(0), cursor.getString(1), cursor.getString(2), imgLink, cursor.getString(4));
                Toast.makeText(getActivity(), cursor.getString(1), Toast.LENGTH_LONG);
            }
        }
        return myArticle;
    }

    public void enableOfflineRecycler(View view) {
        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);
        MyAdapter myAdapter = new MyAdapter(getActivity(), armArticle.articleData);
        recyclerView1.setAdapter(myAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        MyAdapter myAdapter1 = new MyAdapter(getActivity(), cardioArticle.articleData);
        recyclerView2.setAdapter(myAdapter1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        MyAdapter myAdapter3 = new MyAdapter(getActivity(), strArticle.articleData);
        recyclerView3.setAdapter(myAdapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }


    public void enableOnlineRecycler(View view, Article article1) {
        RecyclerView recyclerView4 = (RecyclerView) view.findViewById(R.id.recyclerView4);
        recyclerView4.setHasFixedSize(true);
        MyAdapter myAdapter4 = new MyAdapter(getActivity(), article1.articleData);
        recyclerView4.setAdapter(myAdapter4);
        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }
}





