package service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.out.println("test broker");
        SpringApplication.run(Application.class, args);
    }
}