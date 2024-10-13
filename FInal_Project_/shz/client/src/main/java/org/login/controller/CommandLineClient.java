package org.login.controller;

import com.mysql.cj.log.Log;
import org.login.controller.PrintController;
import org.login.controller.basicServices;
import org.login.service.login.LogIn;
import org.login.service.register.Register;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Scanner;

@Component
public class CommandLineClient implements CommandLineRunner {
    private static RestTemplate restTemplate = new RestTemplate();
    private static Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        start();
    }
    public static void start(){
        while (true) {
            int action = PrintController.welcomePage();
            switch (action) {
                case 1:
                    Register.registerUser();
                    break;
                case 2:
                    if (LogIn.loginUser()){
                        basicServices.basicServicesSelection(LogIn.authToken);
                    }
                    break;
                case 3:
                    System.exit(0);;
                    break;
                default:
                    System.out.println("Invalid action. Please choose again.");
            }

        }
    }



}
