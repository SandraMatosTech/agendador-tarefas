package com.sandra.agendadortarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // Adicione este import

@SpringBootApplication
@EnableFeignClients // ADICIONE ESTA ANOTAÇÃO AQUI
public class AgendadorTarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendadorTarefasApplication.class, args);
	}

}