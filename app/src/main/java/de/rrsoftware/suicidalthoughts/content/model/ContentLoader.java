package de.rrsoftware.suicidalthoughts.content.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import de.rrsoftware.suicidalthoughts.common.CommonVars;

public final class ContentLoader {
    private ContentLoader() {
        //Util class
    }

    public static void loadContent() {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File(CommonVars.contentPath, "/content/entry.json");
        ContentDocument document = null;
        try {
            document = mapper.readValue(file, ContentDocument.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (document != null) {
            String id = "content";//TODO
            document.setId(id);
        }
    }

}
