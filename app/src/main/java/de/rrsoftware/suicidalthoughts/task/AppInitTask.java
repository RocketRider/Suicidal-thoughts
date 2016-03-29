package de.rrsoftware.suicidalthoughts.task;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import de.rrsoftware.suicidalthoughts.common.git.GitRepo;

public class AppInitTask extends AsyncTask<Context, Void, Void> {

    private void downloadContent(final Context context) {
        //ContextWrapper cw = new ContextWrapper(context);
        File contentPath = new File(Environment.getExternalStorageDirectory(), "ST_content");//cw.getDir("content", Context.MODE_PRIVATE);
        if (!contentPath.exists()) {
            contentPath.mkdir();
        }

        Log.e("TEST", "Git start pull " + contentPath);
        boolean result = GitRepo.pull("https://github.com/RocketRider/Suicidal-thoughts-content.git", contentPath);
        Log.e("TEST", "Git result " + result);
    }

    @Override
    protected Void doInBackground(Context... context) {
        downloadContent(context[0]);
        return null;
    }
}
