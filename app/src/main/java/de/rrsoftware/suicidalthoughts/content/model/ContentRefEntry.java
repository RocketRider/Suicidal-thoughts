package de.rrsoftware.suicidalthoughts.content.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentRefEntry {
    @JsonProperty("id")
    public String id;
    private ContentDocument ref;

    public ContentDocument getDocument() {
        if (ref == null) {
            ref = ContentDocument.findById(id);
        }
        return ref;
    }
}
