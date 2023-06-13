package com.company.lndprotips.QuestionContent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.lndprotips.R;

public class PracticeSetActivity extends AppCompatActivity {

    Toolbar toolbar;
    int questionListSize;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;
    CardView cardPracticeSet1, cardPracticeSet2, cardPracticeSet3, cardPracticeSet4;
    TextView practiceSetText1, practiceSetText2, practiceSetText3, practiceSetText4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_set);

        toolbar = findViewById(R.id.practiceSetToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        cardPracticeSet1 = findViewById(R.id.cardPracticeSet1);
        cardPracticeSet2 = findViewById(R.id.cardPracticeSet2);
        cardPracticeSet3 = findViewById(R.id.cardPracticeSet3);
        cardPracticeSet4 = findViewById(R.id.cardPracticeSet4);
        practiceSetText1 = findViewById(R.id.practiceSetText1);
        practiceSetText2 = findViewById(R.id.practiceSetText2);
        practiceSetText3 = findViewById(R.id.practiceSetText3);
        practiceSetText4 = findViewById(R.id.practiceSetText4);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radio10);
        radioButton2 = findViewById(R.id.radio20);
        radioButton3 = findViewById(R.id.radio50);

        // get data from intent
        String quizCategory = getIntent().getStringExtra("quizType");

        // set text of all the cards
        if (quizCategory.equals("math")) {
            practiceSetText1.setText("Addition");
            practiceSetText2.setText("Subtraction");
            practiceSetText3.setText("Multiplication");
            practiceSetText4.setText("Division");
        } else if (quizCategory.equals("english")) {
            practiceSetText1.setText("Use of Is/Am/Are");
            practiceSetText2.setText("Use of Punctuations");
            practiceSetText3.setText("Use of Pronouns");
            practiceSetText4.setText("Use of Prepositions");
        } else if (quizCategory.equals("urdu")) {
            practiceSetText1.setText("");
            practiceSetText2.setText("");
            practiceSetText3.setText("");
            practiceSetText4.setText("");
        }


        // set radio buttons
        if (radioButton1.isChecked()) {
            questionListSize = 10;
        } else if (radioButton2.isChecked()) {
            questionListSize = 20;
        } else if (radioButton3.isChecked()) {
            questionListSize = 50;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButton1.isChecked()) {
                    questionListSize = 10;
                    radioButton1.setBackgroundResource(R.drawable.shape_radio_bg1);
                    radioButton2.setBackgroundResource(R.drawable.shape_radio_bg0);
                    radioButton3.setBackgroundResource(R.drawable.shape_radio_bg0);
                } else if (radioButton2.isChecked()) {
                    questionListSize = 20;
                    radioButton1.setBackgroundResource(R.drawable.shape_radio_bg0);
                    radioButton2.setBackgroundResource(R.drawable.shape_radio_bg1);
                    radioButton3.setBackgroundResource(R.drawable.shape_radio_bg0);
                } else if (radioButton3.isChecked()) {
                    questionListSize = 50;
                    radioButton1.setBackgroundResource(R.drawable.shape_radio_bg0);
                    radioButton2.setBackgroundResource(R.drawable.shape_radio_bg0);
                    radioButton3.setBackgroundResource(R.drawable.shape_radio_bg1);
                }
            }
        });

        // set click listener of all the card view of quiz
        cardPracticeSet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuizShowActivity(quizCategory, 1, questionListSize);
            }
        });
        cardPracticeSet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuizShowActivity(quizCategory, 2, questionListSize);
            }
        });
        cardPracticeSet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuizShowActivity(quizCategory, 3, questionListSize);
            }
        });
        cardPracticeSet4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuizShowActivity(quizCategory, 4, questionListSize);
            }
        });

    }

    // next quiz activity
    void nextQuizShowActivity(String quizCategory, int quizPracticeSet, int questionListSize) {
        Intent intent = new Intent(PracticeSetActivity.this, QuizShowActivity.class);
        intent.putExtra("quizCategory", quizCategory);
        intent.putExtra("quizPracticeSet", quizPracticeSet);
        intent.putExtra("questionListSize", questionListSize);
        startActivity(intent);
    }

    // set lick listener on the option menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}