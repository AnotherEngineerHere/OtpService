# OTP Service Demo

Este proyecto es una demo de un servicio de generación y validación de códigos OTP (One-Time Password) en una aplicación **Spring Boot** utilizando **WebFlux** para manejo de flujos reactivos. 

El OTP generado se cifra utilizando el algoritmo AES y se almacena temporalmente para su validación posterior. El proyecto incluye pruebas unitarias utilizando **JUnit 5**, **Mockito** y **StepVerifier** para verificar el comportamiento del servicio.

## Requisitos

Para ejecutar el proyecto, necesitarás:

- Java 21
- Maven 3.x

## Dependencias principales

- **Spring Boot**: Framework utilizado para crear la aplicación web.
- **Spring WebFlux**: Para manejar de manera reactiva las solicitudes HTTP.
- **Java OTP**: Biblioteca para la generación y validación de códigos OTP.

## Instalación

1. Clona este repositorio:

    ```bash
    git clone https://github.com/your-username/otp-service-demo.git
    cd otp-service-demo
    ```

2. Instala las dependencias del proyecto utilizando Maven:

    ```bash
    mvn clean install
    ```

## Ejecución del proyecto

Para ejecutar la aplicación localmente, puedes usar el siguiente comando de Maven:

```bash
mvn spring-boot:run
```

Esto iniciará la aplicación en `http://localhost:8080`.

## Pruebas

El proyecto incluye un conjunto de pruebas unitarias para validar la generación y verificación de OTPs. Las pruebas están escritas utilizando **JUnit 5**, **Mockito** para los mocks, y **StepVerifier** para probar los flujos reactivos.

Para ejecutar las pruebas, usa:

```bash
mvn test
```

Esto ejecutará todas las pruebas unitarias, validando que el servicio OTP funcione correctamente.

## Arquitectura del Servicio OTP

El servicio OTP implementado en este proyecto funciona de la siguiente manera:

- **Generación de OTP**: 
    - Se genera un código OTP de 6 dígitos de forma aleatoria.
    - El OTP se cifra utilizando el algoritmo AES.
    - El OTP cifrado se almacena en un `ConcurrentHashMap` con una duración de 30 segundos.
  
- **Validación de OTP**: 
    - El código OTP cifrado se busca en el almacenamiento temporal.
    - Si el código OTP no ha expirado, se descifra y se compara con el OTP proporcionado por el usuario.
    - Si la validación es exitosa, se devuelve un `true`; de lo contrario, `false`.

### Clases Principales

- **OtpService**: Maneja la generación, cifrado y validación del OTP.
- **OtpResponse**: Clase de respuesta que contiene el OTP cifrado y la duración de su validez.

### Pruebas Unitarias

Las pruebas cubren los siguientes casos:

1. **Generación de OTP**: Verifica que el OTP sea generado y cifrado correctamente.
2. **Validación de OTP**: Prueba que el OTP se valide correctamente si es válido y no ha expirado.
3. **Expiración de OTP**: Prueba que el OTP no sea válido si ha expirado.

## Contribuciones

Si quieres contribuir al proyecto, puedes hacer un fork y crear un pull request con tus mejoras o correcciones. Asegúrate de que tus cambios pasen las pruebas antes de enviar el pull request.