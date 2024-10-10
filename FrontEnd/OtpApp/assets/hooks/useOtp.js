// hooks/useOtp.js
import { useState, useEffect } from 'react';
import { generateOtpApi } from '../service/otpService';

const useOtp = () => {
    const [otp, setOtp] = useState('');
    const [duration, setDuration] = useState(0);
    const [timer, setTimer] = useState(null);
    const [error, setError] = useState(null);

    // Función para manejar la generación del OTP
    const handleGenerateOtp = async () => {
        try {
            const response = await generateOtpApi();
            if (response) {
                setOtp(response.otp); // Establecer el OTP
                setDuration(response.duration); // Establecer la duración
                startTimer(response.duration); // Iniciar el temporizador
            }
        } catch (error) {
            setError('Error al generar el OTP');
            console.error(error);
        }
    };

    // Iniciar el temporizador
    const startTimer = (initialDuration) => {
        setDuration(initialDuration);
        if (timer) clearInterval(timer);

        const newTimer = setInterval(() => {
            setDuration((prev) => {
                if (prev <= 1) {
                    clearInterval(newTimer);
                    return 0; // Tiempo expirado
                }
                return prev - 1;
            });
        }, 1000);
        setTimer(newTimer);
    };

    // Cleanup function para detener el temporizador
    useEffect(() => {
        return () => {
            if (timer) clearInterval(timer);
        };
    }, [timer]);

    useEffect(() => {
        if (duration === 0) {
            // Generar un nuevo OTP si el tiempo ha expirado
            handleGenerateOtp();
        }
    }, [duration]);

    return {
        otp,
        duration,
        error,
        handleGenerateOtp,
    };
};

export default useOtp;
