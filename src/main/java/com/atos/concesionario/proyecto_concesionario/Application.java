package com.atos.concesionario.proyecto_concesionario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void checkPassword() {
		String raw = "admin123";
		String stored = "$2a$10$rDkVPuF0kgNh8KHcfEPL0u2Vn3iAcPSP7iU3mg2AeiYph9JIlLQ8S";  // o

		boolean match = new BCryptPasswordEncoder().matches(raw, stored);
		System.out.println(" Comparación directa de hash en Java: " + match);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void generarHashDeJuan() {
		String raw = "1234";
		String hash = new BCryptPasswordEncoder().encode(raw);
		System.out.println("🔐 Hash válido para '1234': " + hash);
	}

}
