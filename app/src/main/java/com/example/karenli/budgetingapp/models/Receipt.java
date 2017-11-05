package com.example.karenli.budgetingapp.models;

import android.text.format.DateFormat;

/**
 * Created by karenli on 9/27/17.
 */

public class Receipt {
    private long myId;
    private String myName;
    private String myDescription;
    private String myImgPath;
    private int myMonth;
    private int myYear;
    private double myTotal;

    public Receipt(String myName, String myDescription, String myImgPath, int myMonth, int myYear, double myTotal) {
        this.myName = myName;
        this.myDescription = myDescription;
        this.myImgPath = myImgPath;
        this.myMonth = myMonth;
        this.myYear = myYear;
        this.myTotal = myTotal;
    }

    public Receipt() {}

    public long getMyId() {
        return myId;
    }

    public void setMyId(long myId) {
        this.myId = myId;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getMyDescription() {
        return myDescription;
    }

    public void setMyDescription(String myDescription) {
        this.myDescription = myDescription;
    }

    public int getMyMonth() {
        return myMonth;
    }

    public void setMyMonth(int myMonth) {
        this.myMonth = myMonth;
    }

    public int getMyYear() {
        return myYear;
    }

    public void setMyYear(int myYear) {
        this.myYear = myYear;
    }

    public double getMyTotal() {
        return myTotal;
    }

    public void setMyTotal(double myTotal) {
        this.myTotal = myTotal;
    }

    public String getMyImgPath() {
        return myImgPath;
    }

    public void setMyImgPath(String myImgPath) {
        this.myImgPath = myImgPath;
    }
}
