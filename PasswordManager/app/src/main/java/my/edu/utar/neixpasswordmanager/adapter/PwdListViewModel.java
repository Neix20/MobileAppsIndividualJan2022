package my.edu.utar.neixpasswordmanager.adapter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import my.edu.utar.neixpasswordmanager.data.PasswordElem;
import my.edu.utar.neixpasswordmanager.data.PasswordElemRepository;

public class PwdListViewModel extends AndroidViewModel {

    private PasswordElemRepository mRepository;
    private LiveData<List<PasswordElem>> pwdList;

    public PwdListViewModel(@NonNull Application app) {
        super(app);

        mRepository = new PasswordElemRepository(app);
    }

    public LiveData<List<PasswordElem>> getPwdList(){
        if(pwdList == null){
            pwdList = mRepository.getPasswordElemList();
        }

        return pwdList;
    }

    public PasswordElem getPassword(long id) throws ExecutionException, InterruptedException {
        return mRepository.getPassword(id);
    }

    public void insertPassword(PasswordElem pwd) {
        mRepository.insertPassword(pwd);
    }

    public void updatePassword(PasswordElem pwd){
        mRepository.updatePassword(pwd);
    }

    public void deletePassword(PasswordElem pwd){
        mRepository.deletePassword(pwd);
    }

    public void deleteAllPassword() { mRepository.deleteAllPassword(); }
}
