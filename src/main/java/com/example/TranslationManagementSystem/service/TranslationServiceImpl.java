package com.example.TranslationManagementSystem.service;


import com.example.TranslationManagementSystem.dto.TranslationDTO;
import com.example.TranslationManagementSystem.entity.Tag;
import com.example.TranslationManagementSystem.entity.Translation;
import com.example.TranslationManagementSystem.entity.TranslationKey;
import com.example.TranslationManagementSystem.repository.TagRepository;
import com.example.TranslationManagementSystem.repository.TranslationKeyRepository;
import com.example.TranslationManagementSystem.repository.TranslationRepository;
import com.example.TranslationManagementSystem.util.TranslationMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TranslationServiceImpl implements TranslationService {

    @Autowired private TranslationRepository repo;
    @Autowired private TranslationMapper mapper;
    @Autowired private TagRepository tagRepo;
    @Autowired private TranslationKeyRepository translationKeyRepo;

    @Transactional
    public TranslationDTO create(TranslationDTO dto) {
        return mapper.toDTO(repo.save(mapper.toEntity(dto)));
    }

    @Transactional
    public TranslationDTO update(Long id, TranslationDTO dto) {
        Translation existing = repo.findById(id).orElseThrow();
        Translation up = mapper.toEntity(dto);
        // preserve ID and relational link
        up.setId(existing.getId());
        return mapper.toDTO(repo.save(up));
    }

    public List<TranslationDTO> search(String key, String content, String tag) {
        if (key == null || key.isBlank()) key = "";
        if (content == null || content.isBlank()) content = "";
        if (tag == null || tag.isBlank()) tag = "";

        return repo
                .search(key, content, tag)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }



    public Map<String, String> export(String locale) {
        return repo.findByLocale(locale).stream()
                .collect(Collectors.toMap(
                        t -> t.getTranslationKey().getKey(),
                        Translation::getContent
                ));
    }


    @Transactional
    public void seed(int count) {
        System.out.println("Seeding " + count);

        List<Translation> batch = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String keyName = "key_" + i;

            //either find existing TranslationKey or create/save a new one
            TranslationKey translationKey = translationKeyRepo.findByKey(keyName)
                    .orElseGet(() -> translationKeyRepo.save(new TranslationKey(keyName)));

            Translation translation = new Translation();
            translation.setTranslationKey(translationKey);
            translation.setLocale("en");
            translation.setContent("auto content " + i);

            batch.add(translation);

            //batch insert every 1000 items
            if (batch.size() % 1000 == 0) {
                repo.saveAll(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            repo.saveAll(batch);
        }

        System.out.println("Seeding completed successfully");
    }

}