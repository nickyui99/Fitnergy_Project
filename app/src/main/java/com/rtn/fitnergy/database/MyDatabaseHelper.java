package com.rtn.fitnergy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.rtn.fitnergy.profile.model.UserModel;
import com.rtn.fitnergy.profile.model.WeightModel;
import com.rtn.fitnergy.profile.model.WorkoutRecordModel;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Fitnergy.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Article";


    //User Table
    public static final String USER_TABLE_NAME = "User";
    public static final String USER_COLUMN_USERID = "userID";
    public static final String USER_COLUMN_USERNAME = "userName";
    public static final String USER_COLUMN_USERPASSWORD = "userPassword";
    public static final String USER_COLUMN_USEREMAIL = "userEmail";
    public static final String USER_COLUMN_PROFILEIMAGE = "profileImage";
    public static final String USER_COLUMN_GENDER = "gender";
    public static final String USER_COLUMN_HEIGHT = "height";
    public static final String USER_COLUMN_BIRTHDATE = "birthdate";
    public static final String USER_COLUMN_PROFILEVISIBILITYSETTING = "profileVisibilitySetting";
    public static final String USER_COLUMN_SOCIALCIRCLESETTING = "socialCircleSetting";
    public static final String USER_COLUMN_PRIVATEMODESETTING = "privateModeSetting";

    //Weight Table
    public static final String WEIGHT_TABLE_NAME = "Weight";
    public static final String WEIGHT_COLUMN_WEIGHTID = "weightID";
    public static final String WEIGHT_COLUMN_DATE = "date";
    public static final String WEIGHT_COLUMN_PREVIOUSWEIGHT = "previousWeight";
    public static final String WEIGHT_COLUMN_CURRENTWEIGHT = "currentWeight";

    //Workout Table
    public static final String WORKOUT_TABLE_NAME = "Workout";
    public static final String WORKOUT_COLUMN_ID = "workoutID";
    public static final String WORKOUT_COLUMN_NAME = "workoutName";
    public static final String WORKOUT_COLUMN_DESC = "workoutDesc";
    public static final String WORKOUT_COLUMN_CALORIES = "workoutCalories";
    public static final String WORKOUT_COLUMN_DURATION = "workoutDuration";

    //WorkoutRecord Table
    public static final String WORKOUT_RECORD_TABLE_NAME = "WorkoutRecord";
    public static final String WR_COLUMN_ID = "recordID";
    public static final String WR_COLUMN_WORKOUTDATE = "workoutDate";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String userTable = "CREATE TABLE " + USER_TABLE_NAME + " (" +
                        USER_COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        USER_COLUMN_USERNAME + " TEXT NOT NULL, " +
                        USER_COLUMN_USERPASSWORD + " TEXT NOT NULL, " +
                        USER_COLUMN_USEREMAIL + " TEXT NOT NULL, " +
                        USER_COLUMN_PROFILEIMAGE + " BLOB, " +
                        USER_COLUMN_GENDER + " TEXT NOT NULL, " +
                        USER_COLUMN_HEIGHT + " INTEGER NOT NULL, " +
                        USER_COLUMN_BIRTHDATE + " TEXT NOT NULL, " +
                        USER_COLUMN_PROFILEVISIBILITYSETTING + " INTEGER NOT NULL, " +
                        USER_COLUMN_SOCIALCIRCLESETTING + " INTEGER NOT NULL, " +
                        USER_COLUMN_PRIVATEMODESETTING + " INTEGER NOT NULL " +
                        "); ";

        String weightTable = "CREATE TABLE " + WEIGHT_TABLE_NAME + " (" +
                        WEIGHT_COLUMN_WEIGHTID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        USER_COLUMN_USEREMAIL + " TEXT NOT NULL, " +
                        WEIGHT_COLUMN_DATE + " TEXT, " +
                        WEIGHT_COLUMN_PREVIOUSWEIGHT + " REAL, " +
                        WEIGHT_COLUMN_CURRENTWEIGHT + " REAL, " +
                        "FOREIGN KEY (" + USER_COLUMN_USEREMAIL +  ") " +
                        "REFERENCES " + USER_TABLE_NAME + " (" + USER_COLUMN_USEREMAIL + ")); ";

        String workoutTable = "CREATE TABLE " + WORKOUT_TABLE_NAME + " (" +
                WORKOUT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORKOUT_COLUMN_NAME + " TEXT NOT NULL, " +
                WORKOUT_COLUMN_DESC + " TEXT NOT NULL, " +
                WORKOUT_COLUMN_CALORIES + " INTEGER NOT NULL, " +
                WORKOUT_COLUMN_DURATION + " REAL NOT NULL);";

        String workoutRecordTable = "CREATE TABLE " + WORKOUT_RECORD_TABLE_NAME + " (" +
                WR_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_COLUMN_USEREMAIL + " TEXT NOT NULL, " +
                WORKOUT_COLUMN_ID + " INTEGER NOT NULL, " +
                WR_COLUMN_WORKOUTDATE + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + USER_COLUMN_USEREMAIL + ")" +
                "REFERENCES " + USER_TABLE_NAME + " (" + USER_COLUMN_USEREMAIL + ")," +
                "FOREIGN KEY (" + WORKOUT_COLUMN_ID + ")" +
                "REFERENCES " + WORKOUT_TABLE_NAME + " (" + WORKOUT_COLUMN_ID + "));";


