package emurphy.c196;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import emurphy.c196.Adapter.CourseTermAdapter;
import emurphy.c196.ViewModel.CourseViewModel;
import emurphy.c196.databinding.ActivityCoursesListBinding;

public class CourseListActivity extends AppCompatActivity {

    private ActivityCoursesListBinding binding;

    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCoursesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        CourseTermAdapter adapter = new CourseTermAdapter();
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getAllCourseTerms().observe(this, courseEntities -> adapter.setCourseTerms(courseEntities));

        adapter.setOnItemClickListener(courseTerm -> {
            Intent intent = new Intent(CourseListActivity.this, CourseEditorActivity.class);
            intent.putExtra(CourseEditorActivity.EXTRA_COURSE_ID, courseTerm.getCourse_id());
            intent.putExtra(CourseEditorActivity.EXTRA_TERM_ID, courseTerm.getTerm_id());
            startActivity(intent);
        });

        binding.fab.setOnClickListener(view -> {
            if (courseViewModel.getAllTerms().isEmpty()) {
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "You must add at least one term before adding courses", BaseTransientBottomBar.LENGTH_LONG);
                snackbar.show();
            } else {
                Intent intent = new Intent(CourseListActivity.this, CourseEditorActivity.class);
                startActivity(intent);
            }
        });
    }
}
