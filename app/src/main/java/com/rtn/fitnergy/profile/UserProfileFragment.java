package com.rtn.fitnergy.profile;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.model.UserModel;
import com.rtn.fitnergy.profile.model.WorkoutRecordModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UserProfileFragment extends Fragment {

    TextView txtViewUsername, txtViewGender, txtViewAge, txtViewWorkoutTimeCounter, txtViewCalorieCounter;
    ImageView imgViewProfileImage;
    ImageButton imageButtonSettings;
    SharedPreferences sharedPreferences;
    String spEmail, spPassword;
    MyDatabaseHelper myDatabaseHelper;
    UserModel userData;
    public static final String SHARED_PREFS = "user_session";   // creating constant keys for shared preferences.
    public static final String EMAIL_KEY = "email_key";     // key for storing email.
    public static final String PASSWORD_KEY = "password_key";   // key for storing password.
    private static final int SETTINGS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        initDatabase();
        initViews();
        setViews();
    }

    public void initDatabase(){
        // getting the data which is stored in shared preferences.
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        spEmail = sharedPreferences.getString(EMAIL_KEY, null);
        spPassword = sharedPreferences.getString(PASSWORD_KEY, null);

        myDatabaseHelper = new MyDatabaseHelper(getContext());
        userData = myDatabaseHelper.getUserData(spEmail, spPassword);
    }

    public void initViews(){
        txtViewUsername = (TextView) getView().findViewById(R.id.UP_txtViewName);
        txtViewGender = (TextView) getView().findViewById(R.id.UP_txtViewGender);
        txtViewAge = (TextView) getView().findViewById(R.id.UP_txtViewAge);
        txtViewWorkoutTimeCounter = (TextView) getView().findViewById(R.id.UP_txtViewWorkoutTimeCounter);
        txtViewCalorieCounter = (TextView) getView().findViewById(R.id.UP_txtViewCalorieCounter);
        imgViewProfileImage = (ImageView) getView().findViewById(R.id.UP_imgViewProfileImage);
        imageButtonSettings = (ImageButton) getView().findViewById(R.id.UP_imgBtnSettings);
    }

    public void setViews(){
        imageButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                startActivityForResult(settingsIntent, SETTINGS);
            }
        });

        if (userData != null){
            txtViewUsername.setText(userData.getUserName());
            txtViewGender.setText("Gender: " + userData.getGender());
            txtViewAge.setText("Age: " + String.valueOf(calcAge(userData.getBirthdate())));
            imgViewProfileImage.setImageBitmap(userData.getProfileImage());
            ArrayList<WorkoutRecordModel> workoutRecordModelArrayList = myDatabaseHelper.getWorkoutRecord(null, spEmail);
            float totalWorkoutDuration = 0;
            int totalCalorie = 0;
            for (WorkoutRecordModel wrm: workoutRecordModelArrayList){
                totalWorkoutDuration += wrm.getWorkoutDuration();
                totalCalorie += wrm.getCalorieBurnt();
            }
            txtViewWorkoutTimeCounter.setText(String.valueOf(totalWorkoutDuration));
            txtViewCalorieCounter.setText(String.valueOf(totalCalorie));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                //Update user data
                initDatabase();
                setViews();
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }

    /**
     * This method is used to calculate age using the Date data
     * @param birthdate
     * @return
     */
    public int calcAge (Date birthdate) {

        Calendar dobCalendar = Calendar.getInstance();
        dobCalendar.setTime(birthdate);
        int ageInteger = 0;
        Calendar today = Calendar.getInstance();
        ageInteger = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) == dobCalendar.get(Calendar.MONTH)) {

            if (today.get(Calendar.DAY_OF_MONTH) < dobCalendar.get(Calendar.DAY_OF_MONTH)) {
                ageInteger = ageInteger - 1;
            }
        } else if (today.get(Calendar.MONTH) < dobCalendar.get(Calendar.MONTH)) {
            ageInteger = ageInteger - 1;
        }
        return ageInteger;
    }
}
