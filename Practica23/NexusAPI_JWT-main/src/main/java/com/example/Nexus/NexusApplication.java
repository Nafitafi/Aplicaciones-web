package com.example.Nexus;

import com.example.Nexus.security.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * ============================================================
 *  NexusApplication — Punto de entrada de la aplicación
 * ============================================================
 *
 * Esta es la clase principal de la API REST construida con
 * Spring Boot. Aquí es donde arranca todo el servidor.
 *
 * @SpringBootApplication es una anotación "todo en uno" que combina:
 *   - @Configuration       → marca esta clase como fuente de beans de Spring
 *   - @EnableAutoConfiguration → activa la configuración automática de Spring Boot
 *   - @ComponentScan       → escanea el paquete actual y sub-paquetes buscando
 *                            componentes (@Service, @Repository, @Controller, etc.)
 *
 * Cuando se ejecuta main(), Spring Boot levanta un servidor Tomcat
 * embebido (por defecto en el puerto 8080) y deja la API lista
 * para recibir peticiones HTTP.
 */
@SpringBootApplication
public class NexusApplication {

	/**
	 * Método principal de Java. Es el primer código que se ejecuta.
	 * SpringApplication.run(...) inicializa el contexto de Spring,
	 * configura la base de datos y levanta el servidor web.
	 *
	 * @param args argumentos de línea de comandos (no se usan en este proyecto)
	 */
	public static void main(String[] args) {
		SpringApplication.run(NexusApplication.class, args);
	}

	// ================================================================
	//  Registro del Filtro JWT
	// ================================================================

	/**
	 * Registra el JwtFilter como un filtro activo en el servidor web.
	 *
	 * ¿Por qué usamos FilterRegistrationBean?
	 * Cuando anotamos una clase con @Component (como JwtFilter), Spring
	 * la detecta como un bean, pero necesitamos decirle explícitamente
	 * que es un filtro HTTP y a qué rutas debe aplicarse.
	 * FilterRegistrationBean es la forma estándar de hacerlo en Spring Boot.
	 *
	 * addUrlPatterns("/api/*") → el filtro se aplica a TODAS las rutas
	 * que empiecen con /api/. La lógica de cuáles son públicas y cuáles
	 * requieren token está dentro del propio JwtFilter.
	 *
	 * @param jwtFilter Spring inyecta el JwtFilter automáticamente (es un @Component)
	 * @return el FilterRegistrationBean configurado
	 */
	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter) {
		FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(jwtFilter);    // el filtro a registrar
		registration.addUrlPatterns("/api/*"); // rutas donde se aplica
		return registration;
	}
}
