package com.rtn.fitnergy.profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.Adapters.WorkoutRecordAdapter;
import com.rtn.fitnergy.profile.model.WorkoutRecordModel;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    RecyclerView rv;
    WorkoutRecordAdapter workoutRecordAdapter;
    ArrayList<WorkoutRecordModel> workoutRecordModelArrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String spEmail, spPassword;
    public static final String SHARED_PREFS = "user_session";   // creating constant keys for shared preferences.
    public static final String EMAIL_KEY = "email_key";     // key for storing email.
    public static final String PASSWORD_KEY = "password_key";   // key for storing password.

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        // getting the data which is stored in shared preferences.
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        spEmail = sharedPreferences.getString(EMAIL_KEY, null);
        spPassword = sharedPreferences.getString(PASSWORD_KEY, null);

        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(getContext());
        workoutRecordModelArrayList = myDatabaseHelper.getWorkoutRecord(null, spEmail);

        rv = (RecyclerView) view.findViewById(R.id.rv_workoutRecord);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        workoutRecordAdapter = new WorkoutRecordAdapter(getContext(), workoutRecordModelArrayList);
        rv.setAdapter(workoutRecordAdapter);
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

    }
}
