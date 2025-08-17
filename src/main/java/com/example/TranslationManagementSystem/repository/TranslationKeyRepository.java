package com.example.TranslationManagementSystem.repository;


import com.example.TranslationManagementSystem.entity.TranslationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslationKeyRepository extends JpaRepository<TranslationKey, Long> {

    Optional<TranslationKey> findByKey(String key);
}

