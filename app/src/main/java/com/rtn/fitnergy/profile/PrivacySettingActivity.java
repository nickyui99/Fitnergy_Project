package com.rtn.fitnergy.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.widget.Switch;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.model.UserModel;

public class PrivacySettingActivity extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "user_session";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    private String spEmail, spPassword;
    UserModel userModel;

    private Switch switchProfileVisibility, switchSocialCircle, switchPrivateMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_setting);

        initSharedPref();
        initDatabase();
        initView();
        setView();
    }

    public void initSharedPref(){
        // getting the data which is stored in shared preferences.
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        spEmail = sharedPreferences.getString(EMAIL_KEY, null);
        spPassword = sharedPreferences.getString(PASSWORD_KEY, null);
    }

    public void initDatabase(){

        myDatabaseHelper = new MyDatabaseHelper(this);
        userModel = myDatabaseHelper.getUserData(spEmail, spPassword);
    }

    public void initView(){
        switchProfileVisibility = (Switch) findViewById(R.id.switchProfileVisibility);
        switchSocialCircle = (Switch) findViewById(R.id.switchSocialCircle);
        switchPrivateMode = (Switch) findViewById(R.id.switchPrivateMode);
    }

    public void setView(){
        if (userModel.isProfileVisibilitySetting()){
            switchProfileVisibility.setChecked(true);
        }
        if (userModel.isSocialCircleSetting()){
            switchSocialCircle.setChecked(true);
        }
        if (userModel.isPrivateModeSetting()){
            switchPrivateMode.setChecked(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //Update the data in the database
        userModel.setProfileVisibilitySetting(switchProfileVisibility.isChecked());
        userModel.setPrivateModeSetting(switchPrivateMode.isChecked());
        userModel.setSocialCircleSetting(switchSocialCircle.isChecked());
        myDatabaseHelper.updateUserProfile(null, userModel);
    }

}