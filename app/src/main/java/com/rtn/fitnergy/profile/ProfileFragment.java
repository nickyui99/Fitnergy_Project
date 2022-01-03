package com.rtn.fitnergy.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rtn.fitnergy.R;


public class ProfileFragment extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserProfileFragment userProfileFragment = new UserProfileFragment();
        WeightFragment weightFragment = new WeightFragment();
        HistoryFragment historyFragment = new HistoryFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.userProfileFragment, userProfileFragment, "fragment_1");
        fragmentTransaction.add(R.id.weightFragment, weightFragment, "fragment_2");
        fragmentTransaction.add(R.id.historyFragment, historyFragment, "fragment_3");
        fragmentTransaction.commit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}