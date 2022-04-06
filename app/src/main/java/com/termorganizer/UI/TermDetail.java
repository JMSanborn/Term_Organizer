package com.termorganizer.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.termorganizer.Database.Repository;
import com.termorganizer.Entity.Course;
import com.termorganizer.Entity.Term;
import com.termorganizer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetail extends AppCompatActivity {

    public static final String EXTRA_TEXT ="com.termorganizer.UI.EXTRA_TEXT";

    EditText editTermName;
    EditText editStartDate;
    EditText editEndDate;
    String termName;
    String start;
    String end;
    int termID;
    Repository repository;
    Term currentTerm;
    int numCourses;
    Course filteredCourses;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository =new Repository(getApplication());
        editTermName = findViewById(R.id.editTermName);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        termID = getIntent().getIntExtra("id", -1);
        termName = getIntent().getStringExtra("name");
        start = getIntent().getStringExtra("termStart");
        end = getIntent().getStringExtra("termEnd");
        editTermName.setText(termName);
        editStartDate.setText(start);
        editEndDate.setText(end);
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //Course List
        RecyclerView recyclerView=findViewById(R.id.recyclerViewCoursesPerTerm);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : repository.getAllCourses()){
            if(course.getTermID() == termID) filteredCourses.add(course);
        }
                adapter.setCourses(filteredCourses);
                numCourses = filteredCourses.size();

        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String info = editStartDate.getText().toString();
                if (info.equals("")) info = "02/10/22";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


        //EndTerm
        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "02/10/22";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


    }



    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
}


    public void saveButton(View view) {

        Term term;
        if (termID == -1) {
            int newID = repository.getAllTerms().get(repository.getAllTerms().size() -1).getTermID() + 1;
            term = new Term(newID, editTermName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
            repository.insert(term);
            Toast.makeText(TermDetail.this, "New Term saved.", Toast.LENGTH_LONG).show();

        } else {
            term = new Term(termID, editTermName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
            repository.update(term);
            Toast.makeText(TermDetail.this, "Term updated.", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(TermDetail.this, TermList.class);
        startActivity(intent);
    }

    public boolean deleteButton(View view) {
        for (Term term : repository.getAllTerms()) {
            if (term.getTermID() == termID) currentTerm = term;
        }
            if (numCourses == 0) {
                repository.delete(currentTerm);
                Toast.makeText(TermDetail.this, currentTerm.getTermName() + " was deleted.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TermDetail.this, TermList.class);
                startActivity(intent);
            } else {
                Toast.makeText(TermDetail.this, currentTerm.getTermName() + " " + "cannot be removed. Please remove all courses before attempting to removing Term.", Toast.LENGTH_LONG).show();
            }
            return true;
        }

    public void addCourse(View view){
        EditText editName =(EditText) findViewById(R.id.editTermName);
        String termName = editName.getText().toString();
        Intent intent = new Intent(TermDetail.this, CourseDetail.class);
        intent.putExtra(EXTRA_TEXT, termName);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }
}