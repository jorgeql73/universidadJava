package com.challenge.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UniversityApplication {

	public static void main(String[] args) {
               /* String password = "123";
                String password2 = "leg123";
                System.out.println(encriptar(password));
                System.out.println(encriptar(password2));*/
                
                        
		SpringApplication.run(UniversityApplication.class, args);
	}
        public static String encriptar(String password){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(password);
        }
}
