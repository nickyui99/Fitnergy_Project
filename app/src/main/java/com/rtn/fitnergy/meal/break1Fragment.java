package com.rtn.fitnergy.meal;

import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rtn.fitnergy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link break1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class break1Fragment extends Fragment {
Button backbf;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    public break1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment break1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static break1Fragment newInstance(String param1, String param2) {
        break1Fragment fragment = new break1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_break1, container, false);

        Button backbf = (Button) view.findViewById(R.id.backbf1);
        backbf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakfastFragment bffragCong = new breakfastFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, bffragCong).commit();
            }
        });
  return view;
    }
}