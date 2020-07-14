package com.example.subby;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Subscription.class}, version = 3, exportSchema = false)
public abstract class SubsRoomDatabase extends RoomDatabase {
    public abstract SubsDao subsDao();

    private static SubsRoomDatabase INSTANCE;

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    public static SubsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SubsRoomDatabase.class) {
                if (INSTANCE == null) {
                    //TODO Change migration strategy?
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SubsRoomDatabase.class, "subs_database").fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final SubsDao mDao;
        Subscription[] subs = {new Subscription("Netflix", 20.00, "Netflix is good!"),
                new Subscription("Hulu", 12.00, "Too many ads!"), new Subscription("New York " +
                "Times", 50.00, "Great morning read.")};

        public PopulateDbAsync(SubsRoomDatabase db) {
            mDao = db.subsDao();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //TODO mDao.deleteAll() was here but I need data to be persistent
            mDao.deleteAllUserSubs();

            for (int i = 0; i < subs.length; i++) {
                Subscription sub = new Subscription(subs[i].getSubName(), subs[i].getPrice(),
                        subs[i].getNotes());
                mDao.insertUserSub(sub);
            }
            return null;
        }
    }
}
