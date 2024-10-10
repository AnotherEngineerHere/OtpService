// App.js
import React from 'react';
import { StyleSheet, View } from 'react-native';
import OtpGenerator from './assets/components/OtpGenerator';


export default function App() {
    return (
        <View style={styles.container}>
            <OtpGenerator/>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
});
