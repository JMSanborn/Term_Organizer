package com.termorganizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.termorganizer.Database.Repository;
import com.termorganizer.Entity.Term;
import com.termorganizer.UI.TermList;

public class MainActivity extends AppCompatActivity {
    //public static int numAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void enterHere(View view) {
        Intent intent=new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
        Repository repo=new Repository(getApplication());
       //Term term=new Term(,"");
        //repo.insert(term);
    }
}