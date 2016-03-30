package de.rrsoftware.suicidalthoughts.common;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public final class CommonVars {
    public static File contentPath;
    public static final String contentRepoURL = "https://github.com/RocketRider/Suicidal-thoughts-content.git";

    private CommonVars() {
        //Util class
    }

    public static void init(final Context context) {
        //ContextWrapper cw = new ContextWrapper(context);
        contentPath = new File(Environment.getExternalStorageDirectory(), "ST_content");//cw.getDir("content", Context.MODE_PRIVATE);
    }

}
