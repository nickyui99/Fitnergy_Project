package com.rtn.fitnergy.profile;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.model.UserModel;
import com.rtn.fitnergy.profile.model.WeightModel;

import java.util.ArrayList;

public class WeightFragment extends Fragment{
    private MyDatabaseHelper myDatabaseHelper;
    private WeightModel weightModel;
    private UserModel userModel;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "user_session";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    private String spEmail, spPassword;

    private LinearLayout linearLayoutBmiCategory;
    private TextView txtViewBmiCounter, txtViewBmiCategory, txtViewPreviousCounter, txtViewCurrentCounter, txtViewChangeCounter;
    private Button btnUpdateTodayWeight;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_user_weight, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        initSharedPref();
        initDatabase();
        initViews();
        setViews();
    }

    public void initSharedPref(){
        // getting the data which is stored in shared preferences.
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        spEmail = sharedPreferences.getString(EMAIL_KEY, null);
        spPassword = sharedPreferences.getString(PASSWORD_KEY, null);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
    }


    public void initDatabase(){
        weightModel = myDatabaseHelper.getWeight(spEmail);
        userModel = myDatabaseHelper.getUserData(spEmail, spPassword);
    }

    public void initViews(){

        linearLayoutBmiCategory = (LinearLayout) getView().findViewById(R.id.linearLayoutBmiCategory);
        txtViewBmiCounter = (TextView) getView().findViewById(R.id.txtViewBmiCounter);
        txtViewBmiCategory = (TextView) getView().findViewById(R.id.txtViewBmiCategory);
        txtViewPreviousCounter = (TextView) getView().findViewById(R.id.UW_txtViewPreviousCounter);
        txtViewCurrentCounter = (TextView) getView().findViewById(R.id.UW_txtViewCurrentCounter);
        txtViewChangeCounter = (TextView) getView().findViewById(R.id.UW_txtViewChangeCounter);
        btnUpdateTodayWeight = (Button) getView().findViewById(R.id.UW_btnUpdateWeight);

    }

    public void setViews() {
        float previousWeight = weightModel.getPreviousWeight();
        float currentWeight = weightModel.getCurrentWeight();
        float weightChange = weightModel.getWeightChange();
        float height = userModel.getHeight();
        float bmi = (currentWeight/height/height) *10000; //calculate bmi
        String str_weightChange = "";

        txtViewBmiCounter.setText(String.format( "%.1f",bmi));
        txtViewBmiCategory.setText(checkBmiCategory(bmi));
        if (weightChange < 0){
            //if the weight change is negative then set text color as green
            txtViewChangeCounter.setTextColor(ContextCompat.getColor(getContext(), R.color.success));
            str_weightChange = String.valueOf(weightChange);
        }else if (weightChange > 0 ){
            //if the weight change is positive then set text color as red
            txtViewChangeCounter.setTextColor(ContextCompat.getColor(getContext(), R.color.alert));
            str_weightChange = "+" + String.valueOf(weightChange);

        }
        txtViewPreviousCounter.setText(String.valueOf(previousWeight));
        txtViewCurrentCounter.setText(String.valueOf(currentWeight));
        txtViewChangeCounter.setText(str_weightChange);

        btnUpdateTodayWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateWeightDialog();
            }
        });
    }

    /**
     * This method is used to check bmi category of the user
     * @param bmi
     * @return
     */
    private String checkBmiCategory(float bmi){
        if (bmi > 0 && bmi < 18.5){
            linearLayoutBmiCategory.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.harmful));
            return "Underweight";
        }else if (bmi <= 24.9){
            linearLayoutBmiCategory.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.safe));
            return "Normal weight";
        }else if (bmi <= 29.9){
            linearLayoutBmiCategory.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.harmful));
            return "Overweight";
        } else if (bmi >= 30){
            linearLayoutBmiCategory.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.dangerous));
            return "Obesity";
        } else {
            return "Invalid bmi";
        }
    }

    /**
     * This method is used to show a dialog that allows user to input current weight
     */
    public void showUpdateWeightDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_update_weight, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView)
                .setTitle("Update Weight")
                // Add action buttons
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editTextUpdateWeight = (EditText) dialogView.findViewById(R.id.editTextUpdateWeight);
                        float currentWeight = Float.parseFloat(editTextUpdateWeight.getText().toString());
                        weightModel.setPreviousWeight(weightModel.getCurrentWeight());
                        weightModel.setCurrentWeight(currentWeight);

                        myDatabaseHelper.updateWeight(null, weightModel);
                        setViews();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog updateWeightDialog = builder.create();
        updateWeightDialog.show();
    }

}

