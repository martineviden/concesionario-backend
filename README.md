# 🚗 Concesionario Backend - Spring Boot API

## 📋 Descripción del Proyecto

Sistema backend completo para la gestión de un concesionario de vehículos desarrollado con Spring Boot. El sistema permite gestionar vehículos, tipos de vehículos, usuarios, reservas y reseñas con un sistema de autenticación JWT robusto y una arquitectura RESTful bien estructurada.

## 🛠️ Tecnologías Utilizadas

### Backend Framework
- **Spring Boot 3.4.5** - Framework principal
- **Java 17** - Lenguaje de programación
- **Maven** - Gestión de dependencias

### Base de Datos
- **MySQL 8** - Base de datos relacional
- **Spring Data JPA** - ORM para persistencia
- **Hibernate** - Implementación JPA

### Seguridad y Autenticación
- **Spring Security 6** - Framework de seguridad
- **JWT (JSON Web Tokens)** - Autenticación stateless
- **BCrypt** - Encriptación de contraseñas

### Herramientas de Desarrollo
- **Lombok** - Reducción de código boilerplate
- **Spring Boot DevTools** - Desarrollo en caliente
- **Spring Boot Validation** - Validaciones de entrada

### Testing
- **JUnit 5** - Framework de testing
- **Mockito** - Mocking para pruebas unitarias
- **Spring Boot Test** - Testing integrado

## 🏗️ Arquitectura del Proyecto

### Estructura de Capas

```
src/main/java/com/atos/concesionario/proyecto_concesionario/
├── Application.java                    # Clase principal
├── Config/
│   └── SeguridadConfig.java           # Configuración de seguridad
├── Controller/                        # Capa de controladores REST
│   ├── AuthControlador.java
│   ├── ResenaControlador.java
│   ├── ReservaControlador.java
│   ├── TipoVehiculoControlador.java
│   ├── UsuarioControlador.java
│   └── VehiculoControlador.java
├── Exception/                         # Manejo de excepciones
│   └── ResourceNotFoundException.java
├── Jwt/                              # Utilidades JWT
│   └── JwtUtils.java
├── Model/                            # Entidades JPA
│   ├── Resena.java
│   ├── Reserva.java
│   ├── TipoVehiculo.java
│   ├── Usuario.java
│   └── Vehiculo.java
├── Repository/                       # Capa de acceso a datos
│   ├── ResenaRepositorio.java
│   ├── ReservaRepositorio.java
│   ├── TipoVehiculoRepositorio.java
│   ├── UsuarioRepositorio.java
│   └── VehiculoRepositorio.java
├── Security/                         # Componentes de seguridad
│   ├── CustomUserDetailsService.java
│   └── JwtAuthorizationFilter.java
└── Service/                          # Lógica de negocio
    ├── ResenaServicio.java
    ├── ReservaServicio.java
    ├── TipoVehiculoServicio.java
    ├── UsuarioServicio.java
    └── VehiculoServicio.java
```

## 📊 Modelo de Datos

### Entidades Principales

#### Usuario
- **Campos**: ID, nombre, apellidos, email, contraseña, teléfono, rol (CLIENTE/ADMIN)
- **Relaciones**: OneToMany con Reserva y Reseña

#### Vehiculo
- **Campos**: Matrícula (PK), tipo vehículo, color, kilometraje, disponibilidad, combustible, etiqueta ambiental, autonomía, puertas, aire acondicionado, plazas, transmisión, ubicación
- **Enums**: Combustible, Transmisión, EtiquetaAmbiental, Provincia
- **Relaciones**: ManyToOne con TipoVehiculo, OneToMany con Reserva y Reseña

#### TipoVehiculo
- **Campos**: ID, nombre, tipo, precio
- **Enums**: Tipo (COUPE, SEDAN, SUV, HATCHBACK, CONVERTIBLE, MONOVOLUMEN)
- **Relaciones**: OneToMany con Vehiculo

#### Reserva
- **Campos**: ID, vehículo, usuario, fecha reserva, días reserva, precio
- **Relaciones**: ManyToOne con Usuario y Vehiculo, OneToOne con Reseña

#### Reseña
- **Campos**: ID, comentario, puntuación, fecha, usuario, vehículo, reserva
- **Relaciones**: ManyToOne con Usuario y Vehiculo, OneToOne con Reserva

## 🔌 API Endpoints

### Autenticación
- `POST /auth/login` - Autenticación de usuario

### Usuarios
- `GET /usuarios` - Obtener todos los usuarios
- `GET /usuarios/{id}` - Obtener usuario por ID
- `POST /usuarios` - Crear nuevo usuario
- `PUT /usuarios/{id}` - Actualizar usuario
- `DELETE /usuarios/{id}` - Eliminar usuario

### Vehículos
- `GET /vehiculos` - Obtener todos los vehículos
- `GET /vehiculos/{matricula}` - Obtener vehículo por matrícula
- `GET /vehiculos/ubicacion/{ubicacion}` - Obtener vehículos por ubicación
- `GET /vehiculos/buscar?tipo={tipo}&ubicacion={ubicacion}` - Búsqueda avanzada
- `POST /vehiculos` - Crear nuevo vehículo
- `PUT /vehiculos/{matricula}` - Actualizar vehículo
- `DELETE /vehiculos/{matricula}` - Eliminar vehículo

