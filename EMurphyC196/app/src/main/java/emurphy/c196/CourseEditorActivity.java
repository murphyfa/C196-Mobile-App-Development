package emurphy.c196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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

import emurphy.c196.Adapter.AssessmentAdapter;
import emurphy.c196.Database.AssessmentEntity;
import emurphy.c196.Database.CourseEntity;
import emurphy.c196.Database.NoteEntity;
import emurphy.c196.Database.TermEntity;
import emurphy.c196.Helper.DateHelper;
import emurphy.c196.ViewModel.CourseViewModel;
import emurphy.c196.databinding.ActivityCourseEditorBinding;
import kotlin.Triple;

public class CourseEditorActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ActivityCourseEditorBinding binding;
    private Menu mOptionsMenu;

    public static final String EXTRA_COURSE_ID = "EXTRA_COURSE_ID";
    public static final String EXTRA_TERM_ID = "EXTRA_TERM_ID";
    private CourseViewModel courseViewModel;
    private CourseEntity courseEntity;

    private EditText courseTitleEditText;
    private EditText courseStartDateEditText;
    private EditText courseEndDateEditText;
    private EditText courseInstructorNameEditText;
    private EditText courseInstructorPhoneEditText;
    private EditText courseInstructorEmailEditText;
    Button startDateButton;
    Button endDateButton;
    private CheckBox alertStartDate;
    private CheckBox alertEndDate;
    private int datePickerSelected = 0;

    private boolean newCourse = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        courseEntity = new CourseEntity();

        binding = ActivityCourseEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        courseTitleEditText = findViewById(R.id.course_title_text);
        courseStartDateEditText = findViewById(R.id.start_date_text);
        courseEndDateEditText = findViewById(R.id.end_date_text);
        courseInstructorNameEditText = findViewById(R.id.course_instructor_name_text);
        courseInstructorPhoneEditText = findViewById(R.id.course_instructor_phone_text);
        courseInstructorEmailEditText = findViewById(R.id.course_instructor_email_text);
        alertStartDate = findViewById(R.id.start_date_cb);
        alertEndDate = findViewById(R.id.end_date_cb);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_COURSE_ID)) {
            long courseId = intent.getLongExtra(EXTRA_COURSE_ID, 0);
            courseEntity = courseViewModel.getCourseById(courseId);
            newCourse = false;

            courseTitleEditText.setText(courseEntity.getTitle());
            courseStartDateEditText.setText(courseEntity.getStart_date());
            courseEndDateEditText.setText(courseEntity.getEnd_date());
            courseInstructorNameEditText.setText(courseEntity.getInstructor_name());
            courseInstructorPhoneEditText.setText(courseEntity.getInstructor_phone());
            courseInstructorEmailEditText.setText(courseEntity.getInstructor_email());
            alertStartDate.setChecked(courseEntity.getStart_notification_id() > 0);
            alertEndDate.setChecked(courseEntity.getEnd_notification_id() > 0);
        } else if (intent.hasExtra(EXTRA_TERM_ID)) {
            binding.fab.setVisibility(View.GONE);
            courseEntity.setTerm_id(intent.getLongExtra(EXTRA_TERM_ID, 0));
        } else {
            binding.fab.setVisibility(View.GONE);
            List<TermEntity> termList = courseViewModel.getAllTerms();
            ArrayList<String> termNames = new ArrayList<>();
            for (TermEntity t : termList) {
                termNames.add(t.getTitle());
            }
            Spinner termSpinner = findViewById(R.id.term_spinner);
            termSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter termAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, termNames);
            termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            termSpinner.setAdapter(termAdapter);
            termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    courseEntity.setTerm_id(termList.get(position).getTerm_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        AssessmentAdapter adapter = new AssessmentAdapter();
        binding.courseContent.assessmentListRecyclerView.setLayoutManager(layoutManager);
        binding.courseContent.assessmentListRecyclerView.setAdapter(adapter);
        List<AssessmentEntity> test = courseViewModel.getAllAssessmentsForCourse(courseEntity.getCourse_id());
        Log.wtf("test", "number of assessments found: " + test.size());
        courseViewModel.getAllAssessmentsForCourseLive(courseEntity.getCourse_id()).observe(this, assessmentEntities -> adapter.setExtended(assessmentEntities, courseEntity));

        adapter.setOnItemClickListener(assessment -> {
            Intent assessmentIntent = new Intent(CourseEditorActivity.this, AssessmentEditorActivity.class);
            assessmentIntent.putExtra(AssessmentEditorActivity.EXTRA_ASSESSMENT_ID, assessment.getAssessment_id());
            assessmentIntent.putExtra(AssessmentEditorActivity.EXTRA_COURSE_ID, courseEntity.getCourse_id());
            startActivity(assessmentIntent);
        });

        binding.fab.setOnClickListener(view -> {
            Intent intent1 = new Intent(CourseEditorActivity.this, AssessmentEditorActivity.class);
            intent1.putExtra(AssessmentEditorActivity.EXTRA_COURSE_ID, courseEntity.getCourse_id());
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

    private void saveCourse() {
        courseEntity.setTitle(courseTitleEditText.getText().toString());
        courseEntity.setStart_date(courseStartDateEditText.getText().toString());
        courseEntity.setEnd_date(courseEndDateEditText.getText().toString());
        courseEntity.setStatus(courseEndDateEditText.getText().toString());
        courseEntity.setInstructor_name(courseInstructorNameEditText.getText().toString());
        courseEntity.setInstructor_phone(courseInstructorPhoneEditText.getText().toString());
        courseEntity.setInstructor_email(courseInstructorEmailEditText.getText().toString());

        if (courseEntity.getCourse_id() > 0) {
            courseViewModel.updateCourse(courseEntity);
        } else {
            courseEntity.setCourse_id(courseViewModel.insertCourse(courseEntity));
            mOptionsMenu.findItem(R.id.delete_button).setVisible(true);
            mOptionsMenu.findItem(R.id.notes_button).setVisible(true);
            binding.fab.setVisibility(View.VISIBLE);
        }

        if (alertStartDate.isChecked() && courseStartDateEditText.getText().toString().trim().length() > 0) {
            if (courseEntity.getStart_notification_id() > 0) {
                createAlert("course_start", courseEntity.getStart_notification_id());
            } else {
                courseEntity.setStart_notification_id(createAlert("course_start", 0));
            }
        } else if (!alertStartDate.isChecked() && courseEntity.getStart_notification_id() > 0) {
            cancelAlert("course_start", courseEntity.getStart_notification_id());
            courseEntity.setStart_notification_id(0);
        }

        if (alertEndDate.isChecked() && courseEndDateEditText.getText().toString().trim().length() > 0) {
            if (courseEntity.getEnd_notification_id() > 0) {
                createAlert("course_end", courseEntity.getEnd_notification_id());
            } else {
                courseEntity.setEnd_notification_id(createAlert("course_end", 0));
            }
        } else if (!alertEndDate.isChecked() && courseEntity.getEnd_notification_id() > 0) {
            cancelAlert("course_end", courseEntity.getEnd_notification_id());
            courseEntity.setEnd_notification_id(0);
        }
    }

    private boolean deleteCourse() {
        cancelAlert("course_start", courseEntity.getStart_notification_id());
        cancelAlert("course_end", courseEntity.getEnd_notification_id());
        Triple<CourseEntity, List<AssessmentEntity>, List<NoteEntity>> courseWithAssessmentsAndNotes = courseViewModel.getCourseWithAssessmentsAndNotes(courseEntity.getCourse_id());
        CourseEntity checkEntity = courseWithAssessmentsAndNotes.component1();
        List<AssessmentEntity> checkAssessments = courseWithAssessmentsAndNotes.component2();
        List<NoteEntity> checkNotes = courseWithAssessmentsAndNotes.component3();
        if (checkAssessments.isEmpty() && checkNotes.isEmpty()) {
            courseViewModel.deleteCourse(checkEntity);
            mOptionsMenu.findItem(R.id.delete_button).setVisible(false);
            mOptionsMenu.findItem(R.id.notes_button).setVisible(false);
            mOptionsMenu.findItem(R.id.save_button).setVisible(false);
            binding.fab.setVisibility(View.GONE);
            return true;
        } else {
            return false;
        }
    }

    private int createAlert(String type, int existing_id) {
        String text = "";
        long time = 0;
        switch (type) {
            case "course_start": {
                text = "Your course " + courseEntity.getTitle() + " is starting";
                time = DateHelper.toMilliseconds(courseStartDateEditText.getText().toString());
                break;
            }
            case "course_end": {
                text = "Your course " + courseEntity.getTitle() + " is ending";
                time = DateHelper.toMilliseconds(courseEndDateEditText.getText().toString());
                break;
            }
        }
        return Notifier.createAlert(this.getApplicationContext(),
                time,
                "course",
                courseEntity.getCourse_id(),
                "Course Reminder!",
                text,
                existing_id
        );
    }

    private void cancelAlert(String type, int existing_id) {
        String text = "";
        long time = 0;
        switch (type) {
            case "course_start": {
                text = "Your course " + courseEntity.getTitle() + " is starting";
                time = DateHelper.toMilliseconds(courseStartDateEditText.getText().toString());
                break;
            }
            case "course_end": {
                text = "Your course " + courseEntity.getTitle() + " is ending";
                time = DateHelper.toMilliseconds(courseEndDateEditText.getText().toString());
                break;
            }
        }
        Notifier.cancelAlert(this.getApplicationContext(),
                time,
                "course",
                courseEntity.getCourse_id(),
                "Course Reminder!",
                text,
                existing_id
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.course_editor_menu, menu);
        mOptionsMenu = menu;
        if (newCourse) {
            menu.findItem(R.id.delete_button).setVisible(false);
            menu.findItem(R.id.notes_button).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_button: {
                saveCourse();
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
                boolean deleted = deleteCourse();
                Snackbar snackbar;
                if (deleted) {
                    snackbar = Snackbar.make(binding.getRoot(), "Deleted", BaseTransientBottomBar.LENGTH_SHORT).addCallback(
                            new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                        finish();
                                    }
                                }
                            });
                } else {
                    snackbar = Snackbar.make(binding.getRoot(), "Cannot delete course with notes or assessments associated.", BaseTransientBottomBar.LENGTH_LONG);
                }
                snackbar.show();
                return true;
            }
            case R.id.notes_button: {
                Intent intent = new Intent(CourseEditorActivity.this, NoteListActivity.class);
                intent.putExtra(NoteListActivity.EXTRA_COURSE_ID, courseEntity.getCourse_id());
                startActivity(intent);
                return true;
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
                courseStartDateEditText.setText(dateString);
                break;
            case 1:
                courseEndDateEditText.setText(dateString);
                break;
        }
    }
}
