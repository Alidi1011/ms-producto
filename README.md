# Producto Service

Microservicio reactivo con Spring WebFlux + R2DBC + H2.

La capa HTTP utiliza `ProductRequestDto` para las solicitudes y `ProductResponseDto`
para las respuestas. `Product` queda reservado para el modelo persistido; `ProductMapper`
se encarga de convertir entre los DTOs y el modelo. El controlador depende del contrato
`ProductService`, implementado por `ProductServiceImpl`.

## Ejecutar con Docker
./mvnw clean package -DskipTests
docker compose up --build

## Ejecutar tests y cobertura
./mvnw test
Reporte en: target/site/jacoco/index.html

## Documentación API
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Swagger UI: http://localhost:8080/swagger-ui.html

## Endpoints
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/productos` | Listar productos activos |
| GET | `/api/productos/{id}` | Obtener un producto por ID |
| POST | `/api/productos` | Crear un producto |
| PUT | `/api/productos/{id}` | Actualizar un producto |
| DELETE | `/api/productos/{id}` | Eliminar un producto |

## Ejemplos cURL

```bash
# Listar productos activos
curl http://localhost:8080/api/productos

# Obtener un producto por ID
curl http://localhost:8080/api/productos/1

# Crear un producto
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"SOAT","descripcion":"Cobertura","precio":120.0,"stock":10,"activo":true}'

# Actualizar un producto por ID
curl -X PUT http://localhost:8080/api/productos/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"SOAT","descripcion":"Cobertura actualizada","precio":130.0,"stock":8,"activo":true}'

# Eliminar un producto por ID
curl -X DELETE http://localhost:8080/api/productos/1
```
