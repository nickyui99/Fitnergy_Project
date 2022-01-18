package com.rtn.fitnergy.meal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rtn.fitnergy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link lunchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class lunchFragment extends Fragment {
    ImageView imageView5, imageView6;
    Button lunch;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public lunchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment lunchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static lunchFragment newInstance(String param1, String param2) {
        lunchFragment fragment = new lunchFragment();
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
        View view = inflater.inflate(R.layout.fragment_lunch, container, false);

        ImageView imageView5 = (ImageView) view.findViewById(R.id.imageView5);
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunch1Fragment fragCong = new lunch1Fragment();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragCong).commit();
            }
        });
        ImageView imageView6 = (ImageView) view.findViewById(R.id.imageView6);
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunch2Fragment fragCong = new lunch2Fragment();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragCong).commit();
            }
        });

        Button lunch = (Button) view.findViewById(R.id.button2);
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealFragment fragCong = new MealFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragCong).commit();
            }
        });

        return view;
    }
}