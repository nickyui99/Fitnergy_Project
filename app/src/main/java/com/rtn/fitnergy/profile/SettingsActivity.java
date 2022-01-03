package com.rtn.fitnergy.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.rtn.fitnergy.LoginActivity;
import com.rtn.fitnergy.R;


public class SettingsActivity extends AppCompatActivity {

    private ListView settingListView;
    private LinearLayout myProfileLayout, privacyLayout, logoutLayout, privacyPolicyLayout;
    private SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "user_session";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    String spEmail, spPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initSharedPref();
        initView();
        setView();

    }

    private void initSharedPref() {
        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, this.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        spEmail = sharedpreferences.getString(EMAIL_KEY, null);
    }

    private void initView() {
        myProfileLayout = (LinearLayout) findViewById(R.id.linearLayoutMyProfile);
        privacyLayout = (LinearLayout) findViewById(R.id.linearLayoutPrivacy);
        logoutLayout = (LinearLayout) findViewById(R.id.linearLayoutLogout);
        privacyPolicyLayout = (LinearLayout) findViewById(R.id.linearLayoutPrivacyPolicy);
    }

    private void setView() {
        myProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myProfileIntent = new Intent(getApplicationContext(), ProfileSettingActivity.class);
                startActivityForResult(myProfileIntent, 1);
            }
        });

        privacyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent privacyIntent = new Intent(getApplicationContext(), PrivacySettingActivity.class);
                startActivity(privacyIntent);
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calling method to edit values in shared prefs.
                SharedPreferences.Editor editor = sharedpreferences.edit();

                // below line will clear
                // the data in shared prefs.
                editor.clear();

                // below line will apply empty
                // data to shared prefs.
                editor.apply();

                Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutIntent);
                finish();
            }
        });

        privacyPolicyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.freeprivacypolicy.com/live/14e0fe50-c8dd-40e3-897c-eb7287984bcd"));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                //Update user data
                Intent returnMainProfile = new Intent();
                setResult(RESULT_OK, returnMainProfile);
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }
}