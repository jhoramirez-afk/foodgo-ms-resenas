# language: es
Característica: Servicio Resena (microservicio resenas del caso FoodGo)
  Los escenarios validan el contrato REST del microservicio alineado a sus endpoints.

  Escenario: el listado del recurso responde 200
    Dado el servicio "Resena" está disponible
    Cuando consulto el listado de "resenas"
    Entonces el listado responde con código 200

  Escenario: ciclo de vida completo del recurso
    Dado un nuevo "resena" con pedido "hola-cucumber"
    Cuando consulto el "resena" recién creado
    Entonces el recurso tiene pedido "hola-cucumber" y código 200
    Cuando actualizo el "resena" con pedido "cucumber-actualizado"
    Entonces el recurso queda con pedido "cucumber-actualizado" y código 200
    Cuando elimino el "resena"
    Entonces la eliminación responde con código 204
    Y al consultar el "resena" eliminado responde 404