### Tipos de Vehículo
- `GET /tipos-vehiculo` - Obtener todos los tipos
- `GET /tipos-vehiculo/{id}` - Obtener tipo por ID
- `POST /tipos-vehiculo` - Crear nuevo tipo
- `PUT /tipos-vehiculo/{id}` - Actualizar tipo
- `DELETE /tipos-vehiculo/{id}` - Eliminar tipo

### Reservas
- `GET /reservas` - Obtener todas las reservas
- `GET /reservas/{id}` - Obtener reserva por ID
- `GET /reservas/usuario/{idUsuario}` - Obtener reservas por usuario
- `POST /reservas` - Crear nueva reserva
- `PUT /reservas/{id}` - Actualizar reserva
- `DELETE /reservas/{id}` - Eliminar reserva
- `DELETE /reservas/matricula/{matricula}` - Eliminar reservas por matrícula

### Reseñas
- `GET /resenas` - Obtener todas las reseñas
- `GET /resenas/{id}` - Obtener reseña por ID
- `GET /resenas/matricula/{matricula}` - Obtener reseñas por matrícula
- `POST /resenas` - Crear nueva reseña
- `PUT /resenas/{id}` - Actualizar reseña
- `DELETE /resenas/{id}` - Eliminar reseña
- `DELETE /resenas/matricula/{matricula}` - Eliminar reseñas por matrícula

## 🔐 Seguridad

### Sistema de Autenticación JWT
- Tokens JWT con expiración de 24 horas
- Encriptación BCrypt para contraseñas
- Filtros de autorización personalizados
- Roles de usuario: CLIENTE y ADMIN

### CORS Configuration
- Configurado para frontend Angular en `localhost:4200`
- Métodos permitidos: GET, POST, PUT, DELETE, OPTIONS
- Headers permitidos: Authorization, Content-Type

## ⚙️ Configuración

### Base de Datos (application.properties)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/concesionario_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Europe/Madrid
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Servidor
```properties
server.port=8080
```

## 🚀 Instalación y Ejecución

### Requisitos Previos
- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone [URL_DEL_REPOSITORIO]
cd concesionario-backend
```

2. **Configurar la base de datos**
```sql
CREATE DATABASE concesionario_db;
```

3. **Configurar credenciales** (application.properties)
```properties
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

4. **Instalar dependencias**
```bash
mvn clean install
```

5. **Ejecutar la aplicación**
```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 🧪 Testing

### Ejecutar Pruebas
```bash
mvn test
```

### Cobertura de Pruebas
- **Pruebas Unitarias**: Servicios y repositorios
- **Pruebas de Integración**: Controladores REST
- **Mocking**: Dependencias externas con Mockito

### Estructura de Tests
```
src/test/java/com/atos/concesionario/proyecto_concesionario/
├── Controller/                        # Tests de controladores
├── Service/                          # Tests de servicios
├── Config/                           # Configuración de tests
└── ApplicationTests.java             # Test de contexto
```

## 📝 Características Destacadas

### Funcionalidades Implementadas
- ✅ CRUD completo para todas las entidades
- ✅ Autenticación y autorización JWT
- ✅ Búsqueda avanzada de vehículos
- ✅ Gestión de roles (CLIENTE/ADMIN)
- ✅ Validaciones de entrada
- ✅ Manejo de excepciones personalizado
- ✅ Operaciones transaccionales
- ✅ Tests unitarios e integración
- ✅ Configuración CORS para frontend
- ✅ Documentación completa de API

### Características Técnicas
- Arquitectura en capas bien definida
- Patrón Repository para acceso a datos
- DTOs implícitos con Lombok
- Manejo de relaciones JPA bidireccionales
- Configuración de seguridad robusta
- Logging y monitoreo integrado

## 🔧 Configuración de Desarrollo

### Variables de Entorno
```bash
# Base de datos
DB_URL=jdbc:mysql://localhost:3306/concesionario_db
DB_USERNAME=root
DB_PASSWORD=

# JWT
JWT_SECRET=clave-secreta-super-segura-para-firmar-jwt12345
JWT_EXPIRATION=86400000

# Servidor
SERVER_PORT=8080
```

### Perfiles de Spring
- `dev` - Desarrollo local
- `test` - Pruebas automatizadas
- `prod` - Producción

## 📚 Dependencias Principales

```xml
<!-- Spring Boot Starters -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- Base de datos -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>

<!-- Utilidades -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Añadir nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para más detalles.

## 👥 Autores y Contribuyentes

### Desarrolladores Principales
- **camilo1102**
- **martineviden**
- **SofiaMendezArroyo**
- **ricardogpeviden**
- **guillermo121**

### Contribuciones
Este proyecto fue desarrollado colaborativamente por el equipo de estudiantes del módulo de Entorno Servidor.

## 📞 Soporte

Para soporte técnico o consultas:
- Email: [tu-email@example.com]
- Issues: [URL del repositorio]/issues

---

**Nota**: Este proyecto fue desarrollado como parte del módulo de Entorno Servidor en el ciclo de Desarrollo de Aplicaciones Web (DAW).
