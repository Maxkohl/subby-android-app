package com.example.subby.ui.paid;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.subby.SubsRepository;
import com.example.subby.Subscription;

import java.util.List;

public class PaidModelView extends AndroidViewModel {

    private SubsRepository mRepository;

    public PaidModelView(@NonNull Application application) {
        super(application);
        mRepository = new SubsRepository(application);
    }

    public LiveData<List<Subscription>> getAllSubs(boolean isPaid) {
        return mRepository.getAllSubs(isPaid);
    }

    public void updateSubPaid(Subscription subscription) { mRepository.updateSubPaid(subscription);}


}