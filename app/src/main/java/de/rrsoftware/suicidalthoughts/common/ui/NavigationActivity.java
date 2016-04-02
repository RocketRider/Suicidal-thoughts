package de.rrsoftware.suicidalthoughts.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import de.rrsoftware.suicidalthoughts.R;
import de.rrsoftware.suicidalthoughts.about.ui.AboutActivity;
import de.rrsoftware.suicidalthoughts.content.ui.ContentActivity;
import de.rrsoftware.suicidalthoughts.questions.ui.QuestionsActivity;

public class NavigationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView drawer;
    private DrawerItem[] drawerItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerItems = new DrawerItem[]{
                new DrawerItem(R.string.content_title, ContentActivity.class),
                new DrawerItem(R.string.questions_title, QuestionsActivity.class),
                new DrawerItem(R.string.about, AboutActivity.class)
        };

        drawer = (ListView) findViewById(R.id.left_drawer);
        drawer.setAdapter(new ItemAdapter());
        drawer.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(drawerItems[position].intent);
        finish();
    }


    private class ItemAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return drawerItems.length;
        }

        @Override
        public Object getItem(int position) {
            return drawerItems[position];
        }

        @Override
        public long getItemId(int position) {
            return drawerItems[position].hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.navigation_drawer_item,
                        container, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1)).setText(
                    drawerItems[position].titleResId);
            return convertView;
        }
    }

    private class DrawerItem {
        int titleResId;
        Intent intent;

        private DrawerItem(int titleResId, Intent intent) {
            this.intent = intent;
            this.titleResId = titleResId;
        }

        private DrawerItem(int titleResId,
                           Class<? extends AppCompatActivity> activityClass) {
            this(titleResId, new Intent(NavigationActivity.this, activityClass));
        }
    }

}
