package de.rrsoftware.suicidalthoughts.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.rrsoftware.suicidalthoughts.common.CommonVars;
import de.rrsoftware.suicidalthoughts.common.git.GitRepo;
import de.rrsoftware.suicidalthoughts.content.model.ContentLoader;

public class AppInitTask extends AsyncTask<Context, Void, Void> {
    private static final String LOGTAG = "AppInitTask";
    private static boolean isInitialized = false;

    private void downloadContent() {
        if (!CommonVars.contentPath.exists()) {
            CommonVars.contentPath.mkdir();
        }

        if (!GitRepo.pull(CommonVars.contentRepoURL, CommonVars.contentPath)) {
            Log.e(LOGTAG, "pull failed!");
        }
    }

    @Override
    protected Void doInBackground(Context... context) {
        if (!isInitialized) {
            isInitialized = true;
            CommonVars.init(context[0]);
            downloadContent();
            ContentLoader.loadContent();
        }
        return null;
    }
}
