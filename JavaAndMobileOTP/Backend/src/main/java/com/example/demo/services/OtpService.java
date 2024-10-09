package com.example.demo.services;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class OtpService {

    private static final String SECRET_KEY = "yourSharedSecretKey"; // Cambia esto a una clave segura
    private static final Duration OTP_DURATION = Duration.ofSeconds(30); // Duración de validez del OTP

    // Almacena el OTP y su tiempo de expiración
    private final ConcurrentHashMap<String, Instant> otpStore = new ConcurrentHashMap<>();

    public Mono<OtpResponse> generateOtp() {
        return Mono.fromCallable(() -> {
            // Generar OTP
            int otp = (int) (Math.random() * 1000000); // OTP de 6 dígitos
            String otpString = String.format("%06d", otp); // Formato de 6 dígitos

            // Encriptar el OTP
            String encryptedOtp = encryptOtp(otpString);

            // Guardar OTP en la memoria con su tiempo de expiración
            otpStore.put(encryptedOtp, Instant.now().plus(OTP_DURATION));

            // Crear la respuesta
            return new OtpResponse(encryptedOtp, OTP_DURATION.getSeconds());
        });
    }

    private String encryptOtp(String otp) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(generateSecretKey().getEncoded(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedOtp = cipher.doFinal(otp.getBytes());
        return Base64.getEncoder().encodeToString(encryptedOtp); // Convertir a Base64 para enviar
    }

    private SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // 128 bits
        return keyGenerator.generateKey();
    }

    // Método para validar el OTP
    public Mono<Boolean> validateOtp(String encryptedOtp, String inputOtp) {
        Instant expirationTime = otpStore.get(encryptedOtp);
        if (expirationTime != null && Instant.now().isBefore(expirationTime)) {
            // Desencriptar para obtener el OTP original
            String decryptedOtp = decryptOtp(encryptedOtp);
            return Mono.just(decryptedOtp.equals(inputOtp));
        }
        return Mono.just(false); // OTP no válido o expirado
    }

    private String decryptOtp(String encryptedOtp) {
        // Implementa la lógica para desencriptar el OTP aquí
        // Deberás implementar la lógica de desencriptación similar a la encriptación
        return null; // Retorna el OTP desencriptado
    }

    public static class OtpResponse {
        private final String otp;
        private final long duration;

        public OtpResponse(String otp, long duration) {
            this.otp = otp;
            this.duration = duration;
        }

        public String getOtp() {
            return otp;
        }

        public long getDuration() {
            return duration;
        }
    }
}
