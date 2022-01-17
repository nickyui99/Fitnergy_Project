package com.rtn.fitnergy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.model.UserModel;
import com.rtn.fitnergy.profile.model.WeightModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName, editTextEmail, editTextBirthdate, editTextWeight, editTextHeight, editTextPassword, editTextConfirmPassword;
    private AutoCompleteTextView dropdownRegisterGender;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "user_session";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    private String name = "", email = "", gender = "", password = "", confirmPassword = "";
    private float weight = 0;
    private int height = 0;
    private Date birthdate = null;
    private UserModel addUserModel;
    private final static String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initSharedPref();
        initView();
        initIntentExtra();
    }

    private void initSharedPref() {
        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, this.MODE_PRIVATE);
    }

    /**
     * This method is to initiate the views in the activity_register.xml layout
     */
    public void initView(){

        editTextName = findViewById(R.id.editTextRegisterName);

        editTextEmail = findViewById(R.id.editTextRegisterEmail);

        editTextBirthdate = findViewById(R.id.editTextRegisterBirthdate);
        editTextBirthdate.setInputType(InputType.TYPE_NULL);    //To make sure the edit text only show date dialog only instead of keyboard
        editTextBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDateDialog();
            }
        });
        editTextWeight = findViewById(R.id.editTextRegisterWeight);

        editTextHeight = findViewById(R.id.editTextRegisterHeight);

        editTextPassword = findViewById(R.id.editTextRegisterPassword);

        editTextConfirmPassword = findViewById(R.id.editTextRegisterConfirmPassword);

        String[] genderArray = getResources().getStringArray(R.array.gender_array);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_item, genderArray);
        dropdownRegisterGender = findViewById(R.id.dropdownRegisterGender);
        dropdownRegisterGender.setAdapter(arrayAdapter);
        dropdownRegisterGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender = (String) parent.getItemAtPosition(position);
            }
        });
    }

    public void initIntentExtra(){
        Intent intent = getIntent();
        UserModel googleUser = intent.getParcelableExtra("userData");
        if (googleUser != null){
            googleRegistration(googleUser);
        }
    }

    private void googleRegistration(UserModel userModel) {
        editTextName.setText(userModel.getUserName());
        editTextEmail.setText(userModel.getUserEmail());
        editTextEmail.setEnabled(false);
        editTextPassword.setText(userModel.getUserPassword());
        editTextConfirmPassword.setText(userModel.getUserPassword());

        //Disable password field for registering google user
        editTextPassword.setVisibility(View.INVISIBLE);
        editTextConfirmPassword.setVisibility(View.INVISIBLE);
        TextInputLayout outlinedText6 = findViewById(R.id.outlinedTextRegister6);
        outlinedText6.setVisibility(View.INVISIBLE);
        TextInputLayout outlinedText7 = findViewById(R.id.outlinedTextRegister7);
        outlinedText7.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegistration:
                validateRegistration();
                break;
        }
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
                editTextBirthdate.setText(date);
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                R.style.MySpinnerDatePickerStyle,
                mDateSetListener,
                year,month,day);
        dialog.show();
    }

    /**
     * This method is to check whether the registration is complete or incomplete
     */
    public void validateRegistration(){
        //initiate variables
        name = editTextName.getText().toString();
        email = editTextEmail.getText().toString();
        birthdate = stringToDate(editTextBirthdate.getText().toString());
        try {
            weight = Float.parseFloat(editTextWeight.getText().toString());
        }catch (NumberFormatException e){
            Log.e(TAG, e.toString());
        }
        try {
            height = Integer.parseInt(editTextHeight.getText().toString());
        }catch (NumberFormatException e){
            Log.e(TAG, e.toString());
        }

        password = editTextPassword.getText().toString();
        confirmPassword = editTextConfirmPassword.getText().toString();

        //check empty inputs. if empty registration fail
        if (name.equals("")){
            Toast.makeText(this, "Please fill in your name", Toast.LENGTH_SHORT).show();
        }
        else if (email.equals("")){
            Toast.makeText(this, "Please fill in your email", Toast.LENGTH_SHORT).show();
        }
        else if (birthdate == null){
            Toast.makeText(this, "Please fill in your birthdate", Toast.LENGTH_SHORT).show();
        }
        else if (gender.equals("")){
            Toast.makeText(this, "Please fill in your gender", Toast.LENGTH_SHORT).show();
        }
        else if (weight == 0){
            Toast.makeText(this, "Please fill in your weight", Toast.LENGTH_SHORT).show();
        }
        else if (height == 0){
            Toast.makeText(this, "Please fill in your height", Toast.LENGTH_SHORT).show();
        }
        else if(password.equals("")){
            Toast.makeText(this, "Please fill in your password", Toast.LENGTH_SHORT).show();
        }
        else if (confirmPassword.equals("")){
            Toast.makeText(this, "Please fill in your confirm password", Toast.LENGTH_SHORT).show();
        }
        else{
            MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);

            //if the inputs are not empty, check the password and confirm password is the same
            if(!password.equals(confirmPassword)){
                //if the password not the same, registration failure
                Toast.makeText(this, "Please make sure your password and confirm password is the same", Toast.LENGTH_SHORT).show();

            }
            else if(!myDatabaseHelper.checkEmailValidity(email)){
                //To check email validity. If email exist in the database it will return false, else it return true
                Toast.makeText(this, "Your email exist in the database", Toast.LENGTH_SHORT).show();
            }
            else{
                //if the registration details valid
                addUserModel = new UserModel(-1, name, password, email, ((BitmapDrawable) getApplicationContext().getDrawable(R.drawable.blank_profile_pic)).getBitmap(), gender, height, birthdate, true, true, true);
                addUser(addUserModel);
                onRegistrationSuccess();
                Toast.makeText(this, "Registration success", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * This method is to add user into the sqlite database
     * @param userModel
     */
    public void addUser(UserModel userModel){
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.addUser(null, userModel);
        myDatabaseHelper.insertWeight(null, new WeightModel(userModel.getUserEmail(), getCurrentDateTime(), weight, weight));
    }

    /**
     * This method for user who register successfully
     * ->This method will save the user session in shared prefs and take user to the main activity;
     */
    public void onRegistrationSuccess(){
        SharedPreferences.Editor editor = sharedpreferences.edit();

        // below two lines will put values for
        // email and password in shared preferences.
        editor.putString(EMAIL_KEY, email);
        editor.putString(PASSWORD_KEY, password);

        // to save our data with key and value.
        editor.apply();

        //take user to main activity
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }

    public Date stringToDate(String dateString){
        try {
            SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy");
            return dateFormatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is to get current date
     *  @return Date
     **/
    public Date getCurrentDateTime(){
        return Calendar.getInstance().getTime();
    }
}