package com.rtn.fitnergy.profile;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rtn.fitnergy.R;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.model.UserModel;


import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettingActivity extends AppCompatActivity implements View.OnClickListener{

    private CircleImageView imgViewProfileImg;
    private Button btnChangeProfileImg;
    private EditText editTextUpdateName, editTextUpdateBirthdate, editTextHeight;
    private AutoCompleteTextView dropdownUpdateGender;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private MyDatabaseHelper myDatabaseHelper;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "user_session";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    private String spEmail, spPassword;
    private UserModel userData;
    public static final int PICK_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    private Bitmap bitmap = null;

    String name = "", gender = "";
    int height = 0;
    Date birthdate=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

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
    }

    private void initView() {
        imgViewProfileImg = (CircleImageView) findViewById(R.id.imageViewUpdateProfileImage);
        btnChangeProfileImg = (Button) findViewById(R.id.btnChangeProfilePicture);
        editTextUpdateName = (EditText) findViewById(R.id.editTextUpdateName);
        editTextUpdateBirthdate = (EditText) findViewById(R.id.editTextUpdateBirthdate);
        dropdownUpdateGender = findViewById(R.id.dropdownUpdateGender);
        editTextHeight = (EditText) findViewById(R.id.editTextUpdateHeight);

    }

    public void setView(){
        //get user data
        userData = myDatabaseHelper.getUserData(spEmail, spPassword);

        //set view
        imgViewProfileImg.setImageBitmap(userData.getProfileImage());

        editTextUpdateName.setText(userData.getUserName());

        editTextUpdateBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDateDialog();
            }
        });
        editTextUpdateBirthdate.setText(dateToString(userData.getBirthdate()));

        dropdownUpdateGender.setText(userData.getGender());
        String[] genderArray = getResources().getStringArray(R.array.gender_array);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_item, genderArray);
        dropdownUpdateGender.setAdapter(arrayAdapter);
        dropdownUpdateGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender = (String) parent.getItemAtPosition(position);
            }
        });

        editTextHeight.setText(String.valueOf(userData.getHeight()));
    }

    /**
     * This method is to call a date dialog to allow user input the date
     */
    private void inputDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                editTextUpdateBirthdate.setText(date);
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                R.style.MySpinnerDatePickerStyle,
                mDateSetListener,
                year,month,day);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnChangeProfilePicture:
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Select image from")
                        .setItems(R.array.change_profile_img, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        //open album to select image
                                        Intent intent = new Intent();
                                        intent.setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                                        break;
                                    case 1:
                                        //open camera to capture image
                                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        try {
                                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                        } catch (ActivityNotFoundException e) {
                                            // display error state to the user
                                        }
                                        break;
                                }
                            }
                        }).show();
                break;

            case R.id.buttonSaveProfile:
                String userName = editTextUpdateName.getText().toString();
                String userBirthdate = editTextUpdateBirthdate.getText().toString();
                String userGender = dropdownUpdateGender.getText().toString();
                int userheight = 0;
                try {
                    userheight = Integer.parseInt(editTextHeight.getText().toString());
                }
                catch (NumberFormatException e){
                    Log.e(TAG, e.toString());
                }

                if(userName.equals("") || userBirthdate.equals("") || userGender.equals("") || userheight == 0){
                    //To check the input details validation
                    Toast.makeText(this, "Input field cannot be empty", Toast.LENGTH_SHORT).show();
                }else {
                    //Save new profile details into the database
                    userData.setUserName(userName);
                    userData.setBirthdate(stringToDate(userBirthdate));
                    userData.setGender(userGender);
                    userData.setHeight(userheight);

                    if (myDatabaseHelper.updateUserProfile(null, userData)){
                        Toast.makeText(this, "Profile details updated", Toast.LENGTH_SHORT).show();
                        Intent returnSettingIntent = new Intent();
                        setResult(RESULT_OK, returnSettingIntent);
                        finish();
                    }
                    else {
                        Toast.makeText(this, "Profile details update fail", Toast.LENGTH_SHORT).show();

                    }
                }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    // Get the URI of the selected file
                    final Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //To store the image selected in temporary user model
                    userData.setProfileImage(bitmap);

                    imgViewProfileImg.setImageBitmap(bitmap);
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");

            //To store the image selected in temporary user model
            userData.setProfileImage(bitmap);

            imgViewProfileImg.setImageBitmap(bitmap);
        }
    }

    /**
     * This method is used to convert Date to String
     * @param date
     * @return
     */
    public String dateToString(Date date){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(date);
        return formattedDate;
    }

    /**
     * This method is to convert string to Date format
     * @param dateString
     * @return
     */
    public Date stringToDate(String dateString){
        try {
            SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormatter.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}