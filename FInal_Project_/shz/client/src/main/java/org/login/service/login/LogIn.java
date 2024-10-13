package org.login.service.login;

import org.login.controller.CommandLineClient;
import org.login.controller.PrintController;
import org.login.frontend.service.Services;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import service.core.ClientInfo;

import java.util.HashMap;
import java.util.Map;

public class LogIn implements Services {
    private static RestTemplate restTemplate = new RestTemplate();
    private static final String API_BASE_URL = "http://localhost:8080";
    public static ClientInfo authToken = null;
    public static boolean logIn = false;
    public static boolean loginUser() {
        MultiValueMap<String, String> map = PrintController.logInPage();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<ClientInfo> response = restTemplate.postForEntity(API_BASE_URL + "/user/login", request, ClientInfo.class);
            if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                PrintController.logInStatusPage(true);
                logIn = true;
                authToken = response.getBody();
            } else {
                System.out.println("Received status code: " + response.getStatusCode());
                if (response.getBody() == null) {
                    System.out.println("Response body is null");
                }
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                PrintController.logInStatusPage(false);
            } else {
                System.out.println("HttpClientErrorException: " + e.getMessage());
            }
            logIn = false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            logIn = false;
        }
        return logIn;
    }
    public static void logOutUser(){
        authToken = null;
        logIn = false;
        PrintController.logOutPage();
    }

}
