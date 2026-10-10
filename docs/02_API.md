# API REST: resenas

Base local: http://localhost:8088/api. Swagger UI: http://localhost:8088/swagger-ui/index.html.

| Método | Ruta | HTTP de éxito |
|---|---|---:|
| POST | /resenas | 201 |
| GET | /resenas | 200 |
| GET | /resenas/{id} | 200 |
| PUT | /resenas/{id} | 200 |
| DELETE | /resenas/{id} | 204 |
| POST | /resenas/{id}/respuestas | 201 |
| GET | /resenas/{id}/respuestas | 200 |
| GET | /respuestas/{id} | 200 |
| PUT | /respuestas/{id} | 200 |
| DELETE | /respuestas/{id} | 204 |

## Crear entidad principal

```json
{
  "pedido": "PED-DEMO",
  "comentario": "Las hamburguesas llegaron calientes y el repartidor encontró la dirección sin problemas.",
  "calificacion": 5
}
```

## Crear entidad relacionada

```json
{
  "autor": "La Cocina de Barrio",
  "mensaje": "Gracias, Camila. Nos alegra que disfrutaras las hamburguesas.",
  "fechaHora": "2026-10-01T13:30:00"
}
```

Usar el ID retornado por la creación del padre. Los ID son generados por la BD. Editar los hijos mediante sus propias rutas. Ver las reglas y los campos calculados en REGLAS_EP02.md.

Errores: 400 para datos o JSON inválidos; 404 para recurso/relación local inexistente; 409 para conflictos de integridad o unicidad cuando corresponda. Un campo demasiado largo devuelve 400. Los mensajes y validationErrors se entregan mediante ApiExceptionHandler.
