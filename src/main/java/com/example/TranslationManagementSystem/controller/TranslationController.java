package com.example.TranslationManagementSystem.controller;

import com.example.TranslationManagementSystem.dto.TranslationDTO;
import com.example.TranslationManagementSystem.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;



@RestController
@RequestMapping("/api/translations")
@Tag(name = "Translation API", description = "Endpoints for managing translations, tags, and exporting locale-specific content")
@SecurityRequirement(name = "BearerAuth")
public class TranslationController {

    @Autowired
    private TranslationService service;

    @PostMapping
    @Operation(summary = "Create a new translation")
    public TranslationDTO create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Translation data to create")
            @RequestBody TranslationDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing translation by ID")
    public TranslationDTO update(
            @Parameter(description = "ID of the translation to update") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated translation data")
            @RequestBody TranslationDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping("/search")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public List<TranslationDTO> search(
            @Parameter(description = "Search by key name") @RequestParam(required = false) String key,
            @Parameter(description = "Search by content") @RequestParam(required = false) String content,
            @Parameter(description = "Search by tag") @RequestParam(required = false) String tag) {
        System.out.println("Searching for key: " + key);
        System.out.println("Searching for content: " + content);
        return service.search(key, content, tag);
    }

    @GetMapping("/export/{locale}")
    @Operation(summary = "Export translations for a given locale as JSON")
    public Map<String, String> export(
            @Parameter(description = "Locale code (e.g., en, fr, ar)") @PathVariable String locale) {
        return service.export(locale);
    }
}

