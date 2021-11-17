package emurphy.c196;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import emurphy.c196.Database.CourseEntity;
import emurphy.c196.Database.NoteEntity;
import emurphy.c196.ViewModel.NoteViewModel;
import emurphy.c196.databinding.ActivityNoteEditorBinding;

public class NoteEditorActivity extends AppCompatActivity {

    private ActivityNoteEditorBinding binding;

    public static final String EXTRA_NOTE_ID = "EXTRA_NOTE_ID";
    public static final String EXTRA_COURSE_ID = "EXTRA_COURSE_ID";

    private NoteViewModel noteViewModel;
    private NoteEntity noteEntity;

    private EditText noteTitleEditText;
    private EditText noteTextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteEntity = new NoteEntity();

        binding = ActivityNoteEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        noteTitleEditText = findViewById(R.id.note_title);
        noteTextEditText = findViewById(R.id.note_text);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NOTE_ID)) {
            long noteId = intent.getLongExtra(EXTRA_NOTE_ID, 0);
            noteEntity = noteViewModel.getNoteById(noteId);

            noteTitleEditText.setText(noteEntity.getTitle());
            noteTextEditText.setText(noteEntity.getText());
        } else {
            noteEntity.setCourse_id(intent.getLongExtra(EXTRA_COURSE_ID, 0));
        }
    }

    private void saveNote() {
        noteEntity.setTitle(noteTitleEditText.getText().toString());
        noteEntity.setText(noteTextEditText.getText().toString());
        if (noteEntity.getNote_id() > 0) {
            noteViewModel.updateNote(noteEntity);
        } else {
            noteEntity.setNote_id(noteViewModel.insertNote(noteEntity));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_menu, menu);
        menu.findItem(R.id.delete_button).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_button: {
                saveNote();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
