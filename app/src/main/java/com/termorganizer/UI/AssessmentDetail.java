package com.termorganizer.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.termorganizer.R;
import com.termorganizer.Utilities.AssessmentTypeEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {
    EditText editAssessmentTitle;
    EditText editAssessmentStartDate;
    EditText editAssessmentEndDate;
    TextView viewCourseTitle;
    String assessmentType;
    String assessmentTitle;
    String assessmentStartDate;
    String assessmentEndDate;
    String courseTitle;
    int assessmentID;
    int courseID;

    Repository repository;
    Assessment currentAssessment;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    Spinner editType;
    private ArrayAdapter<AssessmentTypeEnum> assessmentTypeAdapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PopulateStatusSpinner();
        Intent intent = getIntent();
        courseTitle = intent.getStringExtra(CourseDetail.EXTRA_TEXT);
        editAssessmentTitle = findViewById(R.id.editAssessmentTitle);
        editAssessmentStartDate = findViewById(R.id.editAssessmentStartDate);
        editAssessmentEndDate = findViewById(R.id.editAssessmentEndDate);
        viewCourseTitle = findViewById(R.id.viewCourseTitle);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assessmentTitle = getIntent().getStringExtra("assessmentTitle");
        assessmentStartDate = getIntent().getStringExtra("assessmentStartDate");
        assessmentEndDate = getIntent().getStringExtra("assessmentEndDate");
        courseID = getIntent().getIntExtra("courseID", -1);
        editAssessmentTitle.setText(assessmentTitle);
        editAssessmentStartDate.setText(assessmentStartDate);
        editAssessmentEndDate.setText(assessmentEndDate);
        viewCourseTitle.setText(courseTitle);
        if (getIntent().getStringExtra("courseStatus") != null){
            setType(AssessmentTypeEnum.valueOf(getIntent().getStringExtra("assessmentType")));
        }
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

        editAssessmentStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editAssessmentStartDate.getText().toString();
                if (info.equals("")) info = "02/10/22";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, startDate, myCalendarStart
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

        editAssessmentEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editAssessmentEndDate.getText().toString();
                if (info.equals("")) info = "02/10/22";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
    }

    private void updateLabelStart () {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editAssessmentStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editAssessmentEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    // Assessment Type Spinner
    private int getIndexInSpinner(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    private void setType(AssessmentTypeEnum value){
        editType.setSelection(getIndexInSpinner(editType, value.toString()));
    }

    private void PopulateStatusSpinner(){
        editType = findViewById(R.id.assessmentSpinner);
        assessmentTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AssessmentTypeEnum.values());
        editType.setAdapter(assessmentTypeAdapter);
    }

    private AssessmentTypeEnum getSpinnerValue() {
        return (AssessmentTypeEnum) editType.getSelectedItem();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assessmentdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.notifyAssessmentStart:
                String screenAssessmentStart = editAssessmentStartDate.getText().toString();
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Date date = null;
                try {
                    date = sdf.parse(screenAssessmentStart);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = date.getTime();
                Intent notifyAssessmentStart = new Intent(AssessmentDetail.this, MyReceiver.class);
                notifyAssessmentStart.putExtra("key", assessmentTitle + " STARTS " + assessmentStartDate + "!");
                PendingIntent pendingAssessmentStart = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert, notifyAssessmentStart, 0);
                AlarmManager startAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                startAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingAssessmentStart);
                return true;
            case R.id.notifyAssessmentEnd:
                String screenAssessmentEnd = editAssessmentEndDate.getText().toString();
                dateFormat = "MM/dd/yy";
                sdf = new SimpleDateFormat(dateFormat, Locale.US);
                date = null;
                try {
                    date = sdf.parse(screenAssessmentEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                trigger = date.getTime();
                Intent notifyAssessmentEnd = new Intent(AssessmentDetail.this, MyReceiver.class);
                notifyAssessmentEnd.putExtra("key", assessmentTitle + " STARTS " + assessmentEndDate + "!");
                PendingIntent pendingAssessmentEnd = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert, notifyAssessmentEnd, 0);
                AlarmManager endAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                endAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingAssessmentEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void assessmentSave(View view){
        Assessment assessment;
        if (assessmentID == -1) {
            int newID = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentID() + 1;
            assessment = new Assessment(newID, editAssessmentTitle.getText().toString(), getSpinnerValue(), editAssessmentStartDate.getText().toString(), editAssessmentEndDate.getText().toString(),  courseID, courseTitle);
            repository.insert(assessment);
            Toast.makeText(AssessmentDetail.this, "New Assessment added", Toast.LENGTH_LONG).show();
        }else{
            assessment = new Assessment(assessmentID, editAssessmentTitle.getText().toString(), getSpinnerValue(), editAssessmentStartDate.getText().toString(), editAssessmentEndDate.getText().toString(), courseID, courseTitle);
            repository.update(assessment);
            Toast.makeText(AssessmentDetail.this, "Assessment Updated", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(AssessmentDetail.this, TermList.class);
        startActivity(intent);
    }
    public void assessmentDelete(View view){
        for (Assessment assessment : repository.getAllAssessments()){
            if (assessment.getAssessmentID() == assessmentID) currentAssessment = assessment;
        }
        repository.delete(currentAssessment);
        Toast.makeText(AssessmentDetail.this, currentAssessment.getAssessmentTitle() + " was deleted. Please refresh page.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AssessmentDetail.this, TermList.class);
        startActivity(intent);
    }
}
