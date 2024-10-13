package basic;

//import org.login.login.CheckLogIn;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//public class Application extends SpringBootServletInitializer implements CommandLineRunner {
@SpringBootApplication
public class Application{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
//    @Bean
//    public ServletRegistrationBean<CheckLogIn> servletRegistrationBean() {
//        return new ServletRegistrationBean<>(new CheckLogIn(), "/submit-login");
//    }
}