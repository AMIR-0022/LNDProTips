package com.company.lndprotips.QuestionContent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.company.lndprotips.QuestionBank.QuestionBank;
import com.company.lndprotips.QuestionModel.Question;
import com.company.lndprotips.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class QuizShowActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvQuestion, tvQuizTimer, tvQuestionMark;
    AppCompatButton btnOpt1, btnOpt2, btnOpt3, btnNextQuestion;
    Timer quizTimer;
    private CountDownTimer countDownTimer;
    private static final long TIMER_DURATION = 15000; // 15 seconds
    List<Question> questionLists = new ArrayList<>();
    int currentQuestionPosition = 0;
    String selectedOptionByUser = "";

    // for get data from intent
    int questionListSize;

    // send data to intent
    String quizTitle = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_show);

        toolbar = findViewById(R.id.quizShowToolBar);
        tvQuestionMark = findViewById(R.id.tvQuestionMark);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvQuizTimer = findViewById(R.id.tvTimer);
        btnOpt1 = findViewById(R.id.btnAnswer1);
        btnOpt2 = findViewById(R.id.btnAnswer2);
        btnOpt3 = findViewById(R.id.btnAnswer3);
        btnNextQuestion = findViewById(R.id.btnNext);

        // set the back option of the toolBar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // get data from intent
        String quizCategory = getIntent().getStringExtra("quizCategory");
        int quizPracticeSet = getIntent().getIntExtra("quizPracticeSet", 1);
        questionListSize = getIntent().getIntExtra("questionListSize", 10);

        // get list from question bank
        questionLists = QuestionBank.getQuestions(quizCategory+quizPracticeSet);
        Collections.shuffle(questionLists);

        // start the quiz time for 30 seconds
        startQuizTimer(tvQuizTimer);

        // set text data
        tvQuestionMark.setText((currentQuestionPosition+1) + "/" + questionListSize);
        tvQuestion.setText(questionLists.get(0).getQuestion());
        btnOpt1.setText(questionLists.get(0).getOption1());
        btnOpt2.setText(questionLists.get(0).getOption2());
        btnOpt3.setText(questionLists.get(0).getOption3());

        // set title of the tool bar
        if (quizCategory.equals("math") && quizPracticeSet==1){
            quizTitle = "Addition";
            getSupportActionBar().setTitle("Addition");
        }
        else if (quizCategory.equals("math") && quizPracticeSet==2){
            quizTitle = "Subtraction";
            getSupportActionBar().setTitle("Subtraction");
        }
        else if (quizCategory.equals("math") && quizPracticeSet==3){
            quizTitle = "Multiplication";
            getSupportActionBar().setTitle("Multiplication");
        }
        else if (quizCategory.equals("math") && quizPracticeSet==4){
            quizTitle = "Division";
            getSupportActionBar().setTitle("Division");
        }
        else if (quizCategory.equals("english") && quizPracticeSet==1){
            quizTitle = "Use of Is AM Are";
            getSupportActionBar().setTitle("Use of Is AM Are");
        }
        else if (quizCategory.equals("english") && quizPracticeSet==2){
            quizTitle = "Use of Punctuations";
            getSupportActionBar().setTitle("Use of Punctuations");
        }
        else if (quizCategory.equals("english") && quizPracticeSet==3){
            quizTitle = "Use of Pronouns";
            getSupportActionBar().setTitle("Use of Pronouns");
        }
        else if (quizCategory.equals("english")  && quizPracticeSet==4){
            quizTitle = "Use of Preposition";
            getSupportActionBar().setTitle("Use of Preposition");
        }
        else if (quizCategory.equals("urdu") && quizPracticeSet==1){
            quizTitle = "";
            getSupportActionBar().setTitle("demo1");
        }
        else if (quizCategory.equals("urdu") && quizPracticeSet==2){
            quizTitle = "";
            getSupportActionBar().setTitle("demo2");
        }
        else if (quizCategory.equals("urdu") && quizPracticeSet==3){
            quizTitle = "";
            getSupportActionBar().setTitle("demo3");
        }
        else if (quizCategory.equals("urdu") && quizPracticeSet==4){
            quizTitle = "";
            getSupportActionBar().setTitle("demo4");
        }

        // click listener on the option buttons
        btnOpt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionByUser.isEmpty()) {
                    // change the button text from next to submit
                    if ((currentQuestionPosition + 1) == questionListSize) {
                        btnNextQuestion.setText("Submit Quiz");
                    }
                    // get text from selected option
                    selectedOptionByUser = btnOpt1.getText().toString();
                    // set the background of selected option
                    btnOpt1.setBackgroundResource(R.drawable.shape_btn_wrong_ans);
                    // reveal the answer
                    revealAnswer();
                    // set the user selected answer in the question list
                    questionLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });
        btnOpt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionByUser.isEmpty()) {
                    // change the button text from next to submit
                    if ((currentQuestionPosition + 1) == questionListSize) {
                        btnNextQuestion.setText("Submit Quiz");
                    }
                    // get text from selected option
                    selectedOptionByUser = btnOpt2.getText().toString();
                    // set the background of selected option
                    btnOpt2.setBackgroundResource(R.drawable.shape_btn_wrong_ans);
                    // reveal the answer
                    revealAnswer();
                    // set the user selected answer in the question list
                    questionLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });
        btnOpt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionByUser.isEmpty()) {
                    // change the button text from next to submit
                    if ((currentQuestionPosition + 1) == questionListSize) {
                        btnNextQuestion.setText("Submit Quiz");
                    }
                    // get text from selected option
                    selectedOptionByUser = btnOpt3.getText().toString();
                    // set the background of selected option
                    btnOpt3.setBackgroundResource(R.drawable.shape_btn_wrong_ans);
                    // reveal the answer
                    revealAnswer();
                    // set the user selected answer in the question list
                    questionLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });
        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionByUser.isEmpty()) {
                    Toast.makeText(QuizShowActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    cancelTimer();
                    startQuizTimer(tvQuizTimer);
                    changeNextQuestion();
                }
            }
        });
    }

    // set click listener on the option menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // set the quiz timer
    private void startQuizTimer(TextView timerTextView) {
        // to cancel the previous timer
        cancelTimer();

        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer TextView with the remaining time
                long secondsRemaining = millisUntilFinished / 1000;

//                timeTakenInSeconds = (TIMER_DURATION - millisUntilFinished) / 1000;

                String finalTimeToShow = String.valueOf(secondsRemaining);
                if (finalTimeToShow.length() == 1) {
                    timerTextView.setText("00:0" + finalTimeToShow);
                } else {
                    timerTextView.setText("00:" + finalTimeToShow);
                }
            }

            @Override
            public void onFinish() {
                // Show the time up dialog when the timer is finished
                showTimeUpDialog();
            }
        }.start();
    }

    // to cancel the previous timer
    private void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    // set dialog on time up of quiz
    private void showTimeUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Time Up");
        builder.setMessage("Sorry, your time is up!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle dialog button click if needed
                Intent intent = new Intent(QuizShowActivity.this, QuizResultActivity.class);
                intent.putExtra("correct", getCorrectAnswer());
                intent.putExtra("incorrect", getInCorrectAnswer());
                intent.putExtra("totalQuestion", questionListSize);
                intent.putExtra("quizTitle", quizTitle);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the timer when the activity is destroyed to prevent memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public int getCorrectAnswer() {
        int correctAnswer = 0;
        for (int i = 0; i < questionListSize; i++) {
            String getUserSelectedAnswer = questionLists.get(i).getUserSelectedAnswer();
            String getAnswer = questionLists.get(i).getCorrectAnswer();
            if (getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswer++;
            }
        }
        return correctAnswer;
    }

    public int getInCorrectAnswer() {
        int inCorrect = 0;
        for (int i = 0; i < questionListSize; i++) {
            String getUserSelectedAnswer = questionLists.get(i).getUserSelectedAnswer();
            String getAnswer = questionLists.get(i).getCorrectAnswer();
            if (!getUserSelectedAnswer.equals(getAnswer)) {
                inCorrect++;
            }
        }
        return inCorrect;
    }

    void revealAnswer() {
        String answer = questionLists.get(currentQuestionPosition).getCorrectAnswer();
        if (btnOpt1.getText().toString().equals(answer)) {
            btnOpt1.setBackgroundResource(R.drawable.shape_btn_correct_ans);
            btnOpt1.setTextColor(Color.WHITE);
        } else if (btnOpt2.getText().toString().equals(answer)) {
            btnOpt2.setBackgroundResource(R.drawable.shape_btn_correct_ans);
            btnOpt2.setTextColor(Color.WHITE);
        } else if (btnOpt3.getText().toString().equals(answer)) {
            btnOpt3.setBackgroundResource(R.drawable.shape_btn_correct_ans);
            btnOpt3.setTextColor(Color.WHITE);
        }
    }

    void changeNextQuestion() {
        currentQuestionPosition++;
        if (currentQuestionPosition < questionListSize) {
            selectedOptionByUser = "";

            // reset the color of option buttons
            btnOpt1.setBackgroundResource(R.drawable.shape_btn_stroke0);
            btnOpt1.setTextColor(Color.BLACK);
            btnOpt2.setBackgroundResource(R.drawable.shape_btn_stroke0);
            btnOpt2.setTextColor(Color.BLACK);
            btnOpt3.setBackgroundResource(R.drawable.shape_btn_stroke0);
            btnOpt3.setTextColor(Color.BLACK);

            // reset the options of buttons
            tvQuestionMark.setText((currentQuestionPosition+1) + "/" + questionListSize);
            tvQuestion.setText(questionLists.get(currentQuestionPosition).getQuestion());
            btnOpt1.setText(questionLists.get(currentQuestionPosition).getOption1());
            btnOpt2.setText(questionLists.get(currentQuestionPosition).getOption2());
            btnOpt3.setText(questionLists.get(currentQuestionPosition).getOption3());
        } else {
            Intent intent = new Intent(QuizShowActivity.this, QuizResultActivity.class);
            intent.putExtra("correct", getCorrectAnswer());
            intent.putExtra("incorrect", getInCorrectAnswer());
            intent.putExtra("totalQuestion", questionListSize);
            intent.putExtra("quizTitle", quizTitle);
            startActivity(intent);
            finish();
        }
    }

}