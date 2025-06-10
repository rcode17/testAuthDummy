# 📘 Prueba Técnica - Autenticación con DummyJSON

Esta API REST construida con Java y Spring Boot permite autenticar usuarios contra la API externa [DummyJSON](https://dummyjson.com), registrando los logins exitosos en una base de datos PostgreSQL.

## ✅ Tecnologías utilizadas

- ☕ Java 21  
- 🌱 Spring Boot 3.2.5  
- ☁️ Spring Cloud 2023.0.1  
- 🔗 OpenFeign  
- 🐘 PostgreSQL  
- 🧰 Maven

## 🚀 Instrucciones de ejecución

### 1. Clona el repositorio

```bash
git clone https://github.com/rcode17/testAuthDummy.git
cd testAuthDummy
```

### 2. Configura la conexión a la base de datos PostgreSQL

Nombre sugerido: `postgres`

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=      # Vacío si no tiene contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 3. Ejecuta la aplicación

```bash
./mvnw spring-boot:run
```
O desde tu IDE de preferencia (IntelliJ, VS Code, Spring Tool Suite, etc.)

## 👤 Usuario de prueba

```properties
username: emilys
password: emilyspass
```

## 🔄 Ejemplo CURL

```bash
curl -X POST http://localhost:8080/api/auth/login      -H "Content-Type: application/json"      -d '{"username": "emilys", "password": "emilyspass"}'
```

## 🔄 Respuesta API

```json
{
    "id": 1,
    "username": "emilys",
    "email": "emily.johnson@x.dummyjson.com",
    "firstName": "Emily",
    "lastName": "Johnson",
    "gender": "female",
    "image": "https://dummyjson.com/icon/emilys/128"
}
```

## 🧠 ¿Cómo se guarda el registro de login?

Después de una autenticación exitosa contra DummyJSON:

1. Se obtienen el token y refresh token del cuerpo de la respuesta.
2. Se realiza una llamada a `/auth/me` con la cookie recibida para obtener los datos del usuario autenticado.
3. Se construye un objeto `LoginLog` con:
   - `username` → usuario autenticado
   - `loginTime` → fecha y hora del login (`LocalDateTime.now()`)
   - `accessToken` → token de autenticación
   - `refreshToken` → refresh token recibido
4. Se persiste el registro en PostgreSQL a través de `LoginLogRepository` y JPA.

### 🗃️ Ejemplo de registro en base de datos:

```txt
 id | username |     login_time      |      access_token      |     refresh_token     
----+----------+---------------------+-------------------------+-----------------------
  1 | emilys   | 2025-06-10 12:45:00 | eyJhbGciOiJIUzI1Ni...   | eyJhbGciOiJIUzI1Ni...
```

## 🍪 Manejo de Cookies

Durante el proceso de login:

- DummyJSON responde con una cookie en el encabezado `Set-Cookie`.
- Esta cookie contiene el token de sesión.
- En el cliente Feign (`DummyClient`), se extrae con:

```java
String token = response.getHeaders().getFirst("Set-Cookie");
```

- Luego se reutiliza en la petición a `/auth/me`:

```java
dummyClient.getUserInfo(token);
```

Esto permite mantener una sesión autenticada como si se tratara de un flujo tradicional de cookies en navegador.
