package com.example.demo.controller;

import com.example.demo.dto.BFHLRequest;
import com.example.demo.dto.BFHLResponse;
import com.example.demo.service.BFHLService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bfhl")
@RequiredArgsConstructor
public class BFHLController {

    private final BFHLService bfhlService;

    @PostMapping
    public ResponseEntity<BFHLResponse> processBFHL(@RequestBody BFHLRequest request) {
        try {
            BFHLResponse response = bfhlService.processBFHLData(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
