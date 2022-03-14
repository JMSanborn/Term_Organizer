package com.termorganizer.Entity;

import android.os.Bundle;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termName;
    private String termStart;
    private String termEnd;

    @Override
    public String toString() {
        return "Term{" +
                "termID=" + termID + "termName=" + termName + '\'' + ", termStart=" + termStart + ", termEnd=" + termEnd + '}';
    }
    public Term(int termID, String termName, String termStart, String termEnd)
    {
        this.termID = termID;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public int getTermID() {return termID;}

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermName(){return termName;}

    public void setTermName(String termName){this.termName = termName;}

    public String getTermStart() {return termStart;}

    public void setTermStart(String termStart) {this.termStart = termStart;}

    public String getTermEnd() {return termEnd;}

    public void setTermEnd(String termEnd) {this.termEnd = termEnd; }
}
