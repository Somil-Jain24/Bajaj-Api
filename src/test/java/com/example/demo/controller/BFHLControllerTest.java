package com.example.demo.controller;

import com.example.demo.dto.BFHLRequest;
import com.example.demo.dto.BFHLResponse;
import com.example.demo.service.BFHLService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BFHLControllerTest {
    private MockMvc mockMvc;

    private BFHLService bfhlService = Mockito.mock(BFHLService.class);
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BFHLController(bfhlService)).build();
    }

    @Test
    void testProcessBFHL() throws Exception {
        BFHLRequest request = new BFHLRequest(Arrays.asList("a", "1"));
        
        BFHLResponse response = BFHLResponse.builder()
                .isSuccess(true)
                .userId(com.example.demo.util.Constants.USER_ID)
                .email(com.example.demo.util.Constants.EMAIL)
                .rollNumber(com.example.demo.util.Constants.ROLL_NUMBER)
                .oddNumbers(Collections.singletonList("1"))
                .evenNumbers(Collections.emptyList())
                .alphabets(Collections.singletonList("A"))
                .specialCharacters(Collections.emptyList())
                .sum("1")
                .concatString("A")
                .build();

        Mockito.when(bfhlService.processBFHLData(any(BFHLRequest.class))).thenReturn(response);

        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.user_id").value(com.example.demo.util.Constants.USER_ID))
                .andExpect(jsonPath("$.odd_numbers[0]").value("1"))
                .andExpect(jsonPath("$.alphabets[0]").value("A"));
    }
}
