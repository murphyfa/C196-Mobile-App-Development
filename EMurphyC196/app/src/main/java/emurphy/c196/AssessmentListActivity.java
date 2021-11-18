package emurphy.c196;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import emurphy.c196.Adapter.AssessmentCourseAdapter;
import emurphy.c196.ViewModel.AssessmentViewModel;
import emurphy.c196.databinding.ActivityAssessmentsListBinding;

public class AssessmentListActivity extends AppCompatActivity {

    private ActivityAssessmentsListBinding binding;

    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAssessmentsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        AssessmentCourseAdapter adapter = new AssessmentCourseAdapter();
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessmentCourses().observe(this, assessmentCourses -> adapter.setAssessmentCourses(assessmentCourses));

        adapter.setOnItemClickListener(assessmentCourse -> {
            Intent intent = new Intent(AssessmentListActivity.this, AssessmentEditorActivity.class);
            intent.putExtra(AssessmentEditorActivity.EXTRA_COURSE_ID, assessmentCourse.getCourse_id());
            intent.putExtra(AssessmentEditorActivity.EXTRA_ASSESSMENT_ID, assessmentCourse.getAssessment_id());
            startActivity(intent);
        });

        binding.fab.setOnClickListener(view -> {
            if (assessmentViewModel.getAllCourses().isEmpty()) {
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "You must add at least one course before adding assessments", BaseTransientBottomBar.LENGTH_LONG);
                snackbar.show();
            } else {
                Intent intent = new Intent(AssessmentListActivity.this, AssessmentEditorActivity.class);
                startActivity(intent);
            }
        });
    }
}
