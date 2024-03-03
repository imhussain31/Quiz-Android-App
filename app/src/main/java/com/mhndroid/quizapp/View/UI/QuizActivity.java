package com.mhndroid.quizapp.View.UI;

import static com.google.android.material.internal.ContextUtils.getActivity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mhndroid.quizapp.R;
import com.mhndroid.quizapp.Service.Model.QuestionModel;
import com.mhndroid.quizapp.ViewModel.CategoriesViewModel;
import com.mhndroid.quizapp.ViewModel.QuestionViewModel;

import java.util.HashMap;
import java.util.List;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    // ViewModel for question data
    QuestionViewModel viewModel;


    // UI components
    private ProgressBar progressBar , Qprogress;
    private Button option1Btn, option2Btn, option3Btn, nextQueBtn;
    private TextView  ansFeedBackTv, questionNumberTv, timerCountTv;

    private JLatexMathView questionTv;


    // Quiz state variables
    private String quizId;

    private long totalQuestions;

    private int currentQueNo = 0;
    private boolean canAnswer = false;
    private long timer;
    private CountDownTimer countDownTimer;
    private int notAnswerd = 0;
    private int correctAnswer = 0;
    private int wrongAnswer = 0;
    private String answer = "";


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initializing ViewModel for handling question data.
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity(this).getApplication())).get(QuestionViewModel.class);

        // Initializing UI components.
        progressBar = findViewById(R.id.quizCoutProgressBar);
        option1Btn = findViewById(R.id.opBtnId_1);
        option2Btn = findViewById(R.id.opBtnId_2);
        option3Btn = findViewById(R.id.opBtnId_3);
        nextQueBtn = findViewById(R.id.nextQnBtnId);
        Qprogress = findViewById(R.id.quizProgressbar);

        questionTv = findViewById(R.id.qnTvId);
        ansFeedBackTv = findViewById(R.id.ansFeedbackTvId);
        questionNumberTv = findViewById(R.id.quizQuestionsCount);
        timerCountTv = findViewById(R.id.countTimeQuiz);


        // Setting click listeners for option buttons and next question button.
        option1Btn.setOnClickListener(this);
        option2Btn.setOnClickListener(this);
        option3Btn.setOnClickListener(this);
        nextQueBtn.setOnClickListener(this);


        // Retrieving quiz details from the CategoriesAdapter.
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                quizId = bundle.getString("quizId");
                totalQuestions = 3; //bundle.getInt("totalQ");
            }
        }


        // Setting the quiz ID in the ViewModel
        viewModel.setQuizId(quizId);

        //loading initial data
        loadData();

    }

    // Load initial data for the quiz, enabling options for the first question.
    private void loadData() {
        enableOption();
        loadQuestions(1);
    }


    // Enable quiz options, hide feedback and next button.
    private void enableOption() {
        option1Btn.setVisibility(View.VISIBLE);
        option2Btn.setVisibility(View.VISIBLE);
        option3Btn.setVisibility(View.VISIBLE);

        //enable button . hide feedback textview and next Button
        option1Btn.setEnabled(true);
        option2Btn.setEnabled(true);
        option3Btn.setEnabled(true);

        ansFeedBackTv.setVisibility(View.INVISIBLE);
        nextQueBtn.setVisibility(View.INVISIBLE);

    }



    // Load questions and set up UI for the current question.
    private void loadQuestions(int i) {
        currentQueNo = i;
        viewModel.getQmutableLiveData().observe(this, new Observer<List<QuestionModel>>() {
            @Override
            public void onChanged(List<QuestionModel> questionModels) {
                Qprogress.setVisibility(View.GONE);
                final JLatexMathDrawable drawable = JLatexMathDrawable.builder(String.valueOf(currentQueNo) + ") " +questionModels.get(i - 1).getQuestion()
                        )
                        .textSize(50)
                        .padding(8)
                        .background(0xFFffffff)
                        .align(JLatexMathDrawable.ALIGN_RIGHT)
                        .build();
                questionTv.setLatexDrawable(drawable);
                option1Btn.setText(questionModels.get(i - 1).getOption_a());
                option2Btn.setText(questionModels.get(i - 1).getOption_b());
                option3Btn.setText(questionModels.get(i - 1).getOption_c());
                timer = questionModels.get(i - 1).getTimer();
                answer = questionModels.get(i - 1).getAnswer();

                //todo set current que no, to que number tv
                questionNumberTv.setText(String.valueOf(currentQueNo+"/"+totalQuestions));
                startTimer();
            }
        });
        canAnswer = true;
    }


    // Start the timer for each question.
    private void startTimer() {
        timerCountTv.setText(String.valueOf(timer));
        progressBar.setVisibility(View.VISIBLE);

        countDownTimer = new CountDownTimer(timer * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // update time
                timerCountTv.setText(millisUntilFinished / 1000 + "");

                Long percent = millisUntilFinished / (timer * 10);
                progressBar.setProgress(percent.intValue());
            }

            @Override
            public void onFinish() {
                canAnswer = false;
                ansFeedBackTv.setText("Times Up !! No answer selected");
                notAnswerd++;
                showNextBtn();
            }
        }.start();
    }


    // Verify the selected answer and update UI accordingly.
    private void verifyAnswer(Button button) {
        if (canAnswer) {
            if (answer.contains(button.getText())) {
                button.setBackgroundResource(R.drawable.green_rounded_corner);
                correctAnswer++;
                ansFeedBackTv.setText("Correct Answer");
            } else {
                button.setBackgroundResource(R.drawable.red_rounded_corner);
                wrongAnswer++;
                ansFeedBackTv.setText("Wrong Answer \nCorrect Answer :" + answer);
            }
        }
        canAnswer = false;
        countDownTimer.cancel();
        showNextBtn();
    }



    // Handle button clicks for quiz options and next question.
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.opBtnId_1) {
            verifyAnswer(option1Btn);
        } else if (view.getId() == R.id.opBtnId_2) {
            verifyAnswer(option2Btn);
        } else if (view.getId() == R.id.opBtnId_3) {
            verifyAnswer(option3Btn);
        } else if (view.getId() == R.id.nextQnBtnId) {
            if (currentQueNo == totalQuestions) {
                submitResults();
            } else {
                currentQueNo++;
                loadQuestions(currentQueNo);
                resetOptions();
            }
        }
    }



    // Reset UI options for the next question.
    private void resetOptions() {
        ansFeedBackTv.setVisibility(View.INVISIBLE);
        nextQueBtn.setVisibility(View.INVISIBLE);
        nextQueBtn.setEnabled(false);
        option1Btn.setBackgroundResource(R.drawable.btn_rounded_corners);
        option2Btn.setBackgroundResource(R.drawable.btn_rounded_corners);
        option3Btn.setBackgroundResource(R.drawable.btn_rounded_corners);
    }



    // Submit quiz results and navigate to the result activity.
    private void submitResults() {

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("correct", correctAnswer);
        intent.putExtra("wrong", wrongAnswer);
        intent.putExtra("notAnswered", notAnswerd);
        startActivity(intent);
        finishAffinity();
    }


    // Display the next button based on the current question number.
    private void showNextBtn() {
        if (currentQueNo == totalQuestions) {
            nextQueBtn.setText("Submit");
            nextQueBtn.setEnabled(true);
            nextQueBtn.setVisibility(View.VISIBLE);
        } else {
            nextQueBtn.setVisibility(View.VISIBLE);
            nextQueBtn.setEnabled(true);
            ansFeedBackTv.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you to close the quiz?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }


}