package my.edu.utar.neixpasswordmanager.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {PasswordElem.class}, version = 2, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PasswordElemDao pwdElemDao();

    private static AppDatabase instance = null;

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "password_db")
                // Wipes and rebuilds instead of migrating
                // if no Migration object.
                // Migration is not part of this practical.
                .fallbackToDestructiveMigration()
                .build();
    }


    public static AppDatabase getDatabase(Context context){
        if(instance == null){
            synchronized (AppDatabase.class){
                if(instance == null)
                    instance = buildDatabase(context);
            }
        }
        return instance;
    }
}
