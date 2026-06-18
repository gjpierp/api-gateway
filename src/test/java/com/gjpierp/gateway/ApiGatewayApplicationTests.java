/*
 * @file ApiGatewayApplicationTests.java
 * @description Clase de prueba para verificar la carga del contexto de Spring Boot en el API Gateway
 * @author IA Assistant
 * @date 2026-06-17
 * @version 1.0.0
 * HISTORIAL DE CAMBIOS:
 * -----------------------------------------------------------------------------
 * FECHA        | AUTOR             | VERSIÓN   | DESCRIPCIÓN DEL CAMBIO
 * -----------------------------------------------------------------------------
 * 2026-06-17   | IA Assistant      | 1.0.0     | Creación inicial del archivo.
 */

package com.gjpierp.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiGatewayApplicationTests {

    /**
     * Prueba que el contexto de Spring cargue correctamente sin errores.
     */
    @Test
    void contextLoads() {
        // La prueba pasa si la aplicación se levanta correctamente
    }
}
