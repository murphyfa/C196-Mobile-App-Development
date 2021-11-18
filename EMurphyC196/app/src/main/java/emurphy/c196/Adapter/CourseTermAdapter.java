package emurphy.c196.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import emurphy.c196.Database.CourseTerm;
import emurphy.c196.R;

public class CourseTermAdapter extends RecyclerView.Adapter<CourseTermAdapter.CourseTermViewHolder> {
    private List<CourseTerm> courseTerms = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CourseTermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item, parent, false);
        return new CourseTermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseTermViewHolder holder, int position) {
        CourseTerm currentCourse = courseTerms.get(position);
        holder.txtCourseTitle.setText(currentCourse.getTitle());
        holder.txtCourseTerm.setText(currentCourse.getTerm_title());
        holder.txtCourseStartDate.setText("Start: " + currentCourse.getStart_date());
        holder.txtCourseEndDate.setText("End: " + currentCourse.getEnd_date());
    }

    @Override
    public int getItemCount() {
        return courseTerms.size();
    }

    public void setCourseTerms(List<CourseTerm> courseTerms) {
        this.courseTerms = courseTerms;
        notifyDataSetChanged();
    }

    public class CourseTermViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCourseTitle, txtCourseTerm, txtCourseStartDate, txtCourseEndDate;

        public CourseTermViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCourseTitle = itemView.findViewById(R.id.course_title);
            txtCourseTerm = itemView.findViewById(R.id.term_title);
            txtCourseStartDate = itemView.findViewById(R.id.course_start_date);
            txtCourseEndDate = itemView.findViewById(R.id.course_end_date);

            itemView.setOnClickListener((x) -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(courseTerms.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CourseTerm course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
