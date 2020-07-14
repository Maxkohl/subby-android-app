package com.example.subby;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SubsRepository {
    private SubsDao mSubDao;
    private LiveData<List<Subscription>> mAllSubs;

    public SubsRepository(Application application) {
        SubsRoomDatabase db = SubsRoomDatabase.getDatabase(application);
        mSubDao = db.subsDao();
        mAllSubs = mSubDao.getAllUserSubs();
    }

    public LiveData<List<Subscription>> getAllSubs() {
        return mAllSubs;
    }

    public void insert(Subscription sub) {
        new insertAsyncTask(mSubDao).execute(sub);
    }

    public void deleteSub(String name) {new deleteAsyncTask(mSubDao).execute(name);}


    private class insertAsyncTask extends AsyncTask <Subscription, Void, Void>{

        private SubsDao mAsyncTaskDao;

        insertAsyncTask(SubsDao subsDao) {
            mAsyncTaskDao = subsDao;
        }

        @Override
        protected Void doInBackground(Subscription... subscriptions) {
            mAsyncTaskDao.insertUserSub(subscriptions[0]);
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask <String, Void, Void>{

        private SubsDao mAsyncTaskDao;

        deleteAsyncTask(SubsDao subsDao) {
            mAsyncTaskDao = subsDao;
        }

        @Override
        protected Void doInBackground(String... name) {
            mAsyncTaskDao.deleteSubscription(name[0]);
            return null;
        }
    }
}
