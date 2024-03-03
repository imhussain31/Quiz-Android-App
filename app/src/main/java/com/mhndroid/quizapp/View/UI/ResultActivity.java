package com.mhndroid.quizapp.View.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhndroid.quizapp.R;

public class ResultActivity extends AppCompatActivity {

    TextView correctAns;
    TextView wrongAns;
    TextView notAns;

    ImageView resultStatus;
    private int crAns , wrAns , Nans;

    Button goHome;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        correctAns = findViewById(R.id.correctAnswerTvId);
        wrongAns = findViewById(R.id.wrongAnswerTvId);
        notAns = findViewById(R.id.notAnsTvId);
        goHome = findViewById(R.id.goHomebtnId);
        resultStatus = findViewById(R.id.imgResultId);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                crAns = bundle.getInt("correct");
                wrAns = bundle.getInt("wrong");
                Nans = bundle.getInt("notAnswered");
            }
        }

        correctAns.setText(String.valueOf(crAns));
        wrongAns.setText(String.valueOf(wrAns));
        notAns.setText(String.valueOf(Nans));

        if (crAns> (wrAns+Nans)){
            resultStatus.setImageResource(R.drawable.passed);
        }else {
            resultStatus.setImageResource(R.drawable.failed);

        }

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , CategoriesActivity.class));
                finishAffinity();
            }
        });

    }
}