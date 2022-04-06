package com.termorganizer.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.termorganizer.Database.Repository;
import com.termorganizer.Entity.Assessment;
import com.termorganizer.Entity.Course;
import com.termorganizer.Entity.Term;
import com.termorganizer.R;

import java.util.List;

public class TermList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        Repository repository=new Repository(getApplication());
        List<Term> terms= repository.getAllTerms();
        final TermAdapter adapter=new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
     return super.onOptionsItemSelected(item);
}
    public void goToTermDetail(View view) {
        Intent intent=new Intent(TermList.this,TermDetail.class);
        startActivity(intent);
    }
    public void goToCourseList(View view) {
        Intent intent = new Intent(TermList.this, CourseList.class);
        startActivity(intent);
    }
    public void goToAssessmentList(View view) {
        Intent intent=new Intent(TermList.this, AssessmentList.class);
        startActivity(intent);
    }
}