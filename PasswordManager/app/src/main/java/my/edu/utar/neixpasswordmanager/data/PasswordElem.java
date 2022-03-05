package my.edu.utar.neixpasswordmanager.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Objects;


@Entity(tableName = "password_tbl")
public class PasswordElem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "website")
    public String website;

    @ColumnInfo(name = "created_date")
    public Calendar created_date;

    @ColumnInfo(name = "last_updated_date")
    public Calendar last_updated_date;

    public PasswordElem(String title, String username, String password, String website) {
        this.title = title;
        this.username = username;
        this.password = password;
        this.website = website;
        this.created_date = Calendar.getInstance();
        this.last_updated_date = Calendar.getInstance();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Calendar getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Calendar created_date) {
        this.created_date = created_date;
    }

    public Calendar getLast_updated_date() {
        return last_updated_date;
    }

    public void setLast_updated_date(Calendar last_updated_date) {
        this.last_updated_date = last_updated_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordElem that = (PasswordElem) o;
        return id == that.id
                && title.equals(that.title)
                && username.equals(that.username)
                && password.equals(that.password)
                && website.equals(that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, username, password, website, created_date, last_updated_date);
    }
}
