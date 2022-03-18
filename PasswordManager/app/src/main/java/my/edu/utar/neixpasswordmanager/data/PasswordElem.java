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

    @ColumnInfo(name = "pin_number")
    public String pin_number;

    @ColumnInfo(name = "security_question_1")
    public String security_question_1;

    @ColumnInfo(name = "security_answer_1")
    public String security_answer_1;

    @ColumnInfo(name = "security_question_2")
    public String security_question_2;

    @ColumnInfo(name = "security_answer_2")
    public String security_answer_2;

    @ColumnInfo(name = "security_question_3")
    public String security_question_3;

    @ColumnInfo(name = "security_answer_3")
    public String security_answer_3;

    @ColumnInfo(name = "created_date")
    public Calendar created_date;

    @ColumnInfo(name = "last_updated_date")
    public Calendar last_updated_date;

    public PasswordElem(){
        this.title = "";
        this.username = "";
        this.password = "";
        this.website = "";
        this.pin_number = "";

        this.security_question_1 = "";
        this.security_question_2 = "";
        this.security_answer_3 = "";

        this.security_answer_1 = "";
        this.security_answer_2 = "";
        this.security_answer_3 = "";

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

    public String getPin_number() {
        return pin_number;
    }

    public void setPin_number(String pin_number) {
        this.pin_number = pin_number;
    }

    public String getSecurity_question_1() {
        return security_question_1;
    }

    public void setSecurity_question_1(String security_question_1) {
        this.security_question_1 = security_question_1;
    }

    public String getSecurity_answer_1() {
        return security_answer_1;
    }

    public void setSecurity_answer_1(String security_answer_1) {
        this.security_answer_1 = security_answer_1;
    }

    public String getSecurity_question_2() {
        return security_question_2;
    }

    public void setSecurity_question_2(String security_question_2) {
        this.security_question_2 = security_question_2;
    }

    public String getSecurity_answer_2() {
        return security_answer_2;
    }

    public void setSecurity_answer_2(String security_answer_2) {
        this.security_answer_2 = security_answer_2;
    }

    public String getSecurity_question_3() {
        return security_question_3;
    }

    public void setSecurity_question_3(String security_question_3) {
        this.security_question_3 = security_question_3;
    }

    public String getSecurity_answer_3() {
        return security_answer_3;
    }

    public void setSecurity_answer_3(String security_answer_3) {
        this.security_answer_3 = security_answer_3;
    }

    public void updateValue(String[] arr){
        this.title = arr[0];
        this.username = arr[1];
        this.password = arr[2];
        this.website = arr[3];
        this.last_updated_date = Calendar.getInstance();
    }

    public void updateOptionalValue(String[] arr){
        this.pin_number = arr[0];
        this.security_question_1 = arr[1];
        this.security_answer_1 = arr[2];
        this.security_question_2 = arr[3];
        this.security_answer_2 = arr[4];
        this.security_question_3 = arr[5];
        this.security_answer_3 = arr[6];
        this.last_updated_date = Calendar.getInstance();
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

    @Override
    public String toString() {
        return "PasswordElem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", website='" + website + '\'' +
                ", created_date=" + created_date +
                ", last_updated_date=" + last_updated_date +
                '}';
    }
}
