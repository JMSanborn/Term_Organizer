package com.termorganizer.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.termorganizer.Database.Repository;
import com.termorganizer.Entity.Assessment;
import com.termorganizer.Entity.Course;
import com.termorganizer.R;
import com.termorganizer.Utilities.CourseStatusEnum;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetail extends AppCompatActivity {

    public static final String EXTRA_TEXT ="com.termorganizer.UI.EXTRA_TEXT";

    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    EditText editInstName;
    EditText editInstPhone;
    EditText editInstEmail;
    EditText editCourseNotes;
    TextView editTermName;
    String courseTitle;
    String start;
    String end;
    String termName;
    String courseInstName;
    String courseInstPhone;
    String courseInstEmail;
    String courseNotes;
    int termID;
    int courseID;
    Repository repository;
    Course currentCourse;
    String myFormat;
    SimpleDateFormat sdf;
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
        repository =new Repository(getApplication());
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
        editCourseNotes = findViewById(R.id.editCourseNotes);
        courseID = getIntent().getIntExtra("id", -1);
        courseTitle = getIntent().getStringExtra("courseTitle");
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");
        termID = getIntent().getIntExtra("termID", -1);
        courseInstName = getIntent().getStringExtra("courseInstName");
        courseInstPhone = getIntent().getStringExtra("courseInstPhone");
        courseInstEmail = getIntent().getStringExtra("courseInstEmail");
        courseNotes = getIntent().getStringExtra("courseNotes");
        editTitle.setText(courseTitle);
        editStartDate.setText(start);
        editEndDate.setText(end);
        editTermName.setText(termName);
        editInstName.setText(courseInstName);
        editInstPhone.setText(courseInstPhone);
        editInstEmail.setText(courseInstEmail);
        editCourseNotes.setText(courseNotes);
        if (getIntent().getStringExtra("courseStatus") != null){
            setStatus(CourseStatusEnum.valueOf(getIntent().getStringExtra("courseStatus")));
        }
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //Course List
        RecyclerView recyclerView=findViewById(R.id.recyclerViewAssessmentsPerCourse);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment assessment : repository.getAllAssessments()){
            if(assessment.getCourseID() == courseID) filteredAssessments.add(assessment);
        }
        adapter.setAssessments(filteredAssessments);

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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.coursedetail, menu);
        return true;
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.notifyCourseStart:
                String screenCourseStart = editStartDate.getText().toString();
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Date date = null;
                try {
                    date = sdf.parse(screenCourseStart);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = date.getTime();
                Intent notifyCourseStart = new Intent(CourseDetail.this, MyReceiver.class);
                notifyCourseStart.putExtra("key", courseTitle + " STARTS " + start + "!");
                PendingIntent pendingAssessmentStart = PendingIntent.getBroadcast(CourseDetail.this, ++MainActivity.numAlert, notifyCourseStart, 0);
                AlarmManager startAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                startAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingAssessmentStart);
                return true;
            case R.id.notifyCourseEnd:
                String screenCourseEnd = editEndDate.getText().toString();
                dateFormat = "MM/dd/yy";
                sdf = new SimpleDateFormat(dateFormat, Locale.US);
                date = null;
                try {
                    date = sdf.parse(screenCourseEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                trigger = date.getTime();
                Intent notifyCourseEnd = new Intent(CourseDetail.this, MyReceiver.class);
                notifyCourseEnd.putExtra("key", courseTitle + " ENDS " + end + "!");
                PendingIntent pendingAssessmentEnd = PendingIntent.getBroadcast(CourseDetail.this, ++MainActivity.numAlert, notifyCourseEnd, 0);
                AlarmManager endAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                endAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingAssessmentEnd);
                return true;
            case R.id.shareCourseNotes:
                Intent sendIntent=new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,editCourseNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE,"Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void courseSave(View view) {
        Course course;
        if (courseID == -1) {
            int newID = repository.getAllCourses().get(repository.getAllCourses().size() -1).getCourseID() + 1;
            course = new Course (newID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), termID, termName, getSpinnerValue(), editInstName.getText().toString(), editInstPhone.getText().toString(), editInstEmail.getText().toString(), editCourseNotes.getText().toString());
            repository.insert(course);
            Toast.makeText(CourseDetail.this, "New Course added.", Toast.LENGTH_LONG).show();
        } else {
            course = new Course(courseID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(),termID, termName, getSpinnerValue(), editInstName.getText().toString(), editInstPhone.getText().toString(), editInstEmail.getText().toString(), editCourseNotes.getText().toString());
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
        Toast.makeText(CourseDetail.this, currentCourse.getCourseTitle() + " was deleted.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CourseDetail.this, TermList.class);
        startActivity(intent);

    }
    public void addAssessment(View view){
        EditText editTitle =(EditText) findViewById(R.id.editCourseTitle);
        String courseTitle = editTitle.getText().toString();
        Intent intent = new Intent(CourseDetail.this, AssessmentDetail.class);
        intent.putExtra(EXTRA_TEXT, courseTitle);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }
}