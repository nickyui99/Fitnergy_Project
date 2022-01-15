package com.rtn.fitnergy.social;

public class Newsfeedrepeat {

    private int profileicon;
    private int postimage;
    private String userid;
    private String caption;

    public Newsfeedrepeat(int profileIcon, int postimage, String userid, String caption) {
        this.profileicon = profileIcon;
        this.postimage = postimage;
        this.userid = userid;
        this.caption = caption;
    }

    public Newsfeedrepeat() {

    }

    public int getProfileIcon() {
        return profileicon;
    }

    public void setProfileIcon(int profileIcon) {
        this.profileicon = profileIcon;
    }

    public int getPostimage() {
        return postimage;
    }

    public void setPostimage(int postimage) {
        this.postimage = postimage;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
