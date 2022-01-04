package com.rtn.fitnergy.workout;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.R;
import com.rtn.fitnergy.profile.model.UserModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment implements CourseAdapter.ClickListener{

    //section para
    private ArrayList<Course> courseList;
    private RecyclerView courseRView;
    int i = 0;
    TextView recentName, recentDesc;
    Button recentBtn, resetBtn;

    SharedPreferences sharedPreferences;
    UserModel userData;
    String spEmail, spPassword;
    public static final String SHARED_PREFS = "user_session";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    int lastWork;
    MyDatabaseHelper db;
    Cursor cursor;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
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
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        courseRView = view.findViewById(R.id.recyclerViewforCourse);
        courseList = new ArrayList<>();
        db = new MyDatabaseHelper(view.getContext());

        recentName = (TextView) view.findViewById(R.id.recentWork_name);
        recentDesc = (TextView) view.findViewById(R.id.recentWork_desc);
        recentBtn = (Button) view.findViewById(R.id.btn_Start) ;

        cursor = db.getWorkout();

        String name;
        String desc;
        while(i < cursor.getCount()){
            name = cursor.getString(0).toString().trim();
            desc = cursor.getString(1).toString().trim();
            setCourse(name, desc);
            setAdapter();
            i++;
            cursor.moveToNext();
        }

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        spEmail = sharedPreferences.getString(EMAIL_KEY, null);
        spPassword = sharedPreferences.getString(PASSWORD_KEY, null);

        userData = db.getUserData(spEmail, spPassword);
        String userEmail = userData.getUserEmail();

        cursor = db.getLastWRec(userEmail);

        if (cursor == null){
            setRecent();

        } else {
            cursor.moveToNext();
            lastWork = cursor.getInt(0);
            recentName.setText(cursor.getString(1).toString().trim());
            recentDesc.setText(cursor.getString(2).toString().trim());
            recentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPosClick(lastWork-1);
                }
            });
        }

        resetBtn = view.findViewById(R.id.ResetRecord);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.resetRecord(userData.getUserID());
                setRecent();
            }
        });

        return view;
    }

    public void setCourse(String name, String desc) {
        courseList.add(new Course(name, desc));
    }

    public void setAdapter(){
       CourseAdapter adapter = new CourseAdapter(courseList, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        courseRView.setLayoutManager(layoutManager);
        courseRView.setItemAnimator(new DefaultItemAnimator());
        courseRView.setAdapter(adapter);
    }

    public void setRecent(){

        recentName.setText(courseList.get(0).getName());
        recentDesc.setText(courseList.get(0).getDesc());
        recentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPosClick(0);
            }
        });
    }

    @Override
    public void onPosClick(int position) {

        String name = courseList.get(position).getName();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("progStat", 1);
        WorkoutFragment_Exe fragExe = new WorkoutFragment_Exe();
        fragExe.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragExe).commit();
    }
}