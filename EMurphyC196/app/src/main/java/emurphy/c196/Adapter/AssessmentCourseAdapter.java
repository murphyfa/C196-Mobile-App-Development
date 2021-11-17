package emurphy.c196.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import emurphy.c196.Database.AssessmentCourse;
import emurphy.c196.R;

public class AssessmentCourseAdapter extends RecyclerView.Adapter<AssessmentCourseAdapter.AssessmentCourseViewHolder> {
    private List<AssessmentCourse> assessmentCourses = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AssessmentCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentCourseViewHolder holder, int position) {
        AssessmentCourse currentAssessmentCourse = assessmentCourses.get(position);
        holder.txtAssessmentCourseName.setText(currentAssessmentCourse.getTitle());
        holder.txtAssessmentCourseCourse.setText(currentAssessmentCourse.getCourse_title());
        holder.txtAssessmentCourseDate.setText("Date: " + currentAssessmentCourse.getStart_date());
        holder.txtAssessmentCourseType.setText(currentAssessmentCourse.getType());
    }

    @Override
    public int getItemCount() {
        return assessmentCourses.size();
    }

    public void setAssessmentCourses(List<AssessmentCourse> assessmentCourses) {
        this.assessmentCourses = assessmentCourses;
        notifyDataSetChanged();
    }

    public class AssessmentCourseViewHolder extends RecyclerView.ViewHolder {
        private TextView txtAssessmentCourseName, txtAssessmentCourseCourse, txtAssessmentCourseDate, txtAssessmentCourseType;

        public AssessmentCourseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAssessmentCourseName = itemView.findViewById(R.id.assessment_title);
            txtAssessmentCourseCourse = itemView.findViewById(R.id.course_title);
            txtAssessmentCourseDate = itemView.findViewById(R.id.assessment_date);
            txtAssessmentCourseType = itemView.findViewById(R.id.assessment_type);

            itemView.setOnClickListener((x) -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(assessmentCourses.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(AssessmentCourse AssessmentCourse);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
