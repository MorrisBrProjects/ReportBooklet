package de.morrisbr.reportbooklet.ui.berichte;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BerichteViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public BerichteViewModel() {
        MutableLiveData mutableLiveData = new MutableLiveData();
        this.mText = mutableLiveData;
        mutableLiveData.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return this.mText;
    }
}