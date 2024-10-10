package com.example.demo.services;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.demo.entities.OtpEntity;
import com.example.demo.exceptions.OtpExpiredException;
import com.example.demo.exceptions.OtpNotFoundException;
import com.example.demo.models.OtpResponse;
import com.example.demo.repository.OtpRepository;

import reactor.core.publisher.Mono;

@Service
public class OtpService {

    private static final Duration OTP_DURATION = Duration.ofSeconds(120); // Duración de validez del OTP
    private final OtpRepository otpRepository;
    private final OtpGenerator otpGenerator;

    // Constructor sin OtpEncryptor
    public OtpService(OtpRepository otpRepository, OtpGenerator otpGenerator) {
        this.otpRepository = otpRepository;
        this.otpGenerator = otpGenerator;
    }

    // Método para generar el OTP sin encriptación
    public Mono<OtpResponse> generateOtp() {
        return Mono.fromCallable(() -> {
            String otp = otpGenerator.generateOtp();
            storeOtp(otp);  // Guardamos el OTP directamente sin encriptar
            return new OtpResponse(otp, OTP_DURATION.getSeconds());
        });
    }

    // Método para validar el OTP sin desencriptación
    public Mono<Boolean> validateOtp(String otp, String inputOtp) throws Exception {
        // Busca el OTP en el repositorio
        OtpEntity otpEntity = otpRepository.findById(otp).orElseThrow(() -> new OtpNotFoundException("OTP not found"));

        if (isOtpExpired(otpEntity.expirationTime())) {
            throw new OtpExpiredException("OTP has expired");
        }

        // Compara directamente los OTPs
        return Mono.just(otp.equals(inputOtp));
    }

    // Almacena el OTP directamente sin encriptar
    private void storeOtp(String otp) {
        otpRepository.save(new OtpEntity(otp, Instant.now().plus(OTP_DURATION)));
    }

    // Verifica si el OTP ha expirado
    private boolean isOtpExpired(Instant expirationTime) {
        return Instant.now().isAfter(expirationTime);
    }
}
