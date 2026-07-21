//package com.iamkr23.bankapplication;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class BankapplicationApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(BankapplicationApplication.class, args);
//	}
//
//}

package com.iamkr23.bankapplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class BankapplicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankapplicationApplication.class, args);
    }
}