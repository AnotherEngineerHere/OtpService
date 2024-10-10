// services/otpService.js
import axios from 'axios';
import CryptoJS from 'crypto-js';

const SECRET_KEY = 'yourSharedSecretKey'; // Clave secreta compartida (inseguro en el frontend)

/**
 * Genera un OTP encriptado a través de la API.
 * @returns {Promise<{ otp: string, duration: number }>} El OTP encriptado y la duración.
 */
export const generateOtpApi = async () => {
    try {
        const response = await axios.get('http://localhost:9300/api/otp/generate');
        return {
            otp: response.data.otp.otp,
            duration: response.data.otp.duration,
        }; // Asegúrate de que la respuesta tenga el formato correcto
    } catch (error) {
        console.error('Error generating OTP:', error);
        return null; // Manejo de error simple
    }
};

/**
 * Desencripta el OTP encriptado.
 * @param {string} encryptedOtp - El OTP encriptado.
 * @returns {string} El OTP desencriptado.
 */
export const decryptOtp = (encryptedOtp) => {
    try {
        const bytes = CryptoJS.AES.decrypt(encryptedOtp, SECRET_KEY);
        const decryptedOtp = bytes.toString(CryptoJS.enc.Utf8);
        return decryptedOtp;
    } catch (error) {
        console.error('Error decrypting OTP:', error);
        return null; // Manejo de error simple
    }
};
