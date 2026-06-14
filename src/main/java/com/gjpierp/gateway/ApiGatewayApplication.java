/*
 @file ApiGatewayApplication.java
 @description Clase principal que inicializa el API Gateway con soporte reactivo de Spring Cloud
 @author Gerado Paiva
 @date 2026-06-04
 @version 1.0.0
 HISTORIAL DE CAMBIOS:
 -----------------------------------------------------------------------------
 FECHA        | AUTOR             | VERSIÓN   | DESCRIPCIÓN DEL CAMBIO
 -----------------------------------------------------------------------------
 2026-06-04   | Gerardo Paiva     | 1.0.0     | Creación inicial del archivo.
 */

package com.gjpierp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
