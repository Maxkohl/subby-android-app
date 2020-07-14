package com.example.subby;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "subs_table")
public class Subscription {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int subId;
    @ColumnInfo(name = "name")
    @NonNull
    private String subName;
    @NonNull
    private double price;
    private String notes;

//    private Date dueDate;

//    private boolean isSubscribed;
    //TODO Due Date,background color?,icon member variable


    public Subscription(@NonNull String subName, double price, String notes) {
        this.subName = subName;
        this.price = price;
        this.notes = notes;
    }

    public int getSubId() {
        return subId;
    }

    @NonNull
    public String getSubName() {
        return subName;
    }

    public double getPrice() {
        return price;
    }

    public String getNotes() {
        return notes;
    }

//    public boolean isSubscribed() {
//        return isSubscribed;
//    }

    public void setSubId(int id) {
        subId = id;
    }
}
