package com.example.demo.service;

import com.example.demo.dto.BFHLRequest;
import com.example.demo.dto.BFHLResponse;
import com.example.demo.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BFHLServiceTest {

    private BFHLServiceImpl bfhlService;

    @BeforeEach
    void setUp() {
        bfhlService = new BFHLServiceImpl();
    }

    @Test
    void testExampleA() {
        BFHLRequest request = new BFHLRequest();
        request.setData(Arrays.asList("a", "1", "334", "4", "R", "$"));

        BFHLResponse response = bfhlService.processBFHLData(request);

        assertTrue(response.isSuccess());
        assertEquals(Constants.USER_ID, response.getUserId());
        assertEquals(Constants.EMAIL, response.getEmail());
        assertEquals(Constants.ROLL_NUMBER, response.getRollNumber());
        assertEquals(List.of("1"), response.getOddNumbers());
        assertEquals(Arrays.asList("334", "4"), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "R"), response.getAlphabets());
        assertEquals(List.of("$"), response.getSpecialCharacters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    void testExampleB() {
        BFHLRequest request = new BFHLRequest();
        request.setData(Arrays.asList("A", "ABCD", "DOE"));

        BFHLResponse response = bfhlService.processBFHLData(request);

        assertEquals(Collections.emptyList(), response.getOddNumbers());
        assertEquals(Collections.emptyList(), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "ABCD", "DOE"), response.getAlphabets());
        assertEquals(Collections.emptyList(), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    @Test
    void testEmptyInput() {
        BFHLRequest request = new BFHLRequest();
        request.setData(Collections.emptyList());

        BFHLResponse response = bfhlService.processBFHLData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    void testSpecialCharactersOnly() {
        BFHLRequest request = new BFHLRequest();
        request.setData(Arrays.asList("@", "#", "12a", " "));

        BFHLResponse response = bfhlService.processBFHLData(request);

        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals(Arrays.asList("@", "#", "12a", " "), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }
}
