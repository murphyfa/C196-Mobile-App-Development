package emurphy.c196.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import emurphy.c196.Database.CourseEntity;
import emurphy.c196.Database.CourseTerm;
import emurphy.c196.Database.TermEntity;
import emurphy.c196.R;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<CourseEntity> courses = new ArrayList<>();
    private TermEntity term;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseEntity currentCourse = courses.get(position);
        holder.txtCourseTitle.setText(currentCourse.getTitle());
        holder.txtCourseTerm.setText(term.getTitle());
        holder.txtCourseStartDate.setText("Start: " + currentCourse.getStart_date());
        holder.txtCourseEndDate.setText("End: " + currentCourse.getEnd_date());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public void setCoursesExtended(List<CourseEntity> courses, TermEntity term){
        this.courses = courses;
        this.term = term;
        notifyDataSetChanged();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCourseTitle, txtCourseTerm, txtCourseStartDate, txtCourseEndDate;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCourseTitle = itemView.findViewById(R.id.course_title);
            txtCourseTerm = itemView.findViewById(R.id.term_title);
            txtCourseStartDate = itemView.findViewById(R.id.course_start_date);
            txtCourseEndDate = itemView.findViewById(R.id.course_end_date);

            itemView.setOnClickListener((x) -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(courses.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CourseEntity course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
