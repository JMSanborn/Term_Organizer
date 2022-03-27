package com.termorganizer.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.recyclerview.widget.RecyclerView;

import com.termorganizer.Entity.Course;
import com.termorganizer.Entity.Term;
import com.termorganizer.R;

import java.text.CollationElementIterator;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("title", current.getCourseTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("courseInstName", current.getCourseInstName());
                    intent.putExtra("courseInstPhone", current.getCourseInstPhone());
                    intent.putExtra("courseInstEmail", current.getCourseInstEmail());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String courseTitle = current.getCourseTitle();
            holder.courseItemView.setText(courseTitle);
        } else {
            holder.courseItemView.setText(R.string.courseItemView);
        }

    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;

    }

    @Override
    public int getItemCount () {
        if (mCourses != null) {
            return mCourses.size();
        } else return 0;
    }
}

