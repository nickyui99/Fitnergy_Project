package com.rtn.fitnergy;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.rtn.fitnergy.database.MyDatabaseHelper;
import com.rtn.fitnergy.profile.model.UserModel;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.ArrayList;
import java.util.TooManyListenersException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private GoogleSignInButton googleSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText editTextEmail, editTextPassword;
    private Button btnRegister, btnLogin;
    private MyDatabaseHelper myDatabaseHelper;
    private static final int RC_SIGN_IN = 1;
    private SharedPreferences sharedpreferences;    // variable for shared preferences.
    public static final String SHARED_PREFS = "user_session";   // creating constant keys for shared preferences.
    public static final String EMAIL_KEY = "email_key";     // key for storing email.
    public static final String PASSWORD_KEY = "password_key";   // key for storing password.
    public static final int SIGN_IN_SUCCESS = 1;
    public static final int SIGN_IN_FAIL = 0;
    String spEmail, spPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initSharedPref();
        initDatabase();
        initView();
        setGoogle();

    }

    public void initSharedPref(){
        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, this.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        spEmail = sharedpreferences.getString(EMAIL_KEY, null);
        spPassword = sharedpreferences.getString(PASSWORD_KEY, null);
    }

    public void initDatabase(){
        myDatabaseHelper = new MyDatabaseHelper(this);
    }

    public void initView(){
        editTextEmail = findViewById(R.id.editTextRegisterEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        googleSignInButton = findViewById(R.id.btn_googleSignin);
        googleSignInButton.setOnClickListener(this);

        btnLogin = findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(this);

        btnRegister = findViewById(R.id.btnSignin);
        btnRegister.setOnClickListener(this);
    }

    public void setGoogle(){
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_googleSignin:
                googleSignIn();
                break;
            case R.id.btnRegister:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.btnSignin:
                //normal signin
                signIn();
                break;
        }
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String personName = account.getDisplayName();
                String personEmail = account.getEmail();
                String password = account.getId(); //set account id to password

                if (!myDatabaseHelper.checkEmailValidity(personEmail)){
                    //if email is exist in database, sign in
                    onGoogleSignInSuccess(personEmail, password);
                }
                else {
                    //if email does not exist in database, register user
                    UserModel googleUser = new UserModel(-1, personName, password, personEmail, null, null, 0, null, true, true, true);
                    Intent registerGoogleUser = new Intent(this, RegisterActivity.class);
                    registerGoogleUser.putExtra("userData", googleUser);
                    startActivity(registerGoogleUser);
                }
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getApplicationContext(), "Sign in fail", Toast.LENGTH_SHORT).show();
        }
    }

    public void onGoogleSignInSuccess(String email, String password){

        Toast.makeText(getApplicationContext(), "Sign In Success", Toast.LENGTH_SHORT).show();

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

    //normal sign in
    public void signIn(){
        ArrayList<UserModel> userModelArrayList = myDatabaseHelper.getAllUser();
        int signInStatus = SIGN_IN_FAIL;
        for (UserModel userModel: userModelArrayList){
            if (editTextEmail.getText().toString().equals(userModel.getUserEmail()) && editTextPassword.getText().toString().equals(userModel.getUserPassword())) {
                onSignInSuccess();
                signInStatus = SIGN_IN_SUCCESS;
            }
        }
        if(signInStatus == SIGN_IN_FAIL){
            Toast.makeText(this, "Sign in fail", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method for user who login successfully using normal signin
     * ->This method will save the user session in shared prefs and take user to the main activity;
     */
    public void onSignInSuccess(){
        // Signed in successfully, show authenticated UI.
        Toast.makeText(getApplicationContext(), "Sign In Success", Toast.LENGTH_SHORT).show();

        SharedPreferences.Editor editor = sharedpreferences.edit();

        // below two lines will put values for
        // email and password in shared preferences.
        editor.putString(EMAIL_KEY, editTextEmail.getText().toString());
        editor.putString(PASSWORD_KEY, editTextPassword.getText().toString());

        // to save our data with key and value.
        editor.apply();

        //take user to main activity
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }

    /**
     * This is to check the saved user session in shared preferences.
     *      If the the shared preferences is not null, it will proceed direct login.
     *      Else, it will show the login page.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (spEmail != null && spPassword!=null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

}