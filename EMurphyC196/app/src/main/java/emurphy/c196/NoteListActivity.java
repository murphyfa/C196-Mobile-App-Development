package emurphy.c196;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import emurphy.c196.Adapter.NoteAdapter;
import emurphy.c196.Database.CourseEntity;
import emurphy.c196.ViewModel.NoteViewModel;
import emurphy.c196.databinding.ActivityNotesListBinding;

public class NoteListActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID = "EXTRA_COURSE_ID";

    private ActivityNotesListBinding binding;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Intent intent = getIntent();
        long courseId = intent.getLongExtra(EXTRA_COURSE_ID, 0);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        CourseEntity course = noteViewModel.getCourseById(courseId);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        NoteAdapter adapter = new NoteAdapter(noteViewModel, course);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        noteViewModel.getAllNotesForCourseLive(courseId).observe(this, noteEntities -> adapter.setNotes(noteEntities));

        binding.fab.setOnClickListener(view -> {
            Intent noteIntent = new Intent(NoteListActivity.this, NoteEditorActivity.class);
            noteIntent.putExtra(NoteEditorActivity.EXTRA_COURSE_ID, course.getCourse_id());
            startActivity(noteIntent);
        });
    }
}
