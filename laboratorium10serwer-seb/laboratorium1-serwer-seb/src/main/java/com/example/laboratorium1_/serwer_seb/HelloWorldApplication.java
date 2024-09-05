package com.example.laboratorium1_.serwer_seb;

// Імпортуємо необхідні класи з пакету Spring
import com.example.laboratorium1_.serwer_seb.demo.Rectangle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Означаємо, що цей клас є головним класом Spring Boot додатку без автоматичного конф бази даних
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HelloWorldApplication {

	public static void main(String[] args) {
		// Запускаємо Spring Boot додаток
		SpringApplication.run(HelloWorldApplication.class, args);

	}
}


// Створюємо контролер для обробки HTTP-запитів
@RestController
@RequestMapping("/")
class HelloWorldController{

	// Створюємо метод для обробки GET-запитів на кореневий URL
	@GetMapping
	public String sayHello(){

		// Повертаємо "Hello World" як відповідь
		return "Hello world";
	}
}

