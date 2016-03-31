package de.rrsoftware.suicidalthoughts.content.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.rrsoftware.suicidalthoughts.R;
import de.rrsoftware.suicidalthoughts.content.model.ContentDocument;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    private final ContentDocument document;

    public ContentAdapter(ContentDocument document) {
        this.document = document;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_content, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText("Title");//document.entries[position].getDocument().title);
        holder.description.setText(document.entries[position].id);//document.entries[position].getDocument().title);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return document.entries.length;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView description;
        // each data item is just a string in this case
        private final View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
        }
    }
}