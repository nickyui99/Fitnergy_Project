package com.rtn.fitnergy.social;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.Adapters.WorkoutRecordAdapter;
import com.rtn.fitnergy.profile.SettingsActivity;
import com.rtn.fitnergy.profile.model.WorkoutRecordModel;

import java.util.ArrayList;

public class SocialFragment extends Fragment {

    private CheckBox like;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_social,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);
        like = (CheckBox) view.findViewById(R.id.likebtn);

        ArrayList<Newsfeedrepeat> arrayList= new ArrayList<>();
        arrayList.add(new Newsfeedrepeat(R.drawable.pic1,R.drawable.post,"Narresh Naidu", "I'm the Strongest..."));
        arrayList.add(new Newsfeedrepeat(R.drawable.pic4, R.drawable.rocky,"Ronald Lim", "I'm superhero..."));
        arrayList.add(new Newsfeedrepeat(R.drawable.ronnie, R.drawable.jogging,"Yi Wee", "I'm jogging..."));
        arrayList.add(new Newsfeedrepeat(R.drawable.pic3, R.drawable.dubell,"Chee Kin", "New Dumbbells.."));
        arrayList.add(new Newsfeedrepeat(R.drawable.pic1,R.drawable.pic1,"Narresh Naidu", "I'm the working out..."));
        arrayList.add(new Newsfeedrepeat(R.drawable.pic4, R.drawable.pic4,"Ronald Lim", "Strongest..."));
        arrayList.add(new Newsfeedrepeat(R.drawable.ronnie, R.drawable.ronnie,"Yi Wee", "Keep it hard..."));
        arrayList.add(new Newsfeedrepeat(R.drawable.pic3, R.drawable.pic3,"Chee Kin", "Never ever give up.."));

        RecyclerView newsfeed=(RecyclerView) view.findViewById(R.id.newsfeed);
        newsfeed.setHasFixedSize(true);

        NewsFeedAdapter newsfeedadapter = new NewsFeedAdapter(arrayList);
        newsfeed.setAdapter(newsfeedadapter);
        newsfeed.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

    }

    public void adapterStart(){


    }
}