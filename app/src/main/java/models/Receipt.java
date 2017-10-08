package models;

import android.text.format.DateFormat;

/**
 * Created by karenli on 9/27/17.
 */

public class Receipt {
    private String myName;
    private String myDescription;
    private String myImgPath;
    private DateFormat myDate;
    private long myTotal;

    public Receipt(String myName, String myDescription, String myImgPath, DateFormat myDate, long myTotal) {
        this.myName = myName;
        this.myDescription = myDescription;
        this.myImgPath = myImgPath;
        this.myDate = myDate;
        this.myTotal = myTotal;
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

    public DateFormat getMyDate() {
        return myDate;
    }

    public void setMyDate(DateFormat myDate) {
        this.myDate = myDate;
    }

    public long getMyTotal() {
        return myTotal;
    }

    public void setMyTotal(long myTotal) {
        this.myTotal = myTotal;
    }
}
