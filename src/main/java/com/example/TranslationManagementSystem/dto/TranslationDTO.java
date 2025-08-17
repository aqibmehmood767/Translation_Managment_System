package com.example.TranslationManagementSystem.dto;

import java.util.List;
import java.util.Set;

public class TranslationDTO {
    private Long id;
    private String key;
    private String locale;
    private String content;
    private Set<String> tags;

    public TranslationDTO() {
    }

    public TranslationDTO(Long id, String key, String locale, String content, Set<String> tags) {
        this.id = id;
        this.key = key;
        this.locale = locale;
        this.content = content;
        this.tags = tags;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}

