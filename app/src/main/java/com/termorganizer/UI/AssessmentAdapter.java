package com.termorganizer.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.termorganizer.Entity.Assessment;
import com.termorganizer.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("assessmentTitle", current.getAssessmentTitle());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("assessmentStartDate", current.getAssessmentStartDate());
                    intent.putExtra("assessmentEndDate", current.getAssessmentEndDate());
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    context.startActivity(intent);
                }
            });

        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String assessmentTitle = current.getAssessmentTitle();
            holder.assessmentItemView.setText(assessmentTitle);
        } else {
            holder.assessmentItemView.setText("No product name");
        }
    }
        public void setAssessments (List< Assessment > assessments) {
            mAssessments = assessments;
            notifyDataSetChanged();

        }
        @Override
        public int getItemCount () {
            if (mAssessments != null) {
                return mAssessments.size();
            } else return 0;
        }

}
