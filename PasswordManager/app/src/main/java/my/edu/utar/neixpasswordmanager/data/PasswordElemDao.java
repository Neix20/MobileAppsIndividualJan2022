package my.edu.utar.neixpasswordmanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PasswordElemDao {
    @Query("SELECT * FROM password_tbl")
    LiveData<List<PasswordElem>> getPasswordElemList();

    @Query("SELECT * FROM password_tbl WHERE id = :pwd_id")
    LiveData<PasswordElem> getPasswordElemLive(long pwd_id);

    @Query("SELECT * FROM password_tbl WHERE id = :pwd_id")
    PasswordElem getPasswordElem(long pwd_id);

    @Insert
    long insertPassword(PasswordElem pwd);

    @Update
    void updatePassword (PasswordElem pwd);

    @Delete
    void deletePassword (PasswordElem pwd);

    @Query("DELETE FROM password_tbl")
    void deleteAllPassword();
}
