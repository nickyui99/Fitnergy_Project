package com.rtn.fitnergy.workout;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import com.rtn.fitnergy.profile.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment_Exe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment_Exe extends Fragment {

    // Section Para
    private ProgressBar progressBar;
    private TextView workCount, workTitle, workInst;
    private Button btn_Next;
    private YouTubePlayerView utubePV;

    private int progressStatus;
    private int progressMax;
    private String progressCnt;
    private static int id;

    SharedPreferences sharedPreferences;
    UserModel userData;
    String spEmail, spPassword;
    public static final String SHARED_PREFS = "user_session";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    private ArrayList<CourseDetail> courseDetList;

    private Handler handler = new Handler();
    MyDatabaseHelper db;
    Cursor cursor;

    //----------------

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutFragment_Exe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment_Exe.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment_Exe newInstance(String param1, String param2) {
        WorkoutFragment_Exe fragment = new WorkoutFragment_Exe();
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
        View view = inflater.inflate(R.layout.fragment_workout__exe, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.workProgress);
        workCount = (TextView) view.findViewById(R.id.workProNum);
        btn_Next = (Button) view.findViewById(R.id.btn_Next);
        workTitle = (TextView) view.findViewById(R.id.tV_workTitle);
        workInst = (TextView) view.findViewById(R.id.tV_workInst);
        utubePV = (YouTubePlayerView) view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(utubePV);
        courseDetList = new ArrayList<>();
        db = new MyDatabaseHelper(view.getContext());

        String workoutName = getArguments().getString("name");

        progressStatus = getArguments().getInt("progStat");

        if(progressStatus == 1){
            exeDet(workoutName);
            Log.d("Click", "Prog Stat = 1");
        } else {
            Log.d("Click", "Test progStat != 1");
            progressMax = getArguments().getInt("progMax");
            courseDetList = (ArrayList<CourseDetail>) getArguments().getSerializable("work");
            if(progressStatus == progressMax){
                btn_Next.setText("Finish");
            }
        }

        //progress bar
        progressBar.setMax(progressMax);
        progressCnt = progressStatus + "/" + progressMax;
        workCount.setText(progressCnt);
        progressBar.setProgress(progressStatus);
        Log.d("Click", "Prog Bar initialize");

        //set content
        int i =  progressStatus - 1;
        Log.d("Click", "Title: "+ courseDetList.get(i).getWork() + " Instruction: " + courseDetList.get(i).getInst() + " Video: " + courseDetList.get(i).getUrl());

        workTitle.setText(courseDetList.get(i).getWork());
        workInst.setText(courseDetList.get(i).getInst());
        Log.d("Click", "Text Set");



        utubePV.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {

                //  youTubePlayer.addListener(YouTubePlayerFullScreenListener fullScreenListener);
               // utubePV.addFullScreenListener();
                String videoId = courseDetList.get(i).getUrl();
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
        Log.d("Click", "Utube Set");

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(progressStatus < progressMax){
                    progressStatus++;
                    nxtAct();
                } else if(progressStatus == progressMax){
                    sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    spEmail = sharedPreferences.getString(EMAIL_KEY, null);
                    spPassword = sharedPreferences.getString(PASSWORD_KEY, null);

                    userData = db.getUserData(spEmail, spPassword);
                    String userEmail = userData.getUserEmail();
                    int userID = userData.getUserID();

                    endAct(userEmail, userID);
                }
            }
        });

        return view;
    }

    public void exeDet(String workoutName){

        String work;
        String inst;
        String vidID;
        cursor = db.getWorkoutID(workoutName);
        id = cursor.getInt(0);
        Log.d("Click", "code initialized");

        cursor = db.getWorkoutDet(id);

        progressMax = cursor.getCount();
        for(int i = 0; i < progressMax; i++){
            work = cursor.getString(0).toString().trim();
            inst = cursor.getString(1).toString().trim();
            vidID= cursor.getString(2).toString().trim();

            setCourseDet(work, inst, vidID);
            Log.d("Count: "+ progressMax + " Click", work + " " + inst + " " + vidID);
            cursor.moveToNext();
        }
    }

    public void setCourseDet(String work, String inst, String url){
        courseDetList.add(new CourseDetail(work, inst, url));
        //courseDetList.add(new CourseDetail(data.workoutName, data.workoutInstruc, data.videoID));
    }

    public void nxtAct(){
        Bundle bundle = new Bundle();
        bundle.putInt("progStat", progressStatus);
        bundle.putInt("progMax", progressMax);
        bundle.putSerializable("work", courseDetList);
        WorkoutFragment_Exe fragExe = new WorkoutFragment_Exe();
        fragExe.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragExe).commit();
        Log.d("Click", "nxtAct " + progressStatus);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void endAct(String userEmail, int userID){

        android.icu.text.SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(new Date());
        Log.d("Click", "UserID: "+ userEmail + " Date: " + today);
        db.addWorkoutRecord(userEmail, id, today, userID);

        WorkoutFragment_Congrat fragCong = new WorkoutFragment_Congrat();
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragCong).commit();
        Log.d("Click", "endAct");
    }


}