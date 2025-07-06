# SIMEL

Sistema de Incentivo y Motivación para Estudiantes de Lima.

## Tecnologías utilizadas

- Java
- NetBeans
- JSP, Servlets
- Bootstrap 4
- MySQL
- Arquitectura MVC (Modelo 2)
- Maven

## Descripción

El sistema **SIMEL** es una plataforma web diseñada para la gestión de estudiantes, docentes y administradores. El sistema aplica el patrón de arquitectura **Modelo-Vista-Controlador (MVC)** y está dividido en varios módulos de usuario:

- **Login de usuarios**: Autenticación para acceder al sistema.
- **Panel de administrador**: Gestión de usuarios y configuración general.
- **Panel de docente**: Gestión de estudiantes y notas.
- **Panel de alumno**: Visualización de sus resultados académicos.

Actualmente en desarrollo. El frontend está implementado con datos estáticos. Se trabaja en la integración de consultas dinámicas.

## Estructura principal

- `src/` — Código fuente (modelo, DAO, servlets, vistas)
- `pom.xml` — Configuración del proyecto Maven
- `.mvn/`, `mvnw`, `mvnw.cmd` — Maven Wrapper
- `.gitignore` — Exclusión de carpetas generadas

## Requisitos

- JDK 17 (o versión compatible)
- NetBeans 12+ o cualquier IDE con soporte Maven
- MySQL

## Ejecución del proyecto

1. **Clona el repositorio:**

   ```bash
   git clone https://github.com/tu-usuario/SIMEL.git
   ```

2. **Navega al directorio del proyecto:**

   ```bash
   cd SIMEL
   ```

3. **Instala las dependencias y construye el proyecto:**

   ```bash
   ./mvnw clean install
   ```

4. **Importa el proyecto en tu IDE (NetBeans recomendado)**

5. **Configura tu base de datos MySQL:**

   - Crea la base de datos `simel` en MySQL.
   - Restaura el respaldo `respaldo.sql` si es necesario:

   ```bash
   mysql -u usuario -p simel < respaldo.sql
   ```