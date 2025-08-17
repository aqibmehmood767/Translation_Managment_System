package com.example.TranslationManagementSystem.controller;

import com.example.TranslationManagementSystem.dto.TranslationDTO;
import com.example.TranslationManagementSystem.service.TranslationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TranslationController.class)
@Import(TestSecurityConfig.class) // permitAll for tests
class TranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private TranslationService service;

    @Test
    @DisplayName("GET /api/translations/search returns list of translations")
    void search_returnsList() throws Exception {
        TranslationDTO dto1 = new TranslationDTO(1L, "key_1", "en", "hello", Set.of("web"));
        TranslationDTO dto2 = new TranslationDTO(2L, "key_2", "fr", "bonjour", Set.of("mobile"));

        Mockito.when(service.search(eq("key_"), isNull(), isNull()))
                .thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/translations/search")
                        .param("key", "key_")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].key", is("key_1")))
                .andExpect(jsonPath("$[0].locale", is("en")))
                .andExpect(jsonPath("$[1].key", is("key_2")))
                .andExpect(jsonPath("$[1].locale", is("fr")));
    }

    @Test
    @DisplayName("POST /api/translations creates a translation")
    void create_createsTranslation() throws Exception {
        TranslationDTO req = new TranslationDTO(null, "greeting", "en", "Hello", Set.of("web"));
        TranslationDTO res = new TranslationDTO(10L, "greeting", "en", "Hello", Set.of("web"));

        Mockito.when(service.create(any(TranslationDTO.class))).thenReturn(res);

        String body = """
            {
              "id": null,
              "key": "greeting",
              "locale": "en",
              "content": "Hello",
              "tags": ["web"]
            }
            """;

        mockMvc.perform(post("/api/translations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.key", is("greeting")))
                .andExpect(jsonPath("$.locale", is("en")))
                .andExpect(jsonPath("$.content", is("Hello")));
    }

    @Test
    @DisplayName("PUT /api/translations/{id} updates a translation")
    void update_updatesTranslation() throws Exception {
        TranslationDTO res = new TranslationDTO(5L, "greeting", "en", "Hi there", Set.of("web"));

        Mockito.when(service.update(eq(5L), any(TranslationDTO.class))).thenReturn(res);

        String body = """
            {
              "id": 5,
              "key": "greeting",
              "locale": "en",
              "content": "Hi there",
              "tags": ["web"]
            }
            """;

        mockMvc.perform(put("/api/translations/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.content", is("Hi there")));
    }

    @Test
    @DisplayName("GET /api/translations/export/{locale} returns key->content map")
    void export_returnsMap() throws Exception {
        Mockito.when(service.export("en"))
                .thenReturn(Map.of("greeting", "Hello", "farewell", "Bye"));

        mockMvc.perform(get("/api/translations/export/en")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.greeting", is("Hello")))
                .andExpect(jsonPath("$.farewell", is("Bye")));
    }
}

