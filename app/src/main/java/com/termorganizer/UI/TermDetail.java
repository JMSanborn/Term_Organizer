package com.termorganizer.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.termorganizer.Database.Repository;
import com.termorganizer.Entity.Term;
import com.termorganizer.R;

public class TermDetail extends AppCompatActivity {
    EditText editName;
    String name;
    int termID;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editName = findViewById(R.id.editTermName);
        termID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        editName.setText(name);
        repository = new Repository(getApplication());
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
            int newID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
            term = new Term(newID, editName.getText().toString());
            repository.insert(term);
        } else {
            term = new Term(termID, editName.getText().toString());
            repository.update(term);
        }
    }
}