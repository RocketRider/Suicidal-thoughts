package de.rrsoftware.suicidalthoughts.about.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import de.rrsoftware.suicidalthoughts.R;
import de.rrsoftware.suicidalthoughts.common.ui.NavigationActivity;

public class AboutActivity extends NavigationActivity {
    private final String LOGTAG = "About";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);
        super.onCreate(savedInstanceState);

        //Set version number
        try {
            final String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView versionView = (TextView) findViewById(R.id.version);
            versionView.setText(String.format(getString(R.string.version_text), version));

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOGTAG, "failed to get version", e);
        }
    }
}
