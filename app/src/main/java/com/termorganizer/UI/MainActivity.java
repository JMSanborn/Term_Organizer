package com.termorganizer.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.termorganizer.Database.Repository;
import com.termorganizer.Entity.Course;
import com.termorganizer.Entity.Term;
import com.termorganizer.R;

import java.util.List;

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
        Repository repository=new Repository(getApplication());
        Term term=new Term(1,"demo term", "0/00/00", "0/00/00");
        Course course =new Course(1,"demo course", "0/00/00", "0/00/00",1, "demo term", "Plan to take", "Snoopy", "876-5309", "snoopydog1@gmail.com");
        repository.insert(term);
        repository.insert(course);
    }

}