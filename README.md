# Post CRUD — Spring Boot + JPA + arquitectura por capas

Proyecto CRUD de la entidad `Post`.

## Tecnologías

- Java 25
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Data JPA (`JpaRepository`)
- PostgreSQL
- Jakarta Validation
- JUnit 5, Mockito y MockMvc
- Maven

## Arquitectura

```text
src/main/java/com/softer/
├── Application.java
└── post/
    ├── controller/
    │   └── PostController.java
    ├── service/
    │   └── PostService.java
    ├── repository/
    │   └── PostRepository.java
    ├── model/
    │   └── Post.java
    └── exception/
        └── PostNoFoundException.java
```

- **Controller:** expone los endpoints REST.
- **Service:** implementa las operaciones y reglas del CRUD.
- **Repository:** accede a los datos mediante `JpaRepository<Post, Integer>`.
- **Model:** entidad JPA `Post`.

## Configuración de base de datos

El proyecto conserva la base de datos PostgreSQL `posts` del original.

Crea la base de datos:

```sql
CREATE DATABASE posts;
```

Configura las variables de entorno antes de ejecutar la aplicación:

**Windows PowerShell**
```powershell
$env:DATABASE_USERNAME="tu_usuario"
$env:DATABASE_PASSWORD="tu_password"
```

**Linux/macOS**
```bash
export DATABASE_USERNAME=tu_usuario
export DATABASE_PASSWORD=tu_password
```

La aplicación ejecuta `schema.sql` y `data.sql` al iniciar.

## Endpoints

| Método | Endpoint | Descripción | Respuesta esperada |
|---|---|---|---|
| GET | `/api/posts` | Listar todos los posts | 200 OK |
| GET | `/api/posts/{id}` | Obtener un post | 200 OK / 404 Not Found |
| POST | `/api/posts` | Crear un post | 201 Created / 400 Bad Request |
| PUT | `/api/posts/{id}` | Actualizar título y contenido | 200 OK / 404 Not Found |
| DELETE | `/api/posts/{id}` | Eliminar un post | 204 No Content |

Ejemplo de JSON:

```json
{
  "id": 101,
  "userId": 1,
  "title": "Mi primer post",
  "body": "Contenido del post",
  "version": null
}
```

El identificador se envía en el cuerpo al crear. Al actualizar, se conserva el `userId` del registro existente y solo se actualizan `title` y `body`.

## Ejecutar pruebas

Desde la raíz del proyecto:

```bash
./mvnw test
```

En Windows:

```powershell
.\mvnw.cmd test
```

Se incluyen:
- Pruebas de capa web con `@WebMvcTest` y `MockMvc`.
- Pruebas unitarias de la capa de servicio con JUnit 5 y Mockito.

## Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```
