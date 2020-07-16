package com.example.subby;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUserSub(Subscription sub);

    @Query("SELECT * FROM subs_table WHERE isPaid = :hasUserPaid")
    LiveData<List<Subscription>> getAllUserSubs(boolean hasUserPaid);

    @Query("DELETE FROM subs_table")
    void deleteAllUserSubs();

    @Query("DELETE FROM subs_table WHERE name = :name")
    void deleteSubscription(String name);

    @Query("SELECT SUM(price) FROM subs_table")
    LiveData<Double> getTotalCost();

    @Update
    void updateSubPaid(Subscription sub);

}
