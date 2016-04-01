package de.rrsoftware.suicidalthoughts.content.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import de.rrsoftware.suicidalthoughts.R;
import de.rrsoftware.suicidalthoughts.content.model.ContentDocument;

public class ContentActivity extends AppCompatActivity {
    private final static String KEY_DOCUMENT = "document";

    private RecyclerView contentView;
    private ContentAdapter adapter;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_DOCUMENT, adapter.getDocument().getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        contentView = (RecyclerView) findViewById(R.id.contentEntries);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        contentView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        contentView.setLayoutManager(layoutManager);

        String doc = "content";
        if (savedInstanceState != null) {
            doc = savedInstanceState.getString(KEY_DOCUMENT);
        }
        adapter = new ContentAdapter(ContentDocument.findById(doc), this);
        contentView.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        boolean handled = false;
        if (adapter != null && adapter.getDocument() != null) {
            String docId = adapter.getDocument().getId();
            if (docId.contains(".")) {
                do {
                    docId = docId.substring(0, docId.lastIndexOf('.'));
                    ContentDocument newDoc = ContentDocument.findById(docId);
                    if (newDoc != null) {
                        adapter.setDocument(newDoc);
                        handled = true;
                    }
                } while (!handled && docId.contains("."));
            }
        }
        if (!handled) {
            super.onBackPressed();
        }
    }
}
