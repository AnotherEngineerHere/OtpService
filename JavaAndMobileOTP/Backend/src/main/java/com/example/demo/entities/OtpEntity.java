package com.example.demo.entities;

import java.time.Instant;

public record OtpEntity(String otp, Instant expirationTime) {

}
