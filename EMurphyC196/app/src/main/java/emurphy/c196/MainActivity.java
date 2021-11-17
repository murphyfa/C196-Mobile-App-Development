package emurphy.c196;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import emurphy.c196.databinding.ActivityMainBinding;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Notifier.createNotificationChannel(this.getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    public void gotoTermsList(View view){
        Intent intent = new Intent(MainActivity.this, TermListActivity.class);
        startActivity(intent);
    }

    public void gotoCoursesList(View view){
        Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
        startActivity(intent);
    }

    public void gotoAssessmentsList(View view){
        Intent intent = new Intent(MainActivity.this, AssessmentListActivity.class);
        startActivity(intent);
    }
}