package kz.jusan.hrapp;

import kz.jusan.hrapp.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtAppDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAppDemoApplication.class, args);
	}

}
