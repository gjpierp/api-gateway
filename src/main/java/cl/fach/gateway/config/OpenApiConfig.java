/*
 @file OpenApiConfig.java
 @description Configuración para la detección y agregación automática de definiciones de Swagger OpenAPI desde Eureka
 @author Gerado Paiva
 @date 2026-06-04
 @version 1.0.0
 HISTORIAL DE CAMBIOS:
 -----------------------------------------------------------------------------
 FECHA        | AUTOR             | VERSIÓN   | DESCRIPCIÓN DEL CAMBIO
 -----------------------------------------------------------------------------
 2026-06-04   | Gerardo Paiva     | 1.0.0     | Creación inicial del archivo.
 */

package cl.fach.gateway.config;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.LinkedHashSet;
import java.util.Set;

@Configuration
@EnableScheduling
public class OpenApiConfig {

    private final RouteDefinitionLocator routeLocator;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public OpenApiConfig(RouteDefinitionLocator routeLocator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.routeLocator = routeLocator;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @Scheduled(fixedDelay = 30000)
    public void refreshSwaggerUrls() {
        routeLocator.getRouteDefinitions().collectList().subscribe(definitions -> {
            Set<SwaggerUrl> urls = new LinkedHashSet<>();
            definitions.forEach(routeDefinition -> {
                String routeId = routeDefinition.getId();
                if (routeId.contains("CompositeDiscoveryClient_") || routeId.endsWith("-service")) {
                    String serviceName;
                    if (routeId.contains("CompositeDiscoveryClient_")) {
                        serviceName = routeId.substring(routeId.indexOf("CompositeDiscoveryClient_") + 25)
                                .toLowerCase();
                    } else {
                        serviceName = routeId.substring(0, routeId.indexOf("-service")).toLowerCase();
                    }
                    if (!serviceName.equals("api-gateway") && !serviceName.equals("eureka-server")) {
                        SwaggerUrl swaggerUrl = new SwaggerUrl();
                        swaggerUrl.setName(serviceName.toUpperCase());
                        swaggerUrl.setUrl("/" + serviceName + "/v3/api-docs");
                        urls.add(swaggerUrl);
                    }
                }
            });
            swaggerUiConfigProperties.setUrls(urls);
        });
    }
}
