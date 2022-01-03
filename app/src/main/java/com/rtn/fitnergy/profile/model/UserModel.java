package com.rtn.fitnergy.profile.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;


public class UserModel implements Parcelable {
    private int userID;
    private String userName;
    private String userPassword;
    private String userEmail;
    private Bitmap profileImage;
    private String gender;
    private int height;
    private Date birthdate;
    private boolean profileVisibilitySetting;
    private boolean socialCircleSetting;
    private boolean privateModeSetting;

    public UserModel(int userID, String userName, String userPassword, String userEmail, Bitmap profileImage, String gender, int height,  Date birthdate, boolean profileVisibilitySetting, boolean socialCircleSetting, boolean privateModeSetting) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.profileImage = profileImage;
        this.gender = gender;
        this.height = height;
        this.birthdate = birthdate;
        this.profileVisibilitySetting = profileVisibilitySetting;
        this.socialCircleSetting = socialCircleSetting;
        this.privateModeSetting = privateModeSetting;
    }

    protected UserModel(Parcel in) {
        userID = in.readInt();
        userName = in.readString();
        userPassword = in.readString();
        userEmail = in.readString();
        profileImage = in.readParcelable(Bitmap.class.getClassLoader());
        gender = in.readString();
        height = in.readInt();
        profileVisibilitySetting = in.readByte() != 0;
        socialCircleSetting = in.readByte() != 0;
        privateModeSetting = in.readByte() != 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail(){return userEmail;}

    public void setUserEmail(String userEmail){this.userEmail = userEmail;}

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        if (profileImage.getHeight() > 480 || profileImage.getWidth()>480){
            this.profileImage = getResizedBitmap(profileImage, 480);
        }else {
            this.profileImage = profileImage;
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isProfileVisibilitySetting() {
        return profileVisibilitySetting;
    }

    public void setProfileVisibilitySetting(boolean profileVisibilitySetting) {
        this.profileVisibilitySetting = profileVisibilitySetting;
    }

    public boolean isSocialCircleSetting() {
        return socialCircleSetting;
    }

    public void setSocialCircleSetting(boolean socialCircleSetting) {
        this.socialCircleSetting = socialCircleSetting;
    }

    public boolean isPrivateModeSetting() {
        return privateModeSetting;
    }

    public void setPrivateModeSetting(boolean privateModeSetting) {
        this.privateModeSetting = privateModeSetting;
    }

    /**
     * reduces the size of the image
     * @param image
     * @param maxSize
     * @return
     */
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userID);
        dest.writeString(userName);
        dest.writeString(userPassword);
        dest.writeString(userEmail);
        dest.writeParcelable(profileImage, flags);
        dest.writeString(gender);
        dest.writeInt(height);
        dest.writeByte((byte) (profileVisibilitySetting ? 1 : 0));
        dest.writeByte((byte) (socialCircleSetting ? 1 : 0));
        dest.writeByte((byte) (privateModeSetting ? 1 : 0));
    }
}
