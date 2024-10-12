import React from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';
import useOtp from '../hooks/useOtp'; // Asegúrate de que esta ruta es correcta

const OtpGenerator = () => {
    const { otp, duration, error, handleGenerateOtp } = useOtp();

    return (
        <View style={styles.container}>
            <Button title="Generar OTP" onPress={handleGenerateOtp} disabled={duration > 0} />
            {otp && (
                <Text style={styles.otpText}>OTP: {otp}</Text>
            )}
            {error && <Text style={styles.errorText}>{error}</Text>}
            <Text style={styles.timerText}>
                {duration > 0 ? `Tiempo restante: ${duration}s` : 'Tiempo expirado'}
            </Text>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    otpText: {
        fontSize: 48, // Ajusta el tamaño según sea necesario
        marginVertical: 20,
    },
    timerText: {
        fontSize: 18,
        marginVertical: 10,
    },
    errorText: {
        color: 'red',
        marginVertical: 10,
    },
});

export default OtpGenerator;
