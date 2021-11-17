package emurphy.c196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import emurphy.c196.Adapter.CourseAdapter;
import emurphy.c196.Database.CourseEntity;
import emurphy.c196.Database.TermEntity;
import emurphy.c196.ViewModel.TermViewModel;
import emurphy.c196.databinding.ActivityTermEditorBinding;

public class TermEditorActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ActivityTermEditorBinding binding;
    private Menu mOptionsMenu;

    public static final String EXTRA_TERM_ID = "EXTRA_TERM_ID";
    private TermViewModel termViewModel;
    private TermEntity termEntity;
    private List<CourseEntity> courseEntityList;

    private EditText termTitleEditText;
    private EditText termStartDateEditText;
    private EditText termEndDateEditText;
    Button startDateButton;
    Button endDateButton;
    private int datePickerSelected = 0;

    private boolean newTerm = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTermEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        termTitleEditText = findViewById(R.id.term_title_text);
        termStartDateEditText = findViewById(R.id.start_date_text);
        termEndDateEditText = findViewById(R.id.end_date_text);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_TERM_ID)) {
            long termId = intent.getLongExtra(EXTRA_TERM_ID, 0);
            termEntity = termViewModel.getTermById(termId);
            newTerm = false;

            termTitleEditText.setText(termEntity.getTitle());
            termStartDateEditText.setText(termEntity.getStart_date());
            termEndDateEditText.setText(termEntity.getEnd_date());
        } else {
            termEntity = new TermEntity();
            courseEntityList = new ArrayList<>();
            binding.fab.setVisibility(View.GONE);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        CourseAdapter adapter = new CourseAdapter();
        binding.termContent.courseListRecyclerView.setLayoutManager(layoutManager);
        binding.termContent.courseListRecyclerView.setAdapter(adapter);
        termViewModel.getCoursesForTerm(termEntity.getTerm_id()).observe(this, courseEntities -> adapter.setCoursesExtended(courseEntities, termEntity));

        adapter.setOnItemClickListener(course -> {
            Intent courseIntent = new Intent(TermEditorActivity.this, CourseEditorActivity.class);
            courseIntent.putExtra(CourseEditorActivity.EXTRA_COURSE_ID, course.getCourse_id());
            courseIntent.putExtra(CourseEditorActivity.EXTRA_TERM_ID, course.getTerm_id());
            startActivity(courseIntent);
        });

        binding.fab.setOnClickListener(view -> {
            Intent intent1 = new Intent(TermEditorActivity.this, CourseEditorActivity.class);
            intent1.putExtra(CourseEditorActivity.EXTRA_TERM_ID, termEntity.getTerm_id());
            startActivity(intent1);
        });

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        startDateButton = findViewById(R.id.start_date_button);
        startDateButton.setOnClickListener(v -> {
            datePickerSelected = 0;
            datePickerDialog.show();
        });
        endDateButton = findViewById(R.id.end_date_button);
        endDateButton.setOnClickListener(v -> {
            datePickerSelected = 1;
            datePickerDialog.show();
        });
    }

    private void saveTerm() {
        termEntity.setTitle(termTitleEditText.getText().toString());
        termEntity.setStart_date(termStartDateEditText.getText().toString());
        termEntity.setEnd_date(termEndDateEditText.getText().toString());
        if (termEntity.getTerm_id() > 0) {
            termViewModel.updateTerm(termEntity);
        } else {
            termEntity.setTerm_id(termViewModel.insertTerm(termEntity));
            mOptionsMenu.findItem(R.id.delete_button).setVisible(true);
            binding.fab.setVisibility(View.VISIBLE);
        }
    }

    private boolean deleteTerm() {
        Pair<TermEntity, List<CourseEntity>> termEntityListPair = termViewModel.getTermWithCourses(termEntity.getTerm_id());
        TermEntity checkEntity = termEntityListPair.first;
        List<CourseEntity> checkCourses = termEntityListPair.second;
        if (checkCourses.isEmpty()) {
            termViewModel.deleteTerm(checkEntity);
            mOptionsMenu.findItem(R.id.delete_button).setVisible(false);
            mOptionsMenu.findItem(R.id.save_button).setVisible(false);
            binding.fab.setVisibility(View.GONE);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_menu, menu);
        mOptionsMenu = menu;
        if (newTerm) {
            menu.findItem(R.id.delete_button).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_button: {
                saveTerm();
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Saved", BaseTransientBottomBar.LENGTH_SHORT).addCallback(
                        new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
//                                    finish();
                                }
                            }
                        });
                snackbar.show();
                return true;
            }
            case R.id.delete_button: {
                boolean deleted = deleteTerm();
                if (deleted) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Deleted", BaseTransientBottomBar.LENGTH_SHORT).addCallback(
                            new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                        finish();
                                    }
                                }
                            });
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Cannot delete term with courses assigned", BaseTransientBottomBar.LENGTH_LONG);
                    snackbar.show();
                }
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dateString = year + "-" + (month + 1) + "-" + dayOfMonth;
        switch (datePickerSelected) {
            case 0:
                termStartDateEditText.setText(dateString);
                break;
            case 1:
                termEndDateEditText.setText(dateString);
                break;
        }
    }
}
