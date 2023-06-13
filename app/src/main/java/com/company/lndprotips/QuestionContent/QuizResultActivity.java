package com.company.lndprotips.QuestionContent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.company.lndprotips.DbHelper.DbHelper;
import com.company.lndprotips.MainActivity;
import com.company.lndprotips.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class QuizResultActivity extends AppCompatActivity {

    Toolbar toolbar;
    AppCompatButton btnGoHome;
    TextView progressPercentage, tvCorrectAnswer, tvWrongAnswer;

    DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        progressPercentage = findViewById(R.id.progressPercentage);
        tvCorrectAnswer = findViewById(R.id.tvCorrectAnswer);
        tvWrongAnswer = findViewById(R.id.tvWrongAnswer);
        btnGoHome = findViewById(R.id.btnGoHome);
        dbHelper = DbHelper.getInstance(QuizResultActivity.this);

        // --------> set the custom toolbar
        toolbar = findViewById(R.id.quizResultToolBar);
        setSupportActionBar(toolbar);

        // --------> get data from intent
        int correctAnswer = getIntent().getIntExtra("correct", 0);
        int inCorrectAnswer = getIntent().getIntExtra("incorrect", 0);
        int totalQuizQuestion = getIntent().getIntExtra("totalQuestion", 10);
        String quizTitle = getIntent().getStringExtra("quizTitle");

        // --------> set data after getting from intent
        float correct = correctAnswer;
        float percentage = (correct / totalQuizQuestion) * 100f;
        String percentageWithFormat = String.format("%.1f", percentage);

        progressPercentage.setText(percentageWithFormat + "%");
        tvCorrectAnswer.setText(String.valueOf(correctAnswer));
        tvWrongAnswer.setText(String.valueOf(inCorrectAnswer));

        // set click listener on home button
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizResultActivity.this, MainActivity.class));
                finish();

                // insert data into table
                dbHelper.insertRecentQuiz(quizTitle, String.valueOf(correctAnswer), String.valueOf(inCorrectAnswer), String.valueOf(totalQuizQuestion), String.valueOf(percentageWithFormat));
            }
        });

        // --------> set the progress indicator
        CircularProgressBar circularProgressBar = findViewById(R.id.circularProgressBar);
        // Set Progress
        circularProgressBar.setProgress(percentage);
        // or with animation
        circularProgressBar.setProgressWithAnimation(percentage, 1000L); // =1s

        // Set Progress Max
        circularProgressBar.setProgressMax(100f);

        // Set ProgressBar Color
        circularProgressBar.setProgressBarColor(Color.BLACK);
        // or with gradient
        circularProgressBar.setProgressBarColorStart(getColor(R.color.secondary_color));
        circularProgressBar.setProgressBarColorEnd(getColor(R.color.primary_color));
        circularProgressBar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);

        // Set background ProgressBar Color
        circularProgressBar.setBackgroundProgressBarColor(getColor(R.color.secondary_color));
        // or with gradient
        circularProgressBar.setBackgroundProgressBarColorStart(getColor(R.color.secondary_color));
        circularProgressBar.setBackgroundProgressBarColorEnd(getColor(R.color.primary_color));
        circularProgressBar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);

        // Set Width
        circularProgressBar.setProgressBarWidth(7f); // in DP
        circularProgressBar.setBackgroundProgressBarWidth(3f); // in DP

        // Other
        circularProgressBar.setRoundBorder(true);
        circularProgressBar.setStartAngle(180f);
        circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);

    }

}