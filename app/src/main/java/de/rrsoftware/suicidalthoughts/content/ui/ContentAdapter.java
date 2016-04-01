package de.rrsoftware.suicidalthoughts.content.ui;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import de.rrsoftware.suicidalthoughts.R;
import de.rrsoftware.suicidalthoughts.content.model.ContentDocument;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    private ContentDocument document;
    private WebView aboutView;

    public ContentAdapter(ContentDocument document, Activity parent) {
        aboutView = (WebView) parent.findViewById(R.id.aboutView);
        setDocument(document);
    }

    public ContentDocument getDocument() {
        return document;
    }

    public void setDocument(final ContentDocument document) {
        this.document = document;
        notifyDataSetChanged();

        if (document != null && aboutView != null) {
            if (document.content != null && !document.content.isEmpty()) {
                aboutView.setVisibility(View.VISIBLE);
                aboutView.loadUrl(document.getContentURL());
                aboutView.setBackgroundColor(Color.TRANSPARENT);
            } else {
                aboutView.setVisibility(View.GONE);
            }
        }

        //TODO: Add to back stack
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_content, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setDocument(document.entries[position].getDocument());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return document.entries.length;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        // each data item is just a string in this case
        private final View view;
        private ContentDocument document;

        public ViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentAdapter.this.setDocument(document);
                }
            });
        }

        public void setDocument(ContentDocument document) {
            this.document = document;
            title.setText(document.title);
            description.setText(document.description);
        }
    }
}