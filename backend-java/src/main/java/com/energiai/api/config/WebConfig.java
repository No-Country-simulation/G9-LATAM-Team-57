package com.energiai.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración del enrutado de la SPA (Angular).
 *
 * Al empaquetar Angular dentro de Spring Boot, las rutas de cliente (por ejemplo
 * {@code /analysis}) deben reenviarse a {@code index.html} para que el router de
 * Angular las resuelva. Las rutas de API no se ven afectadas: se mantienen en los
 * controladores REST correspondientes.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/analysis").setViewName("forward:/index.html");
    }
}
