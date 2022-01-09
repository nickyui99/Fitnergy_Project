package com.rtn.fitnergy.workout;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.model.UserModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment_Congrat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment_Congrat extends Fragment {

    TextView pageTitle, titleContent, textCount;
    ImageView icon;
    Button doneBtn;
    SharedPreferences sharedPreferences;
    UserModel userData;
    String spEmail, spPassword;
    ConstraintLayout congrat, victory;
    VideoView victoryVid;
    MediaController mediaController;
    public static final String SHARED_PREFS = "user_session";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    public static int accFlag, gyroFlag;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutFragment_Congrat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment_Congrat.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment_Congrat newInstance(String param1, String param2) {
        WorkoutFragment_Congrat fragment = new WorkoutFragment_Congrat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout__congrat, container, false);

        pageTitle = (TextView) view.findViewById(R.id.mainText);
        titleContent = (TextView) view.findViewById(R.id.mainContent);
        textCount = (TextView) view.findViewById(R.id.countText);
        icon = (ImageView) view.findViewById(R.id.niceIcon);
        doneBtn = (Button) view.findViewById(R.id.doneBtn);
        congrat = (ConstraintLayout) view.findViewById(R.id.congratz);
        victory = (ConstraintLayout) view.findViewById(R.id.victory);
        victoryVid = (VideoView) view.findViewById(R.id.victoryVid);
        accelerometer = new Accelerometer(getActivity().getApplicationContext());
        gyroscope = new Gyroscope(getActivity().getApplicationContext());
        accFlag = 0;
        gyroFlag = 0;

        MyDatabaseHelper db = new MyDatabaseHelper(view.getContext());

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        spEmail = sharedPreferences.getString(EMAIL_KEY, null);
        spPassword = sharedPreferences.getString(PASSWORD_KEY, null);

        userData = db.getUserData(spEmail, spPassword);

        int cnt = db.getWorkoutCount(userData.getUserID());

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkoutFragment frag = new WorkoutFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, frag).commit();
            }
        });

        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTransition(float tx, float ty, float tz) {
                Log.d("Acc", "Acc: " + tx + " " + ty + " " + tz);
                if(tz > 20.0f){
                    accFlag = 1;
                    isVictory(view, cnt);
                }
            }
        });

        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                Log.d("Gyro", "Gyro: " + rx + " " + ry + " " + rz);
                if(rx < 1.0f && rx > -1.0f){
                    if(ry < 1.0f && ry > -1.0f){
                        gyroFlag = 1;
                        isVictory(view, cnt);
                    }
                }
            }
        });

        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.victory);
        victoryVid.setMediaController(mediaController);
        victoryVid.setVideoURI(uri);
        victoryVid.requestFocus();
        victoryVid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });



        victoryVid.start();

        return view;
    }
    
    public void isVictory(View view, int cnt){
        Log.d("Flag", "Flag: " + accFlag + " " + gyroFlag);
        if(accFlag == 1 && gyroFlag == 1){
            congrat.setVisibility(view.VISIBLE);
            victory.setVisibility(view.INVISIBLE);

            if(cnt%10 == 0){
                textCount.setText("You have worked with our workout plan for "+ cnt + " times. \nKeep it up!");
                textCount.setVisibility(view.VISIBLE);
            }
            accFlag = 0;
            gyroFlag = 0;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        accelerometer.register();
        gyroscope.register();
    }

    @Override
    public void onPause() {
        super.onPause();
        accelerometer.unregister();
        gyroscope.unregister();
    }
}