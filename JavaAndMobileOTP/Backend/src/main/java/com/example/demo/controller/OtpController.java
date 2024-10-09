package com.example.demo.controller;

import com.example.demo.services.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/otp")
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
        return otpService.validateOtp(request.getOtp(), request.getInputOtp())
                .map(isValid -> ResponseEntity.ok(Collections.singletonMap("isValid", isValid)));
    }

    public static class OtpValidationRequest {
        private String otp;
        private String inputOtp;

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getInputOtp() {
            return inputOtp;
        }

        public void setInputOtp(String inputOtp) {
            this.inputOtp = inputOtp;
        }
    }
}
