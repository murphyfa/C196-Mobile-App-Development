package emurphy.c196.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import emurphy.c196.Database.AssessmentEntity;
import emurphy.c196.Database.CourseEntity;
import emurphy.c196.R;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private List<AssessmentEntity> assessments = new ArrayList<>();
    private CourseEntity course;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        AssessmentEntity currentAssessment = assessments.get(position);
        holder.txtAssessmentName.setText(currentAssessment.getTitle());
        holder.txtAssessmentCourse.setText(course.getTitle());
        holder.txtAssessmentDate.setText("Date: " + currentAssessment.getStart_date());
        holder.txtAssessmentType.setText(currentAssessment.getType());
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<AssessmentEntity> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    public void setExtended(List<AssessmentEntity> assessments, CourseEntity course) {
        this.assessments = assessments;
        this.course = course;
        notifyDataSetChanged();
    }

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private TextView txtAssessmentName, txtAssessmentCourse, txtAssessmentDate, txtAssessmentType;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAssessmentName = itemView.findViewById(R.id.assessment_title);
            txtAssessmentCourse = itemView.findViewById(R.id.course_title);
            txtAssessmentDate = itemView.findViewById(R.id.assessment_date);
            txtAssessmentType = itemView.findViewById(R.id.assessment_type);

            itemView.setOnClickListener((x) -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(assessments.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(AssessmentEntity Assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
