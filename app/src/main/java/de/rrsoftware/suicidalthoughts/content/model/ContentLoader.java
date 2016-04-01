package de.rrsoftware.suicidalthoughts.content.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import de.rrsoftware.suicidalthoughts.common.CommonVars;

public final class ContentLoader {
    private ContentLoader() {
        //Util class
    }

    private static void loadContentFile(final File file, final String id) {
        ObjectMapper mapper = new ObjectMapper();

        ContentDocument document = null;
        try {
            document = mapper.readValue(file, ContentDocument.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (document != null) {
            document.setId(id);
            document.setBasePath(file.getParent());
        }
    }

    private static void walk(final File root, final String id) {
        File[] list = root.listFiles();
        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f, id + "." + f.getName());
            } else {
                if (f.getName().equalsIgnoreCase("entry.json")) {
                    loadContentFile(f, id);
                }
            }
        }
    }

    public static void loadContent() {
        File path = new File(CommonVars.contentPath, "/content");
        walk(path, "content");
    }

}
