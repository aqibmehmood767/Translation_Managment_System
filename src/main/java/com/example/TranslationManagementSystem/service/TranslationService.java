package com.example.TranslationManagementSystem.service;

import com.example.TranslationManagementSystem.dto.TranslationDTO;
import com.example.TranslationManagementSystem.entity.Translation;

import java.util.List;
import java.util.Map;

public interface TranslationService {
    TranslationDTO create(TranslationDTO dto);
    TranslationDTO update(Long id, TranslationDTO dto);
    List<TranslationDTO> search(String key, String content, String tag);
    Map<String, String> export(String locale);
    void seed(int count);
}




