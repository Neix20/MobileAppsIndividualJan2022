package my.edu.utar.neixpasswordmanager.ui.pwdList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PwdListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PwdListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}