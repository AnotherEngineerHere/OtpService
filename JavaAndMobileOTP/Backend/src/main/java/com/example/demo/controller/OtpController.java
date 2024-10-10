package com.example.demo.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.OtpValidationRequest;
import com.example.demo.services.OtpService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "*")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<Map<String, Object>>> generateOtp() {
        return otpService.generateOtp()
                .map(response -> ResponseEntity.ok(Collections.singletonMap("otp", response)));
    }

    @PostMapping("/validate")
    public Mono<ResponseEntity<Map<String, Boolean>>> validateOtp(@RequestBody OtpValidationRequest request) {
        try {
            return otpService.validateOtp(request.getOtp(), request.getInputOtp())
                    .map(isValid -> ResponseEntity.ok(Collections.singletonMap("isValid", isValid)));
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return null;
    }
}
