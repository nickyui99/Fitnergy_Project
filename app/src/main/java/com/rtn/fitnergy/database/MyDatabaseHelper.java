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
                "articleEmail TEXT NOT NULL, "+
                "FOREIGN KEY(articleEmail) REFERENCES userTable(userEmail)" +
                ");";

        String articleStoreTable = "CREATE TABLE ArticleStore (" +
                "articleStID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "articleStTitle TEXT NOT NULL, " +
                "articleStDesc TEXT NOT NULL, " +
                "articleStPicture TEXT NOT NULL, " +
                "articleStLink TEXT NOT NULL, "+
                "articleStCategory TEXT NOT NULL"+
                ");";

//        String favouriteTable = "CREATE TABLE Favourite ("+
//                "articleID INTEGER NOT NULL, " +
//                "userID INTEGER NOT NULL, " +
//                "FOREIGN KEY (articleID) " +
//                "REFERENCES Article (articleID), " +
//                "FOREIGN KEY (userID) " +
//                "REFERENCES User (userID), " +
//                "PRIMARY KEY(articleID, userID)" +
//                ");";

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



        String offlineArticles= "INSERT INTO ArticleStore (articleStTitle,articleStDesc,articleStPicture,articleStLink,articleStCategory)"+
                "VALUES ('9 Boxing Combinations To Build Up Total Body Strength','There is a popular belief that if your workout does not make you sweat through your sports bra, it is not worth doing. But that is hogwash, experts say. Gentler workouts can be just as vital as hard-core exercise— and that is especially true for a good old-fashioned daily walk. Our bodies crave movement every day. \"Walking is an accessible way of [staying active], of keeping the blood flowing, utilizing energy, and stretching our muscles,\" explains biomechanist Katy Bowman, MS, cocreator of the online program Walking Well. \"Walking is the daily servings of vegetables in our fitness diet.\"And when you want to push yourself, you can get your heart rate up by climbing hills or following an interval regimen. Adding a challenge can turn your walk into the moderate-to-intense activity our bodies also require three to five days a week. Plus, walking has some major health perks: Research suggests a regular routine can boost immune function and reduce stress (yes, please!). It may also alleviate some hormone-related symptoms that can crop up midlife. A 2020 review of studies in the journal Menopause revealed that 91 percent of 77 different walking programs resulted in the improvement of at least one menopause-related health issue.Walking workouts may even help us stay mentally sharp. In October 2021, researchers reported in the journal NeuroImage that just 40 minutes of brisk walking three times a week was enough to improve signaling in the white matter of the brain, the deterioration of which is associated with cognitive impairment and Alzheimers disease.Wondering how fast \"brisk\" is? \"[The word] is intentionally vague because every body is different,\" says Bowman. Move at a clip that raises your heart rate but doesnt leave you gasping for breath, she suggests. That might be anywhere from a 12-minute mile to a 20-minute mile (or 3–4.5 MPH on a treadmill at a 1.0 incline). If you have a heart rate monitor, aim for between 50 percent and 70 percent of your maximum heart rate. You know the familiar advice to take 10,000 steps a day? That target may have originated as a marketing ploy to sell pedometers in the 1960s. The sweet spot for a long, healthy life may be closer to 7,000, according to a large study published in 2021 in JAMA Network Open. The researchers found that people who took more than 10,000 steps a day did not have any greater reductions in mortality risks than those taking at least 7,000 steps daily.', 'null','https://www.health.com/fitness/cardio-workouts/boxing-combination-workout','cardio'), "+
                "('The Benefits of Walking, Plus 3 Workout Plans to Get You Going','If you want to get in fighting shape, nows your chance. But it is going to require more than just upper-body work. \"I think thats a misconception about boxing,\" says Gina DiNapoli (shown here), creator of the JABS by Gina platform—and a routine for us at Health.com. People think its all arms, but its a lot of legs and core, too. \"The power of each punch comes from the lower body,\" she says. When youre not punching or kicking, youre in a boxers stance—standing at a 45-degree angle with your dominant foot in the back and fists by your face with elbows in—and bouncing lightly on your feet. Once youre ready to throw some punches, use your lower half to drive force through your fists. Heres a snapshot of some of the moves that will get you delivering hits and kicks like a pro. From boxers stance, punch front arm straight out, extending arm fully and rotating wrist so the palm faces down. Return fist to face and go back to boxers stance. Then, punch back arm forward for a cross, pivoting back foot, knee, and hip. Extend arm fully while rotating wrist so palm faces down. Return fist to face and go back to boxers stance. Alternate front arm jab and back arm cross for 60 seconds. Then, rest 30-60 seconds. From boxers stance, punch front arm forward for a jab, then return fist to face and go back to boxers stance. Punch rear arm forward for a cross, then return fist to face and go back to boxers stance. Next, lower into a squat position, and place both hands on floor. Walk hands out to a plank position, shoulders over wrists, forming a straight line from shoulders to ankles. Pause, then walk hands back to feet and stand up from a squat position. Repeat combination for 60 seconds, then rest 30-60 seconds. From boxers stance, lift front elbow to shoulder height, parallel to floor and bent 90 degrees. Rotate through front hip and foot to hook front fist forward for the punch, maintaining 90 degree angle. (Picture yourself hitting your opponent on the side of the jaw.) Return fist to face and to boxers stance. Then, lift rear elbow to shoulder height, parallel to floor and bent 90 degrees. Rotate through back hip and foot to hook back fist forward for the punch. Return fist to face and to boxers stance. Alternate between front hook and rear hook for 60 seconds, then rest 30-60 seconds. Start in boxers stance. Perform a front hook, then return fist to face and return to boxers stance. Perform a rear hook, then return fist to face and return to boxers stance. Then, lower into a squat, place both hands on the floor in front of you and jump or step feet back into a plank position, with shoulders over wrists, forming a straight line from shoulders to heels. Lower body to the ground, maintaining your plank. Then, press the floor away to come back up, completing a push-up. Jump or step feet toward hands and stand up fully. Repeat combination for 60 seconds, then rest for 30-60 seconds.','null','https://www.health.com/fitness/cardio-workouts/walking-workouts','cardio'), " +
                "('Kaley Cuoco Defends Herself For Wearing a Mask While Working Out','From weighted squats to resistance band exercises, Kaley Cuoco has been crushing her quarantine workouts. Her latest fitness \"obsession\"? Jumping rope. Cuoco shared a video of herself \"jumping it out,\" calling the cardio workout her \"newest obsession\" during quarantine. \"All you need is 20 mins, a jump rope, and good music!\" she captioned her post. The video is no doubt impressive. It shows Cuoco practicing footwork, jumping backward, doing crisscrosses, and high knees — all while wearing a face mask, BTW. In response to haters on her post who questioned why she was wearing a mask during her workout, she wrote: \"I wear a mask when Im in an enclosed space around others. Im protecting myself and everyone around me. That is why I choose to wear a mask.\"','null','https://www.health.com/syndication/kaley-cuoco-jump-rope','cardio'), "+
                "('The 12-3-30 Treadmill Routine Is Crazy-Popular on TikTok—But Is It Actually a Good Workout?','The treadmill—or as many people call it, the dreadmill—doesnt typically get a lot of love. But thanks to a recent viral TikTok trend, this fitness tool is receiving tons of hype. The trend, known as the \"12-3-30\" workout, is soothingly straightforward: You set your treadmill to an incline of 12 and a speed of 3 miles per hour and then walk for 30 minutes. The concept originated from social media influencer Lauren Giraldo, who shared it on YouTube in 2019 and again on TikTok in November 2020, where a video explaining the workout racked up nearly 12 million views and more than 2.7 million likes (and counting).\"I used to be so intimidated by the gym and it wasnt motivating,\" Giraldo explained in the 37-second TikTok video. \"But now I go, I do this one thing, and I can feel good about myself.\" The 12-3-30 workout, Giraldo said, helped her lose 30 pounds. As for the origin of this routine? \"Im not a runner, and running on the treadmill was not working for me,\" Giraldo told TODAY.com. \"I started playing around with the settings, and at the time, my gyms treadmill had 12 incline as the max. The three miles per hour felt right, like walking, and my grandma had always told me that 30 minutes of exercise a day was all you needed. Thats how the combination started.\" Giraldo isnt the only one who swears by this routine. Search \"12-3-30 workout\" on YouTube, and youll see tons of rave reviews praising it as a great way to lose weight, boost fitness, and feel excited about exercise. But is 12-3-30 really worth the hype? We asked three fitness experts to weigh in. The 12-3-30 workout centers on walking, and walking, in general, is a great form of lower-impact exercise, says exercise physiologist Janet Hamilton, CSCS and running coach with Running Strong in Atlanta. Regularly walking at a brisk pace offers a ton of health benefits, including strengthening your bones and muscles, boosting your balance and coordination, and preventing or managing conditions like heart disease, high blood pressure, and type 2 diabetes, according to the Mayo Clinic.','null','https://www.health.com/fitness/cardio-workouts/12-3-30-workout','cardio'), "+
                "('High-Intensity, Low-Impact Training Will Burn Calories and Protect Your Joints','High-intensity interval training, or HIIT, is popular because it works—this type of exercise is built around pylometric moves (think jump squats and burpees) and intense bursts of effort. It gets, and keeps, your heart rate up and burns more fat in less time, but it can also seriously stress the spine and joints. Enter HILIT: high-intensity, low-impact training. It’s intense in terms of heart rate, calorie burn, and muscle fatigue, but low-impact on the joints. (Swimming is a great example of a HILIT workout.) “To reap the benefits of those physiological changes that you’re making when you push yourself, you have to back off a little bit and give your muscles and connective tissues time to heal,” explains Sarah Revenig (shown here), CSCS, a trainer at Soho Strength Lab in New York City. Otherwise you’re setting yourself up for exhaustion and potential injury. A great way to think about HILIT workouts is that your heart rate is going up but your feet are always on the ground. Ready to go for it? Revenig created a workout just for Health.','null','https://www.health.com/fitness/cardio-workouts/hilit-workout','cardio'), "+
                "('The 21-Day Mini Resistance Band Challenge That Will Tone and Strengthen Your Entire Body','With most gyms still closed, people have been trying to find ways to get in their workouts (and maintain a regular fitness routine) at home. It is why many have resorted to building up their home gyms—but while dumbbells and yoga mats are both essential pieces of equipment, you may be overlooking one small (but mighty) fitness tool: a mini resistance band. These small, portable pieces of rubber dont get nearly enough credit: They can be used for overall body strengthening by creating levels of tension on your muscles, and can increase mobility. Theyre also low-impact and joint-friendly. Basically, mini resistance bands are an all-in-one gym that can be easily stowed in your closet or taken with you on the go. Right now—while many of us are still under stay-at-home orders—is the perfect time to take advantage of mini resistance bands with this 21-day challenge to help build strength and gain mobility. All you have to do is grab a mini band (pick your own resistance, but make sure to have a few levels on hand), carve out an exercise space at home, and follow the instructions below. This way, when youre finally able to go the gym again, you may be even stronger than you were before COVID-19 hit—or maybe youll decide you dont need a gym at all to get in a good sweat.','null','https://www.health.com/fitness/strength-training/mini-resistance-band-challenge','strength'), "+
                "('Does Muscle Weigh More Than Fat? The Answer is More Complicated Than You May Think','The short answer: Yes, to a certain extent muscle does weigh more than fat; if you simply take a bowl of fat and compare it to a same-sized bowl of muscle, the muscle will weigh more. But that is only an explanation in the simplest of terms—there is much more that goes into that question, particularly how your body responds to these two tissues, body fat and muscle. Muscle can weigh more than fat because it’s denser, says Joel Seedman, PhD, neuromuscular physiologist and owner of Advanced Human Performance in Suwanee, Georgia. So, as previously explained, if you hold a fistful of muscle it will weigh more than the same fistful of fat because you technically have more of the compact tissue in your hand. The catch: That number on the scale shouldn’t really matter here, because the benefits of having more muscle tissue in the body outweighs having more fat tissue. Muscle is a star player in keeping your body happy and healthy in the long-term, for several reasons. For starters, lean muscle mass can help manage blood sugar, keeping type 2 diabetes at bay. In fact, one 2017 cohort study published in PLoS One found a negative association between muscle mass and risk of developing of type 2 diabetes—specifically that a higher muscle mass meant a lower chance of type 2 diabetes. “The number one consumer of blood sugar in the human body is skeletal muscle,” says Tim Church, MD, MPH, PhD, professor of preventive medicine at Pennington Biomedical Research Center at Louisiana State University. So, the more muscle you have, the greater your potential to metabolize blood sugar. As an added bonus, the blood sugar-regulating effect is instant and lasting after exercise. So if you do a workout today, your muscles will utilize blood sugar better over the next 72 hours, Church says.','null','https://www.health.com/fitness/does-muscle-weigh-more-than-fat','strength'), "+
                "('The Best Bodyweight Exercises You Can Do at Home','At this point in your social-distancing journey, at-home solo workouts have likely become your new sweat BFF. And for most people, that means bodyweight-only exercises have taken the place of studio classes and gym sessions that call for weights and other gear. While it might seem like a setback for some, it is a good thing—the best bodyweight exercises test your form and give you a chance to perfect it, require zero equipment and little space, and, paired together, can add up to a total-body strength training workout. “It is understandable to think that a bodyweight workout might not be challenging enough for you, especially if you are used to lifting weights—but you might be surprised,” says Adam Rosante, CPT, CSCS, strength and nutrition coach and creator of Gym Class with Adam, a free online gym class for kids K-6. “From adjusting reps and sets, to tweaking the tempo of the moves, to creating timed challenges and changing the angle of your body, there are plenty of ways to ramp up the difficulty of bodyweight exercises—or dial it back if you are just starting.” Whenever you are trying a new workout, you want to first focus on nailing your form on each move, says Rosante. Performing bodyweight exercises allows you to tune into body alignment and what you should be feeling—so really pay attention to the muscle groups you are working as you go. Once you master each exercise, it is time to take them to the next level.To get you started on a no-weight workout at home, Rosante breaks down the form on 12 of the best bodyweight exercises, plus how to regress and progress them, and the ideal way to turn them into a sweaty exercise session.','null','https://www.health.com/fitness/bodyweight-exercises','strength'), "+
                "('You Can Do This 10-Move Arm Workout At Home No Weights Required','Traditional arm exercises like bicep curls and shoulder presses work great for strengthening the upper body—but they usually require some weights to get the best benefit. So what if you want an efficient, effective workout, without having to worry about equipment (like, when you’re traveling, have limited space, or just can’t make it to the gym)? Enter, bodyweight arm exercises. Just a quick FYI: Arm strength is important—strong arms and shoulders help you move more comfortably throughout daily life, Roxie Jones, NASM-CPT, personal trainer and SoulCycle instructor, tells Health. Just think about how many times you open and close doors, carry groceries, push yourself up out of bed, or lift a suitcase into the overhead compartment. All these moves will feel much easier if you’ve been training your upper body—even without weights. Pushing, pulling, and holding your own bodyweight takes some serious work and requires strength and stability. Plus, if you’re new to strength training, starting with exercises sans equipment is the smartest thing to do. “I always recommend mastering bodyweight exercises before moving on to actual weights,” Jones says. It’s important to get comfortable with an exercise and nail proper form before adding any external resistance—that’s one surefire strategy for sidestepping injury. To get you building up your upper body, try these 10 bodyweight arm exercises from Jones. Do them in your living room, hotel room, or wherever else you feel like you need to get a quick workout in.','null','https://www.health.com/fitness/arm-workouts-at-home','strength'), "+
                "('We Love This Fitness Bloggers Response to Haters Who Say Strength Training Makes Women Look Manly','As anyone who has ever stepped into a weight room knows, women own the room as much as men do. And they have good reason to love lifting. Studies have shown that lifting weights builds strength, improves overall functionality, and helps maintain muscle mass as you age. Yet internet trolls and random critics IRL wont stop telling fit chicks that strength training is unfeminine, and that it will make their bodies look manly or too bulky. Tired of the unsolicited comments from haters who clearly dont know what they are talking about, fitness blogger Kelsey Wells took to Instagram to say her piece. \"Comments along the lines of you are looking manly or careful you dont want to be bulky or weightlifting isnt feminine never cease to amaze me,\" wrote Wells. In the post, she advised her female followers to ignore these ridiculous claims and any other comments suggesting that there is a \"right\" way to be feminine. \"THE ONLY THING A WOMAN NEEDS TO DO TO BE BEAUTIFUL AND FEMININE IS TO BE HERSELF,\" Wells proclaimed. She added that she feels most beautiful at the gym when she is sweating up a storm, and she plans to continue to ignore negative comments about the gym workouts she loves.','null','https://www.health.com/fitness/kelsey-wells-fitness-blogger-strength-training-instagram','strength'), " +
                "('What Muscles Do Push-Ups Work? Here is How to Do Them the Right Way','You’ve probably seen people who can’t quite get all the way to the ground in a push-up or have trouble maintaining a plank, drop their knees to the ground to modify the move. But Atkins says to skip that variation. Instead, put your hands on a couch, chair, table, or bench and perform push-ups at an incline. “When you drop to the knees, you completely eliminate half of your body weight and train improper body mechanics,” says Atkins. “A very crucial part of having the ability to do a push-up is maintaining a strong core. The goal of a push-up is to have the ability to press the equivalent of your body weight away from you.” By taking it to an incline instead, you still maintain that straight line and get used to moving your entire body. As you get stronger, simply bring that incline lower until you can maintain strong plank form while doing a push-up on the ground. “The way you train the body is the way it’s going to respond. If you always do push-ups from the knees, eventually you’ll come up off the knees and the mechanics will feel foreign, so it’s best to begin doing push-ups with the body in a straight line—like you would, and should, do in a proper push-up,” says Atkins. “The incline decreases the amount of bodyweight or load put on the arms and shoulders, but as you progressively lower, you progressively add load to the upper body in a manageable way.” For those who breeze through regular push-ups on the ground, Atkins suggest upping the challenge by reversing the incline and putting your feet on yoga blocks, your couch, a chair, or a bench. This ups the challenge on your upper body. You can also switch up the tempo to make a push-up more difficult. Try pausing for a count of three to five at the bottom or lower to the ground on a count of six. \"The goal is to increase the time under tension, which is another way to change up your push-ups,\" says Atkins.','null','https://www.health.com/fitness/what-muscles-do-push-ups-work','arm'), " +
                "('You Do Not Need Weights to Get a Full Upper Body Workout—Try This Quick Circuit Routine','Good news for at-home exercisers: You dont need a set of weights to get a solid upper-body workout. All you need are a few household items and this workout from NASM-certified trainer Jennifer Romanelli, co-owner of Trooper Fitness in New York City. A powerlifter at heart, Romanelli abandoned the barbell when gyms closed during the COVID-19 shutdown and found joy in pushing her body in new ways—like in her living room, using chairs and towels to target the chest, back, shoulders, and arms. \"The benefit of doing [bodyweight-only] work is that you learn about you,\" she says. \"Youre able to move your body efficiently, get stronger, and then when you do apply weight, your bodys more receptive to it.\" Bodyweight exercises not only build muscle but they can also power you through your days. \"Tweak your training to what you want to do in your life—make it work for you,\" Romanelli says. (For her, that means carrying a toddler in one arm and a stroller in the other…while she walks up five flights!) Your sense of accomplishment will grow every time you do a few more push-ups or hold your plank a little longer, and that progress will motivate you to work through the challenges, Romanelli says.','null','https://www.health.com/fitness/arm-workouts/home-arm-workout','arm'), " +
                "('You Can Do This 10-Move Arm Workout At Home—No Weights Required','Traditional arm exercises like bicep curls and shoulder presses work great for strengthening the upper body—but they usually require some weights to get the best benefit. So what if you want an efficient, effective workout, without having to worry about equipment (like, when you’re traveling, have limited space, or just can’t make it to the gym)? Enter, bodyweight arm exercises. Just a quick FYI: Arm strength is important—strong arms and shoulders help you move more comfortably throughout daily life, Roxie Jones, NASM-CPT, personal trainer and SoulCycle instructor, tells Health. Just think about how many times you open and close doors, carry groceries, push yourself up out of bed, or lift a suitcase into the overhead compartment. All these moves will feel much easier if you’ve been training your upper body—even without weights. Pushing, pulling, and holding your own bodyweight takes some serious work and requires strength and stability. Plus, if you’re new to strength training, starting with exercises sans equipment is the smartest thing to do. “I always recommend mastering bodyweight exercises before moving on to actual weights,” Jones says. It’s important to get comfortable with an exercise and nail proper form before adding any external resistance—that’s one surefire strategy for sidestepping injury. To get you building up your upper body, try these 10 bodyweight arm exercises from Jones. Do them in your living room, hotel room, or wherever else you feel like you need to get a quick workout in.','null','https://www.health.com/fitness/arm-workouts-at-home','arm'), " +
                "('Here is What Most People Get Wrong About Biceps Curls, According to Kim Ks Trainer','The humble biceps curl is probably one of the simplest lifts you can do in the gym. Its also one of the best moves for building a strong upper body, and it might just help you get one step closer to nailing the pull-up of your dreams. But while biceps curls might seem incredibly basic, Kim Kardashians trainer, Melissa Alcantara, is here to tell you that most people just cant seem to get this move right.\"This is a super-simple move that I see so many people murder,\" she recently shared in an Instagram video. To ensure youre actually packing on muscle in all the right places, Alcantara walked her followers through a tutorial on how to do biceps curls using a barbell for optimal results and, most importantly, to avoid injury. \"First thing, you want your shoulders back and your chest up,\" she says in the video. \"You arent swinging the bar back and forth. Youre stiff. You want your wrists straight. So not bent out or curled. Thats going to give you the most pump in your biceps.\"','null','https://www.health.com/syndication/kim-kardashian-trainer-melissa-alcantara-biceps-curl-tutorial','arm'), " +
                "('I Did a Plank Every Day for 3 Months With My Husband—and It Helped More Than Just My Core','My daily planking habit had an improbable start: a tweet. The post is lost to the flow of the feed, but in my memory, it was simple—a woman sharing her trick of adding just five additional seconds to her plank time each week. Easy, right? I sit hunched over my desk all day, a vision of poor posture, so the idea of planking—developing my core strength, and, bonus, possibly preventing some of my persistent lower backache—is appealing. And when I mention the woman’s trick to my husband, Jason, he’s intrigued too. We agree to try it out together. And really, what I thought would happen next is that we’d plank for a day or two. Maybe a week—maybe. But to my surprise, here we are, months later, casually asking each other every day: Hey, want to plank? Here are seven things that I realized as Jason and I created and maintained this daily planking habit.','null','https://www.health.com/fitness/plank-every-day-husband','arm');" ;



        db.execSQL(userTable);
        db.execSQL(weightTable);
        /*db.execSQL(workoutTable);
        db.execSQL(workoutRecordTable);*/
        db.execSQL(articleTable);
        db.execSQL(articleStoreTable);
        //db.execSQL(favouriteTable);
        db.execSQL(workoutActivity);
        db.execSQL(workout);
        db.execSQL(workoutDetail);
        db.execSQL(workoutRecord);
        db.execSQL(workoutCount);
        db.execSQL(workout1);
        db.execSQL(workoutActivity1);
        db.execSQL(workoutDetail1);
        db.execSQL(offlineArticles);
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
        cv.put(USER_COLUMN_HEIGHT, userData.getHeight());
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
