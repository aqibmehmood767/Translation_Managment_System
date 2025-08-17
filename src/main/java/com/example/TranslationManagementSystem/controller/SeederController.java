package com.example.TranslationManagementSystem.controller;

import com.example.TranslationManagementSystem.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/dev")
public class SeederController {
    @Autowired private TranslationService service;

    @PostMapping("/seed")
    public ResponseEntity<String> seedTranslations(@RequestParam(defaultValue = "100000") int count) {
        long start = System.currentTimeMillis();
        service.seed(count);
        long end = System.currentTimeMillis();
        return ResponseEntity.ok("Seeded " + count + " records in " + (end - start) + " ms");
    }
}
