package com.termorganizer.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.termorganizer.Database.Repository;
import com.termorganizer.Entity.Course;
import com.termorganizer.R;
import com.termorganizer.Utilities.CourseStatusEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CourseDetail extends AppCompatActivity {
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    EditText editInstName;
    EditText editInstPhone;
    EditText editInstEmail;
    TextView editTermName;
    String title;
    String start;
    String end;
    String courseStatus;
    String termName;
    String courseInstName;
    String courseInstPhone;
    String courseInstEmail;
    int termID;
    int courseID;
    Repository repository;
    Course currentCourse;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    Spinner editStatus;
    private ArrayAdapter<CourseStatusEnum> courseStatusAdapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PopulateStatusSpinner();
        Intent intent = getIntent();
        termName = intent.getStringExtra(TermDetail.EXTRA_TEXT);
        editTermName = findViewById(R.id.courseTerm);
        editTitle = findViewById(R.id.editCourseTitle);
        editStartDate = findViewById(R.id.editCourseStartDate);
        editEndDate = findViewById(R.id.editCourseEndDate);
        editInstName = findViewById(R.id.editInstName);
        editInstPhone = findViewById(R.id.editInstPhone);
        editInstEmail = findViewById(R.id.editInstEmail);
        courseID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");
        termID = getIntent().getIntExtra("termID", -1);
        termName = getIntent().getStringExtra("termName");
        courseInstName = getIntent().getStringExtra("courseInstName");
        courseInstPhone = getIntent().getStringExtra("courseInstPhone");
        courseInstEmail = getIntent().getStringExtra("courseInstEmail");
        editTitle.setText(title);
        editStartDate.setText(start);
        editEndDate.setText(end);
        editTermName.setText(termName);
        editInstName.setText(courseInstName);
        editInstPhone.setText(courseInstPhone);
        editInstEmail.setText(courseInstEmail);
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        repository = new Repository(getApplication());

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
                new DatePickerDialog(CourseDetail.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

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
                new DatePickerDialog(CourseDetail.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
    }

    // Course Status Spinner
    private int getIndexInSpinner(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    private void setStatus(CourseStatusEnum value){
        editStatus.setSelection(getIndexInSpinner(editStatus, value.toString()));
    }

    private void PopulateStatusSpinner(){
        editStatus = findViewById(R.id.courseSpinner);
        courseStatusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CourseStatusEnum.values());
        editStatus.setAdapter(courseStatusAdapter);
    }

    private CourseStatusEnum getSpinnerValue() {
        return (CourseStatusEnum) editStatus.getSelectedItem();
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
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


    public void courseSave(View view) {
        Course course;
        if (courseID == -1) {
            int newID = repository.getAllCourses().get(repository.getAllCourses().size() -1).getCourseID() + 1;
            course = new Course (newID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), termID, editTermName.getText().toString(), courseStatus, editInstName.getText().toString(), editInstPhone.getText().toString(), editInstEmail.getText().toString());
            repository.insert(course);
            Toast.makeText(CourseDetail.this, "New Course saved.", Toast.LENGTH_LONG).show();
        } else {
            course = new Course(courseID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(),termID, editTermName.getText().toString(), courseStatus, editInstName.getText().toString(), editInstPhone.getText().toString(), editInstEmail.getText().toString());
            repository.update(course);
            Toast.makeText(CourseDetail.this, "Course updated.", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(CourseDetail.this, TermList.class);
        startActivity(intent);
    }
    public void courseDelete(View view){
        for(Course course : repository.getAllCourses()) {
            if (course.getCourseID() == courseID) currentCourse = course;
        }
        repository.delete(currentCourse);
        Toast.makeText(CourseDetail.this, currentCourse.getCourseTitle() + " was deleted. Please refresh page.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CourseDetail.this, CourseList.class);
        startActivity(intent);

    }
}