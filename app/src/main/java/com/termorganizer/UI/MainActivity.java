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
        Repository repo=new Repository(getApplication());
        Term term=new Term(1,"Demo Term", "0/00/00", "0/00/00");
        repo.insert(term);
    }

    public void enterCourses(View view) {
        Intent intent=new Intent(MainActivity.this, CourseList.class);
        startActivity(intent);
        Repository repo=new Repository(getApplication());
        Course course =new Course(1,"Math");
        repo.insert(course);
    }
}