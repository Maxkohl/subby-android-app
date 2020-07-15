package com.example.subby;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SubsRepository {
    private SubsDao mSubDao;
    private LiveData<Double> mTotalCost;

    public SubsRepository(Application application) {
        SubsRoomDatabase db = SubsRoomDatabase.getDatabase(application);
        mSubDao = db.subsDao();
        mTotalCost = mSubDao.getTotalCost();
    }

    public LiveData<List<Subscription>> getAllSubs(boolean isPaid) {
        return mSubDao.getAllUserSubs(isPaid);
    }

    public void insert(Subscription sub) {
        new insertAsyncTask(mSubDao).execute(sub);
    }

    public void deleteSub(String name) {new deleteAsyncTask(mSubDao).execute(name);}

    public LiveData<Double> getTotalCost() { return mTotalCost;}

    public void updateSubPaid(Subscription sub) {new updateAsyncTask(mSubDao).execute(sub);}


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

    private class updateAsyncTask extends AsyncTask<Subscription, Void, Void>{
        private SubsDao mASyncTaskDao;
        updateAsyncTask(SubsDao subsDao) {mASyncTaskDao = subsDao;}

        @Override
        protected Void doInBackground(Subscription... subscriptions) {
            Subscription current = subscriptions[0];
            boolean isPaid = current.isPaid();
            current.setPaid(!isPaid);
            mASyncTaskDao.updateSubPaid(current);
            return null;
        }

    }
}
