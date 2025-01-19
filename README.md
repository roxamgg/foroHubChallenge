# ForoHub - Proyecto de foro en línea

## Descripción

ForoHub es una aplicación de foro en línea que permite a los usuarios interactuar, hacer preguntas, responder temas y participar en discusiones. Los usuarios pueden registrar un tópico, responder a temas existentes y visualizar las respuestas de otros usuarios. El sistema permite la administración de tópicos y respuestas, con control sobre el estado de los mismos.

### Funcionalidades

- **Creación de tópicos**: Los usuarios pueden registrar tópicos en los que otros usuarios pueden responder.
- **Respuestas**: Los usuarios pueden responder a los tópicos creados por otros usuarios.
- **JWT autenticación**: Utiliza JWT para autenticar a los usuarios y proteger las rutas de la API.
- **Estado de respuestas y tópicos**: Los tópicos y respuestas pueden estar en diferentes estados (activo, resuelto, inactivo).

## Tecnologías utilizadas

- **Java 21**: Lenguaje de programación.
- **Spring Boot**: Framework para desarrollar la aplicación.
- **Maven**: Para la construcción del proyecto.
- **Spring Data JPA**: Para interactuar con la base de datos.
- **MySQL**: Sistema de gestión de bases de datos relacional.
- **Flyway**: Herramienta para la gestión de migraciones de la base de datos.
- **JWT**: Para la autenticación y autorización de usuarios.
- **Lombok**: Para generar código repetitivo de forma automática (como getters, setters, constructores, etc.).

## Estructura del proyecto

El proyecto sigue una arquitectura basada en capas y módulos:

- **Domain Layer**: Contiene las entidades principales como `Usuario`, `Topico`, `Respuesta`, y sus correspondientes repositorios.
- **Business Layer (BL)**: Contiene la lógica de negocio para manejar operaciones sobre las entidades.
- **DTOs**: Data Transfer Objects utilizados para recibir y enviar información desde la API.
- **API Layer**: Los controladores (`RestController`) manejan las peticiones HTTP.
- **Security Layer**: Contiene la lógica de seguridad del aplicativo para la autenticación y autorización de usuarios.
- **Tratamiento de errores**: Contiene la lógica para los mensajes en caso de error.
- **SWAGGER**: Contiene la documentación automática del aplicativo. Formato amigable.

## Base de Datos

El sistema utiliza una base de datos MySQL.

### Tablas

1. **`usuarios`**
2. **`topicos`**
3. **`respuestas`**  

## Configuración

### Variables de Entorno

- **DATASOURCE_USERNAME**: Usuario de la base de datos.
- **DATASOURCE_PASSWORD**: Contraseña de la base de datos.
- **JWT_KEY**: Clave secreta para la firma de los tokens JWT.

Asegúrate de reemplazar tu_username, tu_password y tu_JWTKEY con las variables de entorno correspondientes a la configuración de MySQL.

