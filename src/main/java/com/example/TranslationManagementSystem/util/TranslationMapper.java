package com.example.TranslationManagementSystem.util;

import com.example.TranslationManagementSystem.dto.TranslationDTO;
import com.example.TranslationManagementSystem.entity.Tag;
import com.example.TranslationManagementSystem.entity.Translation;
import com.example.TranslationManagementSystem.entity.TranslationKey;
import com.example.TranslationManagementSystem.repository.TagRepository;
import com.example.TranslationManagementSystem.repository.TranslationKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class TranslationMapper {

    @Autowired private TagRepository tagRepo;
    @Autowired private TranslationKeyRepository keyRepo;

    public TranslationDTO toDTO(Translation t) {
        TranslationDTO dto = new TranslationDTO();
        dto.setId(t.getId());
        dto.setKey(t.getTranslationKey().getKey());
        dto.setLocale(t.getLocale());
        dto.setContent(t.getContent());
        dto.setTags(
                t.getTranslationKey().getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toSet())
        );
        return dto;
    }

    public Translation toEntity(TranslationDTO dto) {
        //find or create TranslationKey
        TranslationKey tk = keyRepo.findByKey(dto.getKey())
                .orElseGet(() -> keyRepo.save(new TranslationKey(dto.getKey())));

        //update tags on the key
        tk.getTags().clear();
        dto.getTags().forEach(name -> {
            Tag tag = tagRepo.findByName(name).orElseGet(() -> tagRepo.save(new Tag(name)));
            tk.getTags().add(tag);
        });

        Translation t = new Translation();
        t.setTranslationKey(tk);
        t.setLocale(dto.getLocale());
        t.setContent(dto.getContent());
        return t;
    }
}