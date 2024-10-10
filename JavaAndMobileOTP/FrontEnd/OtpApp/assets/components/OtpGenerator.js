// components/OtpGenerator.js
import React, { useEffect } from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';
import { Typography, CircularProgress } from '@mui/material';
import useOtp from '../hooks/useOtp'

const OtpGenerator = () => {
    const { otp, duration, error, handleGenerateOtp } = useOtp();

    useEffect(() => {
        // Genera un OTP cuando el componente se monta
        handleGenerateOtp();
    }, []);

    return (
        <View style={styles.container}>
            <Typography variant="h5" component="h2" style={styles.otpText}>
                OTP: {otp || 'Cargando...'}
            </Typography>
            {otp && (
                <Typography variant="h4" style={styles.otpNumber}>
                    {otp}
                </Typography>
            )}
            {error && <Text style={styles.errorText}>{error}</Text>}
            <Typography variant="body1" style={styles.timerText}>
                {duration > 0 ? `Tiempo restante: ${duration}s` : 'Tiempo expirado'}
            </Typography>
            <Button 
                variant="contained" 
                color="primary" 
                onClick={handleGenerateOtp} 
                disabled={duration > 0} // Deshabilitar el botón si el tiempo está corriendo
                style={styles.generateButton}
            >
                {duration > 0 ? 'Generar nuevo OTP' : 'Generando...'}
            </Button>
            {duration === 0 && <Button variant="contained" onClick={handleGenerateOtp}>Reintentar</Button>}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 16,
    },
    otpText: {
        marginVertical: 20,
        fontSize: 16,
    },
    otpNumber: {
        marginVertical: 20,
        fontSize: 48, // Aumentar el tamaño de la fuente del OTP
        fontWeight: 'bold',
    },
    timerText: {
        marginVertical: 10,
        fontSize: 18,
    },
    errorText: {
        color: 'red',
        marginVertical: 10,
    },
    generateButton: {
        marginVertical: 10,
    },
});

export default OtpGenerator;
