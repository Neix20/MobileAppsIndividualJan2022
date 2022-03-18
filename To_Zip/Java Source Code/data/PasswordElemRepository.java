package my.edu.utar.neixpasswordmanager.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PasswordElemRepository {
    private PasswordElemDao mPasswordElemDao;
    private LiveData<List<PasswordElem>> passwordElemList;

    public PasswordElemRepository(Application app){
        AppDatabase db = AppDatabase.getDatabase(app);
        mPasswordElemDao = db.pwdElemDao();
        passwordElemList = mPasswordElemDao.getPasswordElemList();
    }

    public LiveData<List<PasswordElem>> getPasswordElemList() {
        return passwordElemList;
    }
    public LiveData<List<PasswordElem>> getPasswordElemListDesc() {
        return mPasswordElemDao.getPasswordElemListDesc();
    }
    public LiveData<List<PasswordElem>> getPasswordElemListDate() {
        return mPasswordElemDao.getPasswordElemListDate();
    }
    public LiveData<List<PasswordElem>> getPasswordElemListDateDesc() {
        return mPasswordElemDao.getPasswordElemListDateDesc();
    }

    public PasswordElem getPassword(long pwdId) throws ExecutionException, InterruptedException {
        return new getPasswordAsync(mPasswordElemDao).execute(pwdId).get();
    }

    public void insertPassword (PasswordElem pwd) {
        new insertPasswordAsync(mPasswordElemDao).execute(pwd);
    }

    public void updatePassword(PasswordElem pwd){
        new updatePasswordAsync(mPasswordElemDao).execute(pwd);
    }

    public void deletePassword(PasswordElem pwd){
        new deletePasswordAsync(mPasswordElemDao).execute(pwd);
    }

    public void deleteAllPassword(){
        new deleteAllPasswordAsync(mPasswordElemDao).execute();
    }

    private static class getPasswordAsync extends AsyncTask<Long, Void, PasswordElem> {

        private PasswordElemDao mPasswordElemDaoAsync;

        public getPasswordAsync(PasswordElemDao mPasswordElemDaoAsync) {
            this.mPasswordElemDaoAsync = mPasswordElemDaoAsync;
        }

        @Override
        protected PasswordElem doInBackground(Long... ids) {
            return mPasswordElemDaoAsync.getPasswordElem(ids[0]);
        }
    }

    private static class insertPasswordAsync extends AsyncTask<PasswordElem, Void, Long>{
        private PasswordElemDao mPasswordElemDaoAsync;

        public insertPasswordAsync(PasswordElemDao mPasswordElemDaoAsync) {
            this.mPasswordElemDaoAsync = mPasswordElemDaoAsync;
        }

        @Override
        protected Long doInBackground(PasswordElem... passwordElems) {
            long id = mPasswordElemDaoAsync.insertPassword(passwordElems[0]);
            return id;
        }
    }

    private static class updatePasswordAsync extends AsyncTask<PasswordElem, Void, Void>{
        private PasswordElemDao mPasswordElemDaoAsync;

        public updatePasswordAsync(PasswordElemDao mPasswordElemDaoAsync) {
            this.mPasswordElemDaoAsync = mPasswordElemDaoAsync;
        }

        @Override
        protected Void doInBackground(PasswordElem... passwordElems) {
            mPasswordElemDaoAsync.updatePassword(passwordElems[0]);
            return null;
        }
    }

    private static class deletePasswordAsync extends AsyncTask<PasswordElem, Void, Void>{
        private PasswordElemDao mPasswordElemDaoAsync;

        public deletePasswordAsync(PasswordElemDao mPasswordElemDaoAsync) {
            this.mPasswordElemDaoAsync = mPasswordElemDaoAsync;
        }

        @Override
        protected Void doInBackground(PasswordElem... passwordElems) {
            mPasswordElemDaoAsync.deletePassword(passwordElems[0]);
            return null;
        }
    }

    private static class deleteAllPasswordAsync extends AsyncTask<Void, Void, Void>{
        private PasswordElemDao mPasswordElemDaoAsync;

        public deleteAllPasswordAsync(PasswordElemDao mPasswordElemDaoAsync) {
            this.mPasswordElemDaoAsync = mPasswordElemDaoAsync;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mPasswordElemDaoAsync.deleteAllPassword();
            return null;
        }
    }
}
