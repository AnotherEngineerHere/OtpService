package com.example.demo.services;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class OtpEncryptor {

    private static final String SECRET_KEY = "yourSharedSecretKey"; // Cambia esto a una clave segura

    public String encrypt(String otp) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(generateSecretKey().getEncoded(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedOtp = cipher.doFinal(otp.getBytes());
        return Base64.getEncoder().encodeToString(encryptedOtp); // Convertir a Base64 para enviar
    }

    public String decrypt(String encryptedOtp) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(generateSecretKey().getEncoded(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedOtp = Base64.getDecoder().decode(encryptedOtp);
        return new String(cipher.doFinal(decodedOtp));
    }

    private SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // 128 bits
        return keyGenerator.generateKey();
    }
}
