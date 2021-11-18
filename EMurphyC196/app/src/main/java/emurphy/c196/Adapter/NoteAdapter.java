package emurphy.c196.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import emurphy.c196.Database.CourseEntity;
import emurphy.c196.Database.NoteEntity;
import emurphy.c196.NoteEditorActivity;
import emurphy.c196.R;
import emurphy.c196.ViewModel.NoteViewModel;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<NoteEntity> notes = new ArrayList<>();
    private CourseEntity course;
    private OnItemClickListener listener;
    private NoteViewModel viewModel;

    public NoteAdapter(NoteViewModel viewModel, CourseEntity course) {
        this.viewModel = viewModel;
        this.course = course;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteEntity currentNote = notes.get(position);
        holder.txtNoteTitle.setText(currentNote.getTitle())
        ;
        holder.txtNoteText.setText(currentNote.getText());
        holder.txtNoteText.setOnClickListener(v -> {
            Intent editIntent = new Intent(v.getContext(), NoteEditorActivity.class);
            editIntent.putExtra(NoteEditorActivity.EXTRA_COURSE_ID, course.getCourse_id());
            editIntent.putExtra(NoteEditorActivity.EXTRA_NOTE_ID, currentNote.getNote_id());
            v.getContext().startActivity(editIntent);
        });

        holder.btnShare.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, currentNote.getTitle() + " : " + currentNote.getText());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            v.getContext().startActivity(shareIntent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            viewModel.deleteNote(currentNote);
            this.notes = viewModel.getAllNotesForCourse(course.getCourse_id());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void setExtended(List<NoteEntity> notes, CourseEntity course) {
        this.notes = notes;
        this.course = course;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNoteTitle, txtNoteText, btnShare, btnDelete;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNoteTitle = itemView.findViewById(R.id.note_title);
            txtNoteText = itemView.findViewById(R.id.note_text);
            btnShare = itemView.findViewById(R.id.share_button);
            btnDelete = itemView.findViewById(R.id.delete_button);

            itemView.setOnClickListener((x) -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(notes.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(NoteEntity Note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