//        String articleTable = "CREATE TABLE " + TABLE_NAME +
//                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_TITLE + " TEXT, " +
//                COLUMN_DESC + " INTEGER);";

        //Article Table
        String articleTable = "CREATE TABLE Article (" +
                "articleID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "articleTitle TEXT NOT NULL, " +
                "articleDesc TEXT NOT NULL, " +
                "articlePicture TEXT NOT NULL, " +
                "articleLink TEXT NOT NULL"+
                ");";
        String articleStoreTable = "CREATE TABLE ArticleStore (" +
                "articleStID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "articleStTitle TEXT NOT NULL, " +
                "articleStDesc TEXT NOT NULL, " +
                "articleStPicture TEXT NOT NULL, " +
                "articleStLink TEXT NOT NULL, "+
                "articleStCategory TEXT NOT NULL"+
                ");";

        String favouriteTable = "CREATE TABLE Favourite ("+
                "articleID INTEGER NOT NULL, " +
                "userID INTEGER NOT NULL, " +
                "FOREIGN KEY (articleID) " +
                "REFERENCES Article (articleID), " +
                "FOREIGN KEY (userID) " +
                "REFERENCES User (userID), " +
                "PRIMARY KEY(articleID, userID)" +
                ");";

        db.execSQL(userTable);
        db.execSQL(weightTable);
        db.execSQL(workoutTable);
        db.execSQL(workoutRecordTable);
        db.execSQL(articleTable);
        db.execSQL(articleStoreTable);
        db.execSQL(favouriteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    //TODO: This part is created by Nicholas

    /**
     * This method add new user into the user table in the database.
     **/
    public boolean addUser(SQLiteDatabase db, UserModel userModel){
        if (db == null) {
            db = this.getWritableDatabase();
        }
        ContentValues cv = new ContentValues();
        cv.put(USER_COLUMN_USERNAME, userModel.getUserName());
        cv.put(USER_COLUMN_USERPASSWORD, userModel.getUserPassword());
        cv.put(USER_COLUMN_USEREMAIL, userModel.getUserEmail());
        cv.put(USER_COLUMN_PROFILEIMAGE, convertBitmaptoByteArray(userModel.getProfileImage()));
        cv.put(USER_COLUMN_GENDER, userModel.getGender());
        cv.put(USER_COLUMN_HEIGHT, userModel.getHeight());
        cv.put(USER_COLUMN_BIRTHDATE, dateToString(userModel.getBirthdate()));

        if(userModel.isProfileVisibilitySetting()){
            //if true
            cv.put(USER_COLUMN_PROFILEVISIBILITYSETTING, 1);
        } else{
            //if false
            cv.put(USER_COLUMN_PROFILEVISIBILITYSETTING, 0);
        }
        if(userModel.isSocialCircleSetting()){
            //if true
            cv.put(USER_COLUMN_SOCIALCIRCLESETTING, 1);
        } else{
            //if false
            cv.put(USER_COLUMN_SOCIALCIRCLESETTING, 0);
        }
        if(userModel.isPrivateModeSetting()){
            //if true
            cv.put(USER_COLUMN_PRIVATEMODESETTING, 1);
        }else{
            //if false
            cv.put(USER_COLUMN_PRIVATEMODESETTING, 0);
        }

        long insert = db.insert(USER_TABLE_NAME, null, cv);
        return insert != 1;
    }


    /**
     * This method update user profile details
     * @param db
     * @param userData
     * @return
     */
    public boolean updateUserProfile (SQLiteDatabase db, UserModel userData){
        if (db == null) {
            db = this.getWritableDatabase();
        }
        ContentValues cv = new ContentValues();
        cv.put(USER_COLUMN_USERNAME, userData.getUserName());
        cv.put(USER_COLUMN_BIRTHDATE, dateToString(userData.getBirthdate()));
        cv.put(USER_COLUMN_GENDER, userData.getGender());
        cv.put(USER_COLUMN_PROFILEIMAGE, convertBitmaptoByteArray(userData.getProfileImage()));
        cv.put(USER_COLUMN_PROFILEVISIBILITYSETTING, userData.isProfileVisibilitySetting());
        cv.put(USER_COLUMN_SOCIALCIRCLESETTING, userData.isSocialCircleSetting());
        cv.put(USER_COLUMN_PRIVATEMODESETTING, userData.isPrivateModeSetting());

        long updateStatus = db.update(USER_TABLE_NAME, cv, USER_COLUMN_USEREMAIL + " = ?", new String[] {userData.getUserEmail()});
        if (updateStatus == 1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * This method get an array list of user in the database
     * @return
     */

    public ArrayList<UserModel> getAllUser(){
        ArrayList<UserModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + USER_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        UserModel returnUserModel;

        if(cursor.moveToFirst()) {
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String userPassword = cursor.getString(2);
                String userEmail = cursor.getString(3);
                byte[] profileImage = cursor.getBlob(4);
                String gender = cursor.getString(5);
                int height = cursor.getInt(6);
                String birthdate = cursor.getString(7);
                boolean profileVisibilitySetting = cursor.getInt(8) == 1 ? true: false;
                boolean socialCircleSetting = cursor.getInt(9)== 1 ? true: false;
                boolean privateModeSetting = cursor.getInt(10)== 1 ? true: false;
                returnList.add(new UserModel(userID, userName, userPassword, userEmail, convertByteArraytoBitmap(profileImage), gender, height, stringToDate(birthdate), profileVisibilitySetting, socialCircleSetting, privateModeSetting));
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    /**
     * This method is used to get user data from the database
     * @param userEmail
     * @param password
     * @return UserModel
     */
    public UserModel getUserData(String userEmail, String password){
        ArrayList<UserModel> userModelArrayList = getAllUser();
        UserModel returnUserModel = null;
        for(UserModel userModel: userModelArrayList){
            if(userModel.getUserEmail().equals(userEmail) && userModel.getUserPassword().equals(password)){
                returnUserModel = userModel;
            }
        }
        return returnUserModel;
    }

    /**
     * This method is used to check the email validity
     *  If the email exist in the database, return false
     *  else return true.
     * @param userEmail
     * @return
     */
    public boolean checkEmailValidity(String userEmail){
        ArrayList<UserModel> userModelArrayList = getAllUser();
        for(UserModel userModel: userModelArrayList){
            if(userModel.getUserEmail().equals(userEmail)){
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to insert user's weight
     * @param db
     * @param weightModel
     */
    public void insertWeight(SQLiteDatabase db, WeightModel weightModel){
        if (db == null) {
            db = this.getWritableDatabase();
        }

        String insertWeight = "insert into " +WEIGHT_TABLE_NAME+ " values(" +
                "NULL, '"+
                weightModel.getUserEmail() + "', '" +
                dateToString(weightModel.getDate()) + "', '" +
                weightModel.getPreviousWeight() + "', '" +
                weightModel.getCurrentWeight() +  "')";

        db.execSQL(insertWeight);
    }

    /**
     * This method is used to update user's weight
     * @param db
     * @param weightModel
     * @return
     */
    public boolean updateWeight(SQLiteDatabase db, WeightModel weightModel){
        if (db == null) {
            db = this.getWritableDatabase();
        }
        ContentValues cv = new ContentValues();
        cv.put(WEIGHT_COLUMN_PREVIOUSWEIGHT, weightModel.getPreviousWeight());
        cv.put(WEIGHT_COLUMN_CURRENTWEIGHT, weightModel.getCurrentWeight());

        long updateStatus = db.update(WEIGHT_TABLE_NAME, cv, USER_COLUMN_USEREMAIL + " = ?", new String[] {weightModel.getUserEmail()});
        if (updateStatus == 1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * This method is used to get user's weight according to user's email
     * @param userEmail
     * @return
     */
    public WeightModel getWeight(String userEmail){
        ArrayList<WeightModel> weightModelArrayList = new ArrayList<>();
        ArrayList<WeightModel> userWeightData = new ArrayList<>();
        String queryString = "SELECT * FROM " + WEIGHT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        WeightModel returnWeightModel = null;

        if(cursor.moveToFirst()) {
            do {
                int getWeightID = cursor.getInt(0);
                String getUserEmail = cursor.getString(1);
                Date getDate = stringToDate(cursor.getString(2));
                float getPreviousWeight = cursor.getFloat(3);
                float getCurrentWeight = cursor.getFloat(4);
                weightModelArrayList.add(new WeightModel(getUserEmail, getDate, getPreviousWeight, getCurrentWeight));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        for (WeightModel weightModel: weightModelArrayList){
            if (weightModel.getUserEmail().equals(userEmail)){
                returnWeightModel = weightModel;
            }
        }

        return returnWeightModel;
    }

    public void insertSampleWorkout(SQLiteDatabase db){
        if (db == null) {
            db = this.getWritableDatabase();
        }

        String insertWeight = "insert into " +WORKOUT_TABLE_NAME+ " values(NULL, 'Plan 1','Testing','400', '30');";
        db.execSQL(insertWeight);

        insertWeight = "insert into " +WORKOUT_TABLE_NAME+ " values(NULL, 'Plan 2','Testing','500', '30');";
        db.execSQL(insertWeight);

        insertWeight = "insert into " +WORKOUT_TABLE_NAME+ " values(NULL, 'Plan 3','Testing','500', '30');";
        db.execSQL(insertWeight);
    }

    public void insertSampleWorkoutRecord(SQLiteDatabase db){
        if (db == null) {
            db = this.getWritableDatabase();
        }

        String insertWeight = "insert into " +WORKOUT_RECORD_TABLE_NAME+ " values(NULL, 'test@mail.com','1','20/Dec/2021');";
        db.execSQL(insertWeight);

        insertWeight = "insert into " +WORKOUT_RECORD_TABLE_NAME+ " values(NULL, 'test@mail.com','2','20/Dec/2021');";
        db.execSQL(insertWeight);

        insertWeight = "insert into " +WORKOUT_RECORD_TABLE_NAME+ " values(NULL, 'test@mail.com','3','20/Dec/2021');";
        db.execSQL(insertWeight);
    }

    /**
     * This method will return Workout Record Model that stores user's workout record details
     * @param db
     * @param wrm
     * @return
     */
    public WorkoutRecordModel getWorkoutPlanDetails(SQLiteDatabase db, WorkoutRecordModel wrm){
        String queryString = "SELECT " + WORKOUT_COLUMN_NAME + ", " + WORKOUT_COLUMN_DURATION + ", " +  WORKOUT_COLUMN_CALORIES + " FROM " + WORKOUT_TABLE_NAME + " WHERE " + WORKOUT_COLUMN_ID + " = '" + wrm.getWorkoutID() + "'";
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            String workoutPlanName = cursor.getString(0);
            float workoutDuration = cursor.getFloat(1);
            int calorie = cursor.getInt(2);
            wrm.setWorkoutPlanName(workoutPlanName);
            wrm.setCalorieBurnt(calorie);
            wrm.setWorkoutDuration(workoutDuration);
        }
        cursor.close();
        return wrm;
    }

    /**
     * This method is used to get workout record that record past workout plan
     * that has taken by user
     * @param db
     * @param userEmail
     * @return
     */
    public ArrayList<WorkoutRecordModel> getWorkoutRecord(SQLiteDatabase db, String userEmail){
        if (db == null) {
            db = this.getReadableDatabase();
        }
        ArrayList<WorkoutRecordModel> workoutRecordModelArrayList = new ArrayList<>();
        String queryString = "SELECT * FROM " + WORKOUT_RECORD_TABLE_NAME + " WHERE " + USER_COLUMN_USEREMAIL + " = '" + userEmail + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        WorkoutRecordModel workoutRecordModel;

        if(cursor.moveToFirst()) {
            do {
                int getWorkoutRecordID = cursor.getInt(0);
                String getUserEmail = cursor.getString(1);
                int getWorkoutID = cursor.getInt(2);
                Date getWorkoutDate = stringToDate(cursor.getString(3));
                workoutRecordModel = new WorkoutRecordModel(getWorkoutID, getWorkoutDate);
                workoutRecordModelArrayList.add(workoutRecordModel);
            }while (cursor.moveToNext());
        }
        cursor.close();

        for(WorkoutRecordModel wrm: workoutRecordModelArrayList){
            wrm = getWorkoutPlanDetails(db, wrm);
        }
        return workoutRecordModelArrayList;
    }

    /**
     * This method convert bitmap image to byte array
     * @param bitmap
     * @return byte[]
     **/
    public static byte[] convertBitmaptoByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * This method convert byte array to bitmap
     * @param image
     * @return bitmap
     */
    public static Bitmap convertByteArraytoBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /**
     * This method is to get current date
     *  @param
     *  @return Date
     **/
    public Date getCurrentDateTime(){
        Date c = Calendar.getInstance().getTime();
        return c;
    }

    /**
     * This method is used to convert Date to String
     * @param date
     * @return
     */
    public String dateToString(Date date){
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        String formattedDate = df.format(date);
        return formattedDate;
    }

    /**
     * This method is to convert String date to Date date
     *  @param date
     *  @return Date
     **/
    public Date stringToDate(String date){
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MMM/yyyy");
        Date stringDate = null;
        try {
            stringDate = simpledateformat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stringDate;
    }

    //TODO: This part is created by Ronald

    public void addArticle(String title, String desc,String imgLink, String link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("articleTitle", title);
        cv.put("articleDesc", desc);
        cv.put("articlePicture", imgLink);
        cv.put("articleLink", link);

        long result = db.insert("Article", null, cv);


        if (result != -1) {
            Toast.makeText(context, "Added to favourites!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to add to favourites..", Toast.LENGTH_LONG).show();
        }
    }

    public Cursor readFavData(){

        String query= "SELECT * FROM Article";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readArticleStoreData(String category){
        String query= "SELECT * FROM ArticleStore WHERE articleStCategory LIKE '"+category+"';";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    public void addArticleStore(String title, String desc,String imgLink, String link,String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("articleStTitle", title);
        cv.put("articleStDesc", desc);
        cv.put("articleStPicture", imgLink);
        cv.put("articleStLink", link);
        cv.put("articleStCategory",category);
        long result = db.insert("ArticleStore", null, cv);


        if (result != -1) {
            Toast.makeText(context, "Stored in db.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to store in db..", Toast.LENGTH_LONG).show();
        }
    }

    public void updateArticle(int id, String title,String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("articleTitle",title);
        cv.put("articleDesc",desc);
        long result = db.update("Article",cv,"articleID=?",new String[]{String.valueOf(id)});
        if (result != -1) {
            Toast.makeText(context, "Update successful!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Update failed!", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteArticle(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result=db.delete("Article","articleID=?",new String[]{String.valueOf(id)});
        if (result != -1) {
            Toast.makeText(context, "Delete successful!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Delete failed!", Toast.LENGTH_LONG).show();
        }
    }


}
