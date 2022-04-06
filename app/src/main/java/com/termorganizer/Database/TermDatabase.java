package com.termorganizer.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.termorganizer.DAO.AssessmentDAO;
import com.termorganizer.DAO.CourseDAO;
import com.termorganizer.DAO.TermDAO;
import com.termorganizer.Entity.Assessment;
import com.termorganizer.Entity.Course;
import com.termorganizer.Entity.Term;
import com.termorganizer.Utilities.DateConverter;
@TypeConverters(DateConverter.class)
@Database(entities={Term.class, Course.class, Assessment.class}, version=3, exportSchema = false)
public abstract class TermDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

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
