package com.example.TranslationManagementSystem.repository;

import com.example.TranslationManagementSystem.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {

    @Query("""
SELECT t FROM Translation t JOIN t.translationKey tk
LEFT JOIN tk.tags tag WHERE (:key IS NULL OR LOWER(tk.key) LIKE LOWER(CONCAT('%', :key, '%')))
AND (:content IS NULL OR LOWER(t.content) LIKE LOWER(CONCAT('%', :content, '%'))) AND (:tag IS NULL OR LOWER(tag.name) LIKE LOWER(CONCAT('%', :tag, '%')))
""")
    List<Translation> search(@Param("key") String key, @Param("content") String content, @Param("tag") String tag);


    List<Translation> findByLocale(String locale);
}


