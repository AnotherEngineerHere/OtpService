package com.example.demo.repository;

import com.example.demo.entities.OtpEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OtpRepository {

    private final ConcurrentHashMap<String, OtpEntity> otpStore = new ConcurrentHashMap<>();

    public void save(OtpEntity otpEntity) {
        otpStore.put(otpEntity.otp(), otpEntity);
    }

    public Optional<OtpEntity> findById(String otp) {
        return Optional.ofNullable(otpStore.get(otp));
    }
}
