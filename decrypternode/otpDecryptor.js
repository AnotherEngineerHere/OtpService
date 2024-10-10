const express = require('express');
const bodyParser = require('body-parser');
const crypto = require('crypto');

const app = express();
const PORT = 3404;

// Clave y algoritmo para la encriptación
const ENCRYPTION_KEY = '12345678901234567890123456789012'; // Debe ser de 32 bytes
const IV = '1234567890123456'; // IV de 16 bytes
const ALGORITHM = 'aes-256-cbc';

// Middleware para parsear el cuerpo de las peticiones
app.use(bodyParser.json());

// Función para desencriptar el OTP
const decryptOtp = (encryptedOtp) => {
    const encryptedText = Buffer.from(encryptedOtp, 'base64'); // Texto encriptado en Base64
    const decipher = crypto.createDecipheriv(ALGORITHM, Buffer.from(ENCRYPTION_KEY, 'utf8'), Buffer.from(IV, 'utf8'));
    let decrypted = decipher.update(encryptedText, 'binary', 'utf8');
    decrypted += decipher.final('utf8');
    return decrypted;
};

// Ruta para desencriptar el OTP
app.post('/api/decrypt', (req, res) => {
    const { encryptedOtp } = req.body;

    if (!encryptedOtp) {
        return res.status(400).json({ error: 'El OTP encriptado es requerido' });
    }

    try {
        const decryptedOtp = decryptOtp(encryptedOtp);
        res.json({ decryptedOtp });
    } catch (error) {
        console.log(error);
        res.status(500).json({ error: 'Error al desencriptar el OTP' });
    }
});

app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
