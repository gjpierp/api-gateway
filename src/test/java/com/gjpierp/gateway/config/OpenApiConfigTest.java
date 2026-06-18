/*
 * @file OpenApiConfigTest.java
 * @description Pruebas unitarias para la configuración de agregación automática de OpenAPI
 * @author IA Assistant
 * @date 2026-06-17
 * @version 1.0.0
 * HISTORIAL DE CAMBIOS:
 * -----------------------------------------------------------------------------
 * FECHA        | AUTOR             | VERSIÓN   | DESCRIPCIÓN DEL CAMBIO
 * -----------------------------------------------------------------------------
 * 2026-06-17   | IA Assistant      | 1.0.0     | Creación inicial del archivo.
 */

package com.gjpierp.gateway.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import reactor.core.publisher.Flux;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OpenApiConfigTest {

    private RouteDefinitionLocator routeLocator;
    private SwaggerUiConfigProperties swaggerUiConfigProperties;
    private OpenApiConfig openApiConfig;

    @BeforeEach
    void setUp() {
        routeLocator = Mockito.mock(RouteDefinitionLocator.class);
        swaggerUiConfigProperties = Mockito.mock(SwaggerUiConfigProperties.class);
        openApiConfig = new OpenApiConfig(routeLocator, swaggerUiConfigProperties);
    }

    /**
     * Prueba el flujo exitoso cuando hay rutas válidas para descubrir servicios.
     */
    @Test
    void shouldRefreshSwaggerUrlsSuccessfully() {
        RouteDefinition definition1 = new RouteDefinition();
        definition1.setId("CompositeDiscoveryClient_USER-SERVICE");
        
        RouteDefinition definition2 = new RouteDefinition();
        definition2.setId("auth-service");
        
        when(routeLocator.getRouteDefinitions()).thenReturn(Flux.just(definition1, definition2));

        openApiConfig.refreshSwaggerUrls();

        ArgumentCaptor<Set<SwaggerUrl>> captor = ArgumentCaptor.forClass(Set.class);
        verify(swaggerUiConfigProperties).setUrls(captor.capture());

        Set<SwaggerUrl> urls = captor.getValue();
        assertEquals(2, urls.size());
        
        boolean hasUserService = urls.stream().anyMatch(url -> url.getName().equals("USER-SERVICE") && url.getUrl().equals("/user-service/v3/api-docs"));
        boolean hasAuthService = urls.stream().anyMatch(url -> url.getName().equals("AUTH") && url.getUrl().equals("/auth/v3/api-docs"));
        
        assertTrue(hasUserService);
        assertTrue(hasAuthService);
    }

    /**
     * Prueba que se excluyan los servicios del api-gateway y eureka-server.
     */
    @Test
    void shouldExcludeGatewayAndEurekaServices() {
        RouteDefinition definition1 = new RouteDefinition();
        definition1.setId("CompositeDiscoveryClient_API-GATEWAY");
        
        RouteDefinition definition2 = new RouteDefinition();
        definition2.setId("eureka-server");
        
        when(routeLocator.getRouteDefinitions()).thenReturn(Flux.just(definition1, definition2));

        openApiConfig.refreshSwaggerUrls();

        ArgumentCaptor<Set<SwaggerUrl>> captor = ArgumentCaptor.forClass(Set.class);
        verify(swaggerUiConfigProperties).setUrls(captor.capture());

        Set<SwaggerUrl> urls = captor.getValue();
        assertEquals(0, urls.size());
    }
}
