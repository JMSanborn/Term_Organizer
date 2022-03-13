package com.termorganizer.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.termorganizer.DAO.TermDAO;
import com.termorganizer.Entity.Term;

@Database(entities={Term.class}, version=2, exportSchema = false)
public abstract class TermDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();

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
