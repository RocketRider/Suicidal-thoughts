package de.rrsoftware.suicidalthoughts.questions.ui;

import android.os.Bundle;

import de.rrsoftware.suicidalthoughts.R;
import de.rrsoftware.suicidalthoughts.common.ui.NavigationActivity;

public class QuestionsActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_questions);
        super.onCreate(savedInstanceState);
        setTitle(R.string.questions_title);
    }
}
