package com.termorganizer.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.termorganizer.DAO.CourseDAO;
import com.termorganizer.DAO.TermDAO;
import com.termorganizer.Entity.Course;
import com.termorganizer.Entity.Term;
import com.termorganizer.Utilities.DateConverter;
@TypeConverters(DateConverter.class)
@Database(entities={Term.class, Course.class}, version=5, exportSchema = false)
public abstract class TermDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();

    private static volatile TermDatabase INSTANCE;

    static TermDatabase getDatabase(final Context context){
        if (INSTANCE==null) {
            synchronized (TermDatabase.class) {
                if (INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermDatabase.class, "myTermData.db ")
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
