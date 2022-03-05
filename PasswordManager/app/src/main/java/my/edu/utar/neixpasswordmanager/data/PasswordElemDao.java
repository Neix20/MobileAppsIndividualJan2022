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
    @Query("SELECT * FROM password_tbl ORDER BY title")
    LiveData<List<PasswordElem>> getPasswordElemList();

    @Query("SELECT * FROM password_tbl ORDER BY title DESC")
    LiveData<List<PasswordElem>> getPasswordElemListDesc();

    @Query("SELECT * FROM password_tbl ORDER BY created_date DESC")
    LiveData<List<PasswordElem>> getPasswordElemListDate();

    @Query("SELECT * FROM password_tbl ORDER BY created_date")
    LiveData<List<PasswordElem>> getPasswordElemListDateDesc();

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
