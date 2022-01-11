package com.rtn.fitnergy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
/*
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
*/

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
                "articleLink TEXT NOT NULL, "+
                "articleEmail TEXT NOT NULL"+
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

        //workout
        String workoutActivity = "CREATE TABLE WorkoutActivity ("+
                "activityID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "activityName TEXT NOT NULL, " +
                "activityInstruc TEXT NOT NULL, " +
                "videoID TEXT NOT NULL "+
                ");";

        String workout = "CREATE TABLE Workout ("+
                "workoutID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "workoutName TEXT NOT NULL, " +
                "workoutDesc TEXT NOT NULL, " +
                "workoutCalories INTEGER NOT NULL, " +
                "workoutDuration REAL NOT NULL" +
                ");";

        String workoutDetail = "CREATE TABLE WorkoutDetail ("+
                "workoutID INTEGER NOT NULL, " +
                "activityID INTEGER NOT NULL, " +
                "FOREIGN KEY(workoutID) REFERENCES workout(workoutID), " +
                "FOREIGN KEY(activityID) REFERENCES workoutActivity(activityID), " +
                "PRIMARY KEY(workoutID, activityID) " +
                ");";

        String workoutRecord = "CREATE TABLE WorkoutRecord ("+
                "recordID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userEmail TEXT NOT NULL, " +
                "workoutID INTEGER NOT NULL, " +
                "workoutDate TEXT NOT NULL, " +
                "FOREIGN KEY(userEmail) REFERENCES userTable(userEmail), " +
                "FOREIGN KEY(workoutID) REFERENCES workout(workoutID) " +
                ");";

        String workoutCount = "CREATE TABLE WorkoutCount( "+
                "workoutID INTEGER NOT NULL, " +
                "userID INTEGER NOT NULL, " +
                "workoutCount INTEGER, " +
                "FOREIGN KEY(userID) REFERENCES userTable(userID), " +
                "PRIMARY KEY(workoutID, userID)" +
                ");";

        String workout1 = "INSERT INTO Workout (workoutName, workoutDesc, workoutCalories, workoutDuration) " +
                "VALUES ('Beginner', 'Suitable for beginner.', 1200, 45), " +
                "('Medium', 'Suitable for usual exerciser.', 1600, 85), " +
                "('Expert', 'Suitable for challenger.', 2500, 120);";

        String workoutActivity1 = "INSERT INTO WorkoutActivity (activityName, activityInstruc, videoID) " +
                "VALUES " +
                "('V bracing - 25s', '1.Tighten the abdomen, put your hands on both slides of the lower leg, and raise the lower leg parallel to the ground. \n2. Adjust the position of the torso, not to the tail bone. \n3. Keep your back straight.', 'YtbKFCdlYog'), " +
                "('Breaststroke Straighten Back - x20', '1. Lie prone on the mat, extend your arms forward and let your legs separate naturally. \n2. Tighten your hips and back, lift your limbs, bend your elbows and draw your arms back. \n3. Lift your knees and shoulders off the ground, pause slightly and return to the starting position.', '92t1DNOPnM0'), " +
                "('Prone Back Stretch - x16', '1. Lie on the yoga mat with your hands on your ears. \n2. Lift up to the highest point, pause, and return to the beginning.', 'LLslBxDaZQ0'), " +
                "('Cross Mountain Climber - x16', '1. The arm is naturally straight and perpendicular to the ground. \n2. Keep knees and toes in the same direction. \n3. Keep your belly tight.', 'luRruRjECm8'), " +
                "('Touch Knee Crunch - x20', '1. Lie on the mat, bend your knees, slightly separate your legs, and firmly step on your feet. \n2. Hold your hands on your thighs, roll your shoulders and upper back off the ground with the strength of your abs, and slowly return to the starting position after touching your knees with your hands. \n3. When touching the knee, keep the lower back close to the ground and keep the arms straight.', 'hG_6ZcTXx54'), " +
                "('Dynamic Plank - x12', '1. Support your body fully. \n2. Lower one arm. \n3. Tighten the waist and abdomen to reduce shaking. \n4. Restore after plank.', 'mLOesPMO_OA'), " +
                "('Push-Ups - x16', '1. Straight back. \n2. Lean to the elbow joint slightly above the torso.', 'BRlikA4oxOw'), " +
                "('Left Bulgarian Squat - x16', '1. Stand on one foot on the left, with your right foot resting on the edge of the chair and your hands on your hips and relaxed as possible. \n2. Squat with your left leg bent so that your thigh is parallel to the floor and your knee does not exceed your toe. \n3. Keep your back straight and stand with your heels up. \n4. Hold on to a table or another fixture if you cannot stand still.', 'X51xIXY09_Y'), " +
                "('Right Bullgarian Squat - x14', '1. Stand on one leg on the right, put the left foot on the edge of the chair, and relax as much as possible with your hands akimbo. \n2. Squat with your right leg bent to make the thigh parallel to the ground, with the knee no more than the toe. \n3. Back straight, heel up. \n4. If you canot stand stably, you can hold the table or other fixings.', '3ezmfUFMQ8o')," +
                "('Supine Leg Raises - x8', '1. Lie on your back on the mat, press your lower back firmly to the ground, straighten your legs, and hook up your toes. \n2. The legs are raised and lowered.', 'q0JVBRHoLz4'), " +
                "('Slow Split Lunge - x20', '1. Lift your head and straighten your chest, straight back. \n2. Keep the moement speed steady and keep your knees below your toes. \n3. Sit down vertically and try not to touch the knees on the hind legs.', 'GMaDnJ5CXoM'), " +
                "('Mummy Jump - x32', '1. Stomach in, chest out, back straight. \n2. Cross the arms and legs at the same time. \n3. The knees slightly bend for buffering when landing. \n4. Keep balance.', 'QtyHmqMZ5gw'), " +
                "('Alternating Leg Raising Plank - x18', '1. Lie prone on the mat, put elbows on the ground, keep the head, shoulder, back, hip, knee and ankle in a straight line. \n2. Lift he legs up to the highest point alternately. \n3. When lifting the legs, keep the legs and upper body still. \n4. The legs are fully extended during the action.', 's1MeMvqSNqA'), " +
                "('Static Hip Bridge - 25s', '1. Lie on your back on a yoga mat with your legs bent and your heels on the floor. \n2. Apply force to lift your hips up to your thighs in line with your body. \n3. Lift your hips up with your upper back supporting the floor and holding.', 'AacXE6_hGBI'), " +
                "('Jumping Jack With Toe Touch - x30', '1. Stomach in, chest out, back straight. \n2. The knees slightly bend for buffering when landing. \n3. Keep balance.', 'L7urjuNTIos' ), " +
                "('Squat Jump - x14', '1. Keep your back straight, tighten your waist and abdomen, and stretch your arms forward when you squat, parallel to the ground, and straighten your fingers. \n2. Use the muscles of the thighs and buttocks to elastically jump when squatting to the bottom.', 'YGGq0AE5Uyc' ), " +
                "('Step burpee - x8', '1. Keep hands shoulder-width apart. \n2. Step backward with alternating legs. \n3. Tighten the core of your waist and abdomen. \n4. Do not cave in.', '4bkOIruGAho'), " +
                "('Butt Kicks - 30s', '1. Back straight, look ahead, put hands on hips. \n2. Keep your body stable, quickly lift your alternate legs up and touch your hands every time.', '-dtvAxibgYQ'), " +
                "('Back Stretch - x12', '1. Lie on the yoga mat with your hands on the ground. \n2. Lift up to the highest point, pause and return to the beginning.', 'LLslBxDaZQ0');";
        String workoutDetail1 = "INSERT INTO WorkoutDetail (workoutID, activityID)" +
                "VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), " +
                "(2, 1), (2, 3), (2, 4), (2, 7), (2, 8), (2, 9), (2, 12), (2, 13), (2, 14), (2, 15), (2, 16), (2, 2), (2, 5), (2, 10), (2, 11), " +
                "(3, 3), (3, 6), (3, 8), (3, 1), (3, 5), (3, 2), (3, 12), (3, 17), (3, 13), (3, 16), (3, 15), (3, 18), (3, 10), (3, 14), (3, 19) , (3, 4), (3, 11), (3, 7)";

        db.execSQL(userTable);
        db.execSQL(weightTable);
        /*db.execSQL(workoutTable);
        db.execSQL(workoutRecordTable);*/
        db.execSQL(articleTable);
        db.execSQL(articleStoreTable);
        db.execSQL(favouriteTable);
        db.execSQL(workoutActivity);
        db.execSQL(workout);
        db.execSQL(workoutDetail);
        db.execSQL(workoutRecord);
        db.execSQL(workoutCount);
        db.execSQL(workout1);
        db.execSQL(workoutActivity1);
        db.execSQL(workoutDetail1);
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
        Cursor data = db.rawQuery("SELECT userID FROM User WHERE userName = '" + userModel.getUserName() + "';", null);
        data.moveToNext();
        String workoutCount1 = "INSERT INTO WorkoutCount (userID, workoutID, workoutCount)" +
                "VALUES (" + data.getInt(0) + ", 1, 0), (" + data.getInt(0) + ", 2, 0), (" + data.getInt(0) + ", 3, 0);";
        db.execSQL(workoutCount1);
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

    public void addArticle(String title, String desc,String imgLink, String link,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put("articleTitle", title);
        cv.put("articleDesc", desc);
        cv.put("articlePicture", imgLink);
        cv.put("articleLink", link);
        cv.put("articleEmail", email);

        long result = db.insert("Article", null, cv);


        if (result != -1) {
            Toast.makeText(context, "Added to favourites!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to add to favourites..", Toast.LENGTH_LONG).show();
        }
    }

    public Cursor readFavData(String articleEmail){

        String query= "SELECT * FROM Article WHERE articleEmail='"+articleEmail+"'";
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

    //workout
    public Cursor getWorkout(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery("SELECT workoutName, workoutDesc FROM Workout" +
                " ORDER BY workoutID;", null);
        data.moveToFirst();

        return data;
    }

    public Cursor getWorkoutID(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery("SELECT workoutID FROM Workout WHERE workoutName = '" + name + "';", null);
        data.moveToFirst();

        return data;
    }

    public Cursor getWorkoutDet(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery("SELECT WorkoutActivity.activityName, WorkoutActivity.activityInstruc, WorkoutActivity.videoID " +
                "FROM WorkoutActivity INNER JOIN WorkoutDetail ON WorkoutActivity.activityID = WorkoutDetail.activityID " +
                "INNER JOIN Workout ON WorkoutDetail.workoutID = Workout.workoutID " +
                "WHERE Workout.workoutID =" + id + ";", null);
        data.moveToFirst();
        return data;
    }

    public Cursor getLastWRec(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT Workout.workoutID, workoutName, workoutDesc FROM WorkoutRecord" +
                " INNER JOIN Workout ON WorkoutRecord.workoutID = Workout.workoutID WHERE userEmail = '" + email + "' ORDER BY recordID DESC LIMIT 1;", null);

        if(data.getCount() == 1){
            return data;
        } else {
            return null;
        }
    }

    public int getWorkoutCount(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery("SELECT workoutCount FROM WorkoutCount WHERE userID ="+ id +";", null);
        data.moveToNext();

        int ttlCount = 0;
        int i = 0;
        while(i < data.getCount()){
            ttlCount += data.getInt(0);
            data.moveToNext();
            i++;
        }

        return ttlCount;
    }

    //nid to update
    public void addWorkoutRecord(String email, int workoutID, String workoutDate, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Log.d("Click", workoutDate);

        cv.put("userEmail", email);
        cv.put("workoutID", workoutID);
        cv.put("workoutDate", workoutDate);

        long result = db.insert("WorkoutRecord", null, cv);

        String query = "UPDATE WorkoutCount SET workoutCount = workoutCount + 1 WHERE userID = " + id +" AND workoutID = "+ workoutID + ";";
        db.execSQL(query);

      /*  Cursor data = db.rawQuery("SELECT workoutCalories FROM Workout WHERE workoutID = " + workoutID + ";", null);
        data.moveToNext();


        //query = "UPDATE User SET calorieAccumulated = calorieAccumulated + data.getInt(0) WHERE workoutID = "+ workoutID + ";";
        //long result2 = db.update("Workout", cv, "workoutID = ?", new String[]{Integer.toString(workoutID)});
        if (result != -1) {
            Log.d("Click", "Success" + data.getInt(0));
        } else {
            Log.d("Click", "Failed");
        }*/
    }

    public void resetRecord(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM WorkoutRecord";
        db.execSQL(query);

        query = "DELETE FROM sqlite_sequence WHERE name = 'WorkoutRecord';";
        db.execSQL(query);

        Cursor data = getWorkout();
        for(int i = 1; i < data.getCount()+1; i++){
            query = "UPDATE WorkoutCount SET workoutCount = 0 WHERE userID =" + id +" AND workoutID = "+ i + ";";
            db.execSQL(query);
        }
    }


}
