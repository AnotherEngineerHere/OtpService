package com.example.demo.services;

import org.springframework.stereotype.Service;

@Service
public class OtpGenerator {

    public String generateOtp() {
        int otp = (int) (Math.random() * 1000000);
        return String.format("%06d", otp);
    }
}
