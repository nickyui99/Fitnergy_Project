package com.rtn.fitnergy.social;

import java.util.ArrayList;

public class ChatRepeat {

    private int profileicon;
    private String line1;
    private String line2;

    public ChatRepeat(int profileIcon, String line1, String line2) {
        this.profileicon = profileIcon;
        this.line1 = line1;
        this.line2 = line2;
    }

    public ChatRepeat() {
    }

    public int getProfileIcon() {
        return profileicon;
    }

    public void setProfileIcon(int profileIcon) {
        this.profileicon = profileIcon;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String userid) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String caption) {
        this.line2 = line2;
    }
}
