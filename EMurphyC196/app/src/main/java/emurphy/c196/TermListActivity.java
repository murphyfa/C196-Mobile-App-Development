package emurphy.c196;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import emurphy.c196.Adapter.TermAdapter;
import emurphy.c196.Database.TermEntity;
import emurphy.c196.ViewModel.TermViewModel;
import emurphy.c196.databinding.ActivityTermsListBinding;

public class TermListActivity extends AppCompatActivity {

    private ActivityTermsListBinding binding;

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTermsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        TermAdapter adapter = new TermAdapter();
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getAllTermsLive().observe(this, termEntities -> adapter.setTerms(termEntities));

        adapter.setOnItemClickListener(term -> {
            Intent intent = new Intent(TermListActivity.this, TermEditorActivity.class);
            intent.putExtra(TermEditorActivity.EXTRA_TERM_ID, term.getTerm_id());
            startActivity(intent);
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermListActivity.this, TermEditorActivity.class);
                startActivity(intent);
            }
        });
    }
}
