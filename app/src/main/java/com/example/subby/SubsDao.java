package com.example.subby;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SubsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUserSub(Subscription sub);

    @Query("SELECT * FROM subs_table")
    LiveData<List<Subscription>> getAllUserSubs();

    @Query("DELETE FROM subs_table")
    void deleteAllUserSubs();

    @Query("DELETE FROM subs_table WHERE name = :name")
    void deleteSubscription(String name);

}
