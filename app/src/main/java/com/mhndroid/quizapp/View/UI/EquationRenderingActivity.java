package com.mhndroid.quizapp.View.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mhndroid.quizapp.R;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class EquationRenderingActivity extends AppCompatActivity {

    private JLatexMathView mathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eqn_rendering);

        mathView = findViewById(R.id.j_latex_math_view);

        final JLatexMathDrawable drawable = JLatexMathDrawable.builder("((a+b)^2 = a^2 + 2ab + b^2\\\\\n" +
                        "(a-b)^2 = a^2 - 2ab + b^2\\\\\n" +
                        "(a+b)(a-b) = a^2 - b^2\\\\\n" + "k_n = k_{n-1} + k_{n-2} \\\\\n" + "cos2 \\theta = 1 - 2sin^2\\theta\\\\\n"
                          + "\\log ab = \\log cd "
                )
                .textSize(50)
                .padding(8)
                .background(0xFFffffff)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();
        mathView.setLatexDrawable(drawable);
    }
}