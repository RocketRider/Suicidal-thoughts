package de.rrsoftware.suicidalthoughts.common.git;

import android.util.Log;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public final class GitRepo {
    private static final String LOGTAG = "GitRepoUtil";

    private GitRepo() {
        //Util class
    }

    public static boolean pull(final String remoteUri, final File localDir) {
        boolean result = false;
        Git git = null;

        //Try to open the git repo
        try {
            git = Git.open(localDir);
        } catch (Exception e) {
            Log.i(LOGTAG, "open git repo failed", e);
            e.printStackTrace();
        }

        //If the open failed, try to clone it from the server
        if (git == null) {
            try {
                git = Git.cloneRepository().setURI(remoteUri).setDirectory(localDir).call();
            } catch (Exception e) {
                Log.e(LOGTAG, "clone git repo failed", e);
            }
        }

        //If the git is initialized, update it to the newest version
        if (git == null) {
            try {
                result = git.pull().call().isSuccessful();
            } catch (Exception e) {
                Log.e(LOGTAG, "pull git repo failed", e);
            }
        }

        //TODO: Issue, always returns false
        return result;
    }

}
