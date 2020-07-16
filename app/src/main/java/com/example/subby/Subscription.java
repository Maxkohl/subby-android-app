package com.example.subby;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
    private String color;

    @NonNull
    private boolean isPaid ;

    @NonNull
    private long dueDate;


    public Subscription(@NonNull String subName, double price, String notes, String color, long dueDate) {
        this.subName = subName;
        this.price = price;
        this.notes = notes;
        this.color = color;
        this.isPaid = false;
        this.dueDate = dueDate;
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

    public String getColor() {
        return color;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setSubId(int id) {
        subId = id;
    }

    public long getDueDate() {
        return dueDate;
    }

    public String getDueDateString() {
        long unixSeconds = dueDate;
        Date date = new java.util.Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yyyy");

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }
}
