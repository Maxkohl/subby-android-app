package com.example.subby;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
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
    //TODO Due Date member variable


    public Subscription(int subId, @NonNull String subName, double price, String notes) {
        this.subId = subId;
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
}
