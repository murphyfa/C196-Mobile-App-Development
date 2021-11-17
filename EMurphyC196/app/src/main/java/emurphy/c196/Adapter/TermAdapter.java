package emurphy.c196.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import emurphy.c196.Database.TermEntity;
import emurphy.c196.R;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<TermEntity> terms = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.terms_list_item, parent, false);
        return new TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        TermEntity currentTerm = terms.get(position);
        holder.txtTermTitle.setText(currentTerm.getTitle());
        holder.txtTermDates.setText(currentTerm.getStart_date() + " - " + currentTerm.getEnd_date());
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setTerms(List<TermEntity> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    public class TermViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTermTitle, txtTermDates;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTermTitle = itemView.findViewById(R.id.term_title);
            txtTermDates = itemView.findViewById(R.id.term_dates);

            itemView.setOnClickListener((x) -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(terms.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TermEntity term);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
