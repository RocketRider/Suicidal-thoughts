package de.rrsoftware.suicidalthoughts.content.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import de.rrsoftware.suicidalthoughts.R;
import de.rrsoftware.suicidalthoughts.content.model.ContentDocument;

public class ContentActivity extends AppCompatActivity {
    private RecyclerView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        contentView = (RecyclerView) findViewById(R.id.contentEntries);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        contentView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        contentView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        ContentAdapter adapter = new ContentAdapter(ContentDocument.findById("content"));
        contentView.setAdapter(adapter);

    }
}
