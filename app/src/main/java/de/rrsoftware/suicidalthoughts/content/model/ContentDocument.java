package de.rrsoftware.suicidalthoughts.content.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentDocument {
    private static final Map<String, ContentDocument> documents = new HashMap<>();
    @JsonProperty("title")
    public String title;
    @JsonProperty("description")
    public String description;
    @JsonProperty("content")
    public String content;
    @JsonProperty("image")
    public String image;
    @JsonProperty("icon")
    public String icon;
    @JsonProperty("entries")
    public ContentRefEntry[] entries;
    //Specified with path to json file
    private String id = null;
    private String contentURL = null;

    public static ContentDocument findById(final String id) {
        return documents.get(id);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
        documents.put(id, this);
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setBasePath(final String url) {
        if (content != null && !content.isEmpty()) {
            contentURL = "file://" + url + "/" + content;
        }
        if (image != null && !image.isEmpty()) {
            image = url + "/" + image;
        }
        if (icon != null && !icon.isEmpty()) {
            icon = url + "/" + icon;
        }
    }

}
