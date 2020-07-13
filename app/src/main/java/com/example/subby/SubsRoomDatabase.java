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

@Database(entities = {Subscription.class}, version = 1, exportSchema = false)
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

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final SubsDao mDao;
        String[] subNames = {"Netflix", "Hulu", "New York Times"};

        public PopulateDbAsync(SubsRoomDatabase db) {
            mDao = db.subsDao();
        }

        @Override
        protected Void doInBackground(Void... params) {
            mDao.deleteAll();

            for (int i = 0; i < subNames.length; i++) {
                Subscription sub = new Subscription(subNames[0],1.00,"Dummy Notes");
                mDao.insert(sub);
            }
            return null;
        }
    }
}
