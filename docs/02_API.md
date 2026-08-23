# Resena — Contrato de la API REST

## Base

- **Base path**: `/api/resenas`
- **Formato**: JSON — **Puerto**: 8088 (configurable con `PORT`)

## Recursos

| Método | Ruta | Códigos de estado | Descripción |
|--------|------|-------------------|-------------|
| GET | `/api/resenas` | 200 | Lista todos los recursos |
| GET | `/api/resenas/{id}` | 200 / 404 | Obtiene un recurso por id |
| POST | `/api/resenas` | 201 / 400 | Crea un recurso |
| PUT | `/api/resenas/{id}` | 200 / 404 / 400 | Actualiza un recurso |
| DELETE | `/api/resenas/{id}` | 204 / 404 | Elimina un recurso |

## Atributos de un recurso

| Campo | Tipo | Obligatorio | Descripción |
|-------|------|-------------|-------------|
| id | Long | - | Identificador autogenerado |
| pedido | String | Sí | Campo principal del recurso |
| comentario | String | No | Campo del dominio |
| calificacion | BigDecimal | No | Campo del dominio |

## Ejemplos con curl

```bash
# Listar
curl http://localhost:8088/api/resenas

# Crear
curl -X POST http://localhost:8088/api/resenas \
  -H "Content-Type: application/json" \
  -d '{"pedido":"Demo"}'

# Obtener por id
curl http://localhost:8088/api/resenas/1

# Actualizar
curl -X PUT http://localhost:8088/api/resenas/1 \
  -H "Content-Type: application/json" \
  -d '{"pedido":"Actualizado"}'

# Eliminar
curl -X DELETE http://localhost:8088/api/resenas/1
```
