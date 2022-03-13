package com.termorganizer.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Terms")
public class Term {
    private String termName;

    @Override
    public String toString() {
        return "Term{" +
                "termID=" + termID + "termName=" + termName + '}';
    }
    public Term(int termID, String termName)
    {
        this.termID = termID;
        this.termName = termName;
    }
    @PrimaryKey(autoGenerate = true)
    private int termID;

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermName(){return termName;}

    public void setTermName(String termName){this.termName = termName;}
}
