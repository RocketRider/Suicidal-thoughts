package de.rrsoftware.suicidalthoughts.questions.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.rrsoftware.suicidalthoughts.R;

public class QuestionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        setTitle(R.string.questions_title);
    }
}
