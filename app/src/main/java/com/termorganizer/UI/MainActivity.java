package com.termorganizer.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.termorganizer.Database.Repository;
import com.termorganizer.Entity.Assessment;
import com.termorganizer.Entity.Course;
import com.termorganizer.Entity.Term;
import com.termorganizer.R;

import java.util.List;

/** Main class for Term Organizer Mobile Application".
 * @author Jason M. Sanborn
 * Mobile Aplication Development  -C196
 * Instructor - Carolyn Sher-DeCusatis
 */

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void enterHere(View view) {
        Intent intent=new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
        Repository repository=new Repository(getApplication());
        Term term=new Term(1,"demo term", "3/30/22", "3/30/22");
        Course course =new Course(1,"demo course", "3/30/22", "3/30/22",1, "demo term", null, "Snoopy", "876-5309", "snoopydog1@gmail.com", "Bow wow wow");
        Assessment  assessment =new Assessment(1, "demo assessment", null, "3/30/22", "3/30/22", 1,"demo course");
        repository.insert(term);
        repository.insert(course);
        repository.insert(assessment);
    }

}