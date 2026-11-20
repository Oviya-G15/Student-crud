package com.example.crud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentApiTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void createAndFetch() throws Exception {
        mvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Arun\",\"email\":\"arun@gmail.com\",\"course\":\"Java\"}"))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/students")).andExpect(status().isOk());
    }

    @Test
    void invalidEmailRejected() throws Exception {
        mvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Arun\",\"email\":\"bad\"}"))
                .andExpect(status().isBadRequest());
    }
}
