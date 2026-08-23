# Resena — Arquitectura del microservicio

## Componentes

| Componente | Rol |
|---|---|
| `ResenaApplication` | Punto de entrada Spring Boot |
| `controller/ResenaController.java` | API REST expuesta en `/api/resenas` |
| `service/ResenaService.java` | Lógica de negocio y transacciones |
| `repository/ResenaRepository.java` | Acceso JPA a H2 |
| `model/Resena.java` | Entidad persistida en tablas H2 |
| `config/OpenApiConfig.java` | Metadata OpenAPI del servicio |
| `static/index.html` | Página de presentación en `/` |
| `static/redoc.html` | Referencia ReDoc de la API |

## Principios aplicados

- **Responsabilidad Única (SRP)**: un dominio por servicio (Reseñas).
- **Bajo acoplamiento**: interactúa con otros microservicios por API/eventos, nunca por tablas compartidas.
- **Alta cohesión**: todas sus capacidades pertenecen al mismo dominio de negocio.
- **Disponibilidad/mantenibilidad local**: código, tests y despliegue independientes.

## Patrones de diseño (en el contexto del diagrama EP01)

En el diagrama general del caso FoodGo este microservicio participa de:

- **API Gateway**: única puerta de entrada a todos los microservicios.
- **Service Registry**: descubrimiento dinámico de instancias (AWS Cloud Map).
- **Circuit Breaker**: aislamiento ante la caída de dependencias externas.
- **Colas (AWS SQS)**: comunicación asíncrona de eventos entre servicios.
- **Funciones serverless (AWS Lambda)**: procesamiento eventual (pagos, notificaciones).

## Diagrama de la API (flujo)

`Cliente/Sistema → API Gateway → ResenaController → ResenaService → ResenaRepository → H2`
