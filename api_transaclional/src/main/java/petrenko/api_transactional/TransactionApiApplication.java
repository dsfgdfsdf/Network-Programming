package petrenko.api_transactional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "petrenko.api_transactional")
public class TransactionApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionApiApplication.class, args);
    }
}