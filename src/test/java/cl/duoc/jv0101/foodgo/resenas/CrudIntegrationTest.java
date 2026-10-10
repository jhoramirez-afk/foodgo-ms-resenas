package cl.duoc.jv0101.foodgo.resenas;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CrudIntegrationTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    private String unique(String json) { return json.replace("TEST20261009", UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase()); }

    private long createParent() throws Exception {
        String result = mvc.perform(post("/api/resenas").contentType("application/json")
                .content(unique("""
{"pedido":"PED-TEST20261009","comentario":"Las hamburguesas llegaron calientes y el repartidor encontró la dirección sin problemas.","calificacion":5}
"""))).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return mapper.readTree(result).get("id").asLong();
    }

    private long createChild(String nested) throws Exception {
        String result = mvc.perform(post(nested).contentType("application/json")
                .content("""
{"autor":"La Cocina de Barrio","mensaje":"Gracias, Camila. Nos alegra que disfrutaras las hamburguesas.","fechaHora":"2026-10-01T13:30:00"}
""")).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return mapper.readTree(result).get("id").asLong();
    }

    @Test
    void crudRelationsAndCascadeThroughHttp() throws Exception {
        long id = createParent();
        String nested = "/api/resenas/" + id + "/respuestas";
        long childId = createChild(nested);
        mvc.perform(get("/api/resenas/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.respuestas[0].id").value(childId));
        mvc.perform(get("/api/resenas")).andExpect(status().isOk());
        mvc.perform(get(nested)).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(childId));
        mvc.perform(get("/api/respuestas/" + childId)).andExpect(status().isOk());
        mvc.perform(put("/api/resenas/" + id).contentType("application/json").content(unique("""
{"pedido":"PED-TEST20261009","comentario":"Buena comida y entrega puntual. Sugiero incluir más servilletas.","calificacion":4}
"""))).andExpect(status().isOk());
        mvc.perform(put("/api/respuestas/" + childId).contentType("application/json").content("""
{"autor":"La Cocina de Barrio","mensaje":"Gracias por la sugerencia, Camila. Revisaremos la cantidad de servilletas.","fechaHora":"2026-10-01T13:35:00"}
""")).andExpect(status().isOk());
        mvc.perform(delete("/api/respuestas/" + childId)).andExpect(status().isNoContent());
        mvc.perform(get("/api/respuestas/" + childId)).andExpect(status().isNotFound());
        long cascadeId = createChild(nested);
        mvc.perform(delete("/api/resenas/" + id)).andExpect(status().isNoContent());
        mvc.perform(get("/api/resenas/" + id)).andExpect(status().isNotFound());
        mvc.perform(get("/api/respuestas/" + cascadeId)).andExpect(status().isNotFound());
    }

    @Test
    void malformedJsonAndIdsReturnStructuredErrors() throws Exception {
        mvc.perform(post("/api/resenas").contentType("application/json").content("{"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value(400));
        mvc.perform(get("/api/resenas/no-es-numero"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void validationAndMissingResourcesReturnStructuredErrors() throws Exception {
        mvc.perform(post("/api/resenas").contentType("application/json").content("{}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.validationErrors").isNotEmpty());
        mvc.perform(get("/api/resenas/9223372036854775807"))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.status").value(404));
        mvc.perform(post("/api/resenas/9223372036854775807/respuestas").contentType("application/json")
                .content("""
{"autor":"La Cocina de Barrio","mensaje":"Gracias, Camila. Nos alegra que disfrutaras las hamburguesas.","fechaHora":"2026-10-01T13:30:00"}
""")).andExpect(status().isNotFound());
        var longBody = (com.fasterxml.jackson.databind.node.ObjectNode) mapper.readTree("""
{"pedido":"PED-TEST20261009","comentario":"Las hamburguesas llegaron calientes y el repartidor encontró la dirección sin problemas.","calificacion":5}
""");
        longBody.put("pedido", "X".repeat(300));
        mvc.perform(post("/api/resenas").contentType("application/json").content(longBody.toString())).andExpect(status().isBadRequest());
    }

    static Stream<Arguments> invalidInputs() {
        return Stream.of(
            Arguments.of("Calificación cero", "parent", """
{"pedido":"PED-TEST20261009","comentario":"Las hamburguesas llegaron calientes y el repartidor encontró la dirección sin problemas.","calificacion":0}
""", "calificacion"),
            Arguments.of("Calificación superior a cinco", "parent", """
{"pedido":"PED-TEST20261009","comentario":"Las hamburguesas llegaron calientes y el repartidor encontró la dirección sin problemas.","calificacion":6}
""", "calificacion"),
            Arguments.of("Calificación fraccionaria", "parent", """
{"pedido":"PED-TEST20261009","comentario":"Las hamburguesas llegaron calientes y el repartidor encontró la dirección sin problemas.","calificacion":4.5}
""", "calificacion"),
            Arguments.of("Calificación obligatoria", "parent", """
{"pedido":"PED-TEST20261009","comentario":"Las hamburguesas llegaron calientes y el repartidor encontró la dirección sin problemas.","calificacion":null}
""", "calificacion"),
            Arguments.of("Comentario obligatorio", "parent", """
{"pedido":"PED-TEST20261009","comentario":"","calificacion":5}
""", "comentario"),
            Arguments.of("Respuesta obligatoria", "child", """
{"autor":"La Cocina de Barrio","mensaje":"","fechaHora":"2026-10-01T13:30:00"}
""", "mensaje")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidInputs")
    void businessValidationReturns400WithField(String name, String target, String body, String field) throws Exception {
        if ("parent".equals(target)) {
            mvc.perform(post("/api/resenas").contentType("application/json").content(unique(body)))
                    .andExpect(status().isBadRequest()).andExpect(jsonPath("$.validationErrors." + field).exists());
        } else {
            long id = createParent();
            mvc.perform(post("/api/resenas/" + id + "/respuestas").contentType("application/json").content(body))
                    .andExpect(status().isBadRequest()).andExpect(jsonPath("$.validationErrors." + field).exists());
            mvc.perform(delete("/api/resenas/" + id)).andExpect(status().isNoContent());
        }
    }

}
