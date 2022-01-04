package com.rtn.fitnergy.workout;

import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    public static final String SHARED_PREFS = "user_session";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

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

        MyDatabaseHelper db = new MyDatabaseHelper(view.getContext());

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        spEmail = sharedPreferences.getString(EMAIL_KEY, null);
        spPassword = sharedPreferences.getString(PASSWORD_KEY, null);

        userData = db.getUserData(spEmail, spPassword);

        int cnt = db.getWorkoutCount(userData.getUserID());

        if(cnt%10 == 0){
            textCount.setText("You have worked with our workout plan for "+ cnt + " times. \nKeep it up!");
            textCount.setVisibility(view.VISIBLE);
        }

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkoutFragment frag = new WorkoutFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, frag).commit();
            }
        });

        return view;
    }
}