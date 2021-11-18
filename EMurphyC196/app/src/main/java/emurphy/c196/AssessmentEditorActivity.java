package emurphy.c196;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import emurphy.c196.Database.AssessmentEntity;
import emurphy.c196.Database.CourseEntity;
import emurphy.c196.Helper.DateHelper;
import emurphy.c196.ViewModel.AssessmentViewModel;
import emurphy.c196.databinding.ActivityAssessmentEditorBinding;

public class AssessmentEditorActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    private ActivityAssessmentEditorBinding binding;
    private Menu mOptionsMenu;

    public static final String EXTRA_ASSESSMENT_ID = "EXTRA_ASSESSMENT_ID";
    public static final String EXTRA_COURSE_ID = "EXTRA_COURSE_ID";
    private AssessmentViewModel assessmentViewModel;
    private AssessmentEntity assessmentEntity;

    private EditText assessmentTitleEditText;
    private EditText assessmentStartDateEditText;
    private EditText assessmentEndDateEditText;
    Button startDateButton;
    Button endDateButton;
    private CheckBox alertStartDate;
    private CheckBox alertEndDate;
    private int datePickerSelected = 0;

    private boolean newAssessment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assessmentEntity = new AssessmentEntity();

        binding = ActivityAssessmentEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        assessmentTitleEditText = findViewById(R.id.assessment_title_text);
        assessmentStartDateEditText = findViewById(R.id.start_date_text);
        assessmentEndDateEditText = findViewById(R.id.end_date_text);
        alertStartDate = findViewById(R.id.start_date_cb);
        alertEndDate = findViewById(R.id.end_date_cb);

        Spinner spinner = findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessment_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ASSESSMENT_ID)) {
            long assessmentId = intent.getLongExtra(EXTRA_ASSESSMENT_ID, 0);
            assessmentEntity = assessmentViewModel.getAssessmentById(assessmentId);
            newAssessment = false;

            assessmentTitleEditText.setText(assessmentEntity.getTitle());
            assessmentStartDateEditText.setText(assessmentEntity.getStart_date());
            assessmentEndDateEditText.setText(assessmentEntity.getEnd_date());
            spinner.setSelection(adapter.getPosition(assessmentEntity.getType()));
            alertStartDate.setChecked(assessmentEntity.getStart_notification_id() > 0);
            alertEndDate.setChecked(assessmentEntity.getEnd_notification_id() > 0);
        } else if (intent.hasExtra(EXTRA_COURSE_ID)) {
            assessmentEntity.setCourse_id(intent.getLongExtra(EXTRA_COURSE_ID, 0));
        } else {
            List<CourseEntity> courseList = assessmentViewModel.getAllCourses();
            ArrayList<String> courseNames = new ArrayList<>();
            for (CourseEntity c : courseList) {
                courseNames.add(c.getTitle());
            }
            Spinner courseSpinner = findViewById(R.id.course_spinner);
            courseSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter courseAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courseNames);
            courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            courseSpinner.setAdapter(courseAdapter);
            courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    assessmentEntity.setCourse_id(courseList.get(position).getCourse_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

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

    private void saveAssessment() {
        assessmentEntity.setTitle(assessmentTitleEditText.getText().toString());
        assessmentEntity.setStart_date(assessmentStartDateEditText.getText().toString());
        assessmentEntity.setEnd_date(assessmentEndDateEditText.getText().toString());

        if (assessmentEntity.getAssessment_id() > 0) {
            assessmentViewModel.updateAssessment(assessmentEntity);
        } else {
            assessmentEntity.setAssessment_id(assessmentViewModel.insertAssessment(assessmentEntity));
            mOptionsMenu.findItem(R.id.delete_button).setVisible(true);
        }

        if (alertStartDate.isChecked() && assessmentStartDateEditText.getText().toString().trim().length() > 0) {
            if (assessmentEntity.getStart_notification_id() > 0) {
                createAlert("assessment_start", assessmentEntity.getStart_notification_id());
            } else {
                assessmentEntity.setStart_notification_id(createAlert("assessment_start", 0));
            }
        } else if (!alertStartDate.isChecked() && assessmentEntity.getStart_notification_id() > 0) {
            cancelAlert("assessment_start", assessmentEntity.getStart_notification_id());
            assessmentEntity.setStart_notification_id(0);
        }

        if (alertEndDate.isChecked() && assessmentEndDateEditText.getText().toString().trim().length() > 0) {
            if (assessmentEntity.getEnd_notification_id() > 0) {
                createAlert("assessment_end", assessmentEntity.getEnd_notification_id());
            } else {
                assessmentEntity.setEnd_notification_id(createAlert("assessment_end", 0));
            }
        } else if (!alertEndDate.isChecked() && assessmentEntity.getEnd_notification_id() > 0) {
            cancelAlert("assessment_end", assessmentEntity.getEnd_notification_id());
            assessmentEntity.setEnd_notification_id(0);
        }
        assessmentViewModel.updateAssessment(assessmentEntity);
    }

    private void deleteAssessment() {
        cancelAlert("assessment_start", assessmentEntity.getStart_notification_id());
        cancelAlert("assessment_end", assessmentEntity.getEnd_notification_id());
        assessmentViewModel.deleteAssessment(assessmentEntity);
        mOptionsMenu.findItem(R.id.delete_button).setVisible(false);
        mOptionsMenu.findItem(R.id.save_button).setVisible(false);
    }

    private int createAlert(String type, int existing_id) {
        String text = "";
        long time = 0;
        switch (type) {
            case "assessment_start": {
                text = "Your assessment " + assessmentEntity.getTitle() + " is starting";
                time = DateHelper.toMilliseconds(assessmentStartDateEditText.getText().toString());
                break;
            }
            case "assessment_end": {
                text = "Your assessment " + assessmentEntity.getTitle() + " is ending";
                time = DateHelper.toMilliseconds(assessmentEndDateEditText.getText().toString());
                break;
            }
        }
        return Notifier.createAlert(this.getApplicationContext(),
                time,
                "assessment",
                assessmentEntity.getAssessment_id(),
                "Assessment Reminder!",
                text,
                existing_id
        );
    }

    private void cancelAlert(String type, int existing_id) {
        String text = "";
        long time = 0;
        switch (type) {
            case "assessment_start": {
                text = "Your assessment " + assessmentEntity.getTitle() + " is starting";
                time = DateHelper.toMilliseconds(assessmentStartDateEditText.getText().toString());
                break;
            }
            case "assessment_end": {
                text = "Your assessment " + assessmentEntity.getTitle() + " is ending";
                time = DateHelper.toMilliseconds(assessmentEndDateEditText.getText().toString());
                break;
            }
        }
        Notifier.cancelAlert(this.getApplicationContext(),
                time,
                "assessment",
                assessmentEntity.getAssessment_id(),
                "Assessment Reminder!",
                text,
                existing_id
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_menu, menu);
        mOptionsMenu = menu;
        if (newAssessment) {
            menu.findItem(R.id.delete_button).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_button: {
                saveAssessment();
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
                deleteAssessment();
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
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        String dateString = year + "-" + (month + 1) + "-" + dayOfMonth;
        switch (datePickerSelected) {
            case 0:
                assessmentStartDateEditText.setText(dateString);
                timePickerDialog.show();
                break;
            case 1:
                assessmentEndDateEditText.setText(dateString);
                timePickerDialog.show();
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        switch (datePickerSelected) {
            case 0:
                assessmentStartDateEditText.setText(assessmentStartDateEditText.getText().toString() + " " + hourOfDay + ":" + minute + ":00");
                break;
            case 1:
                assessmentEndDateEditText.setText(assessmentEndDateEditText.getText().toString() + " " + hourOfDay + ":" + minute + ":00");
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        assessmentEntity.setType(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
