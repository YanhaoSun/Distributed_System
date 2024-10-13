package org.login.service.register;

import org.login.controller.PrintController;
import org.login.frontend.service.Services;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import service.core.Ack;
import service.core.ClientInfo;

public class Register implements Services {
    private static RestTemplate restTemplate = new RestTemplate();
    private static final String API_BASE_URL = "http://localhost:";
    public static void registerUser() {
        ClientInfo newUser = PrintController.registerPage();
//        System.out.println("user_name = "+newUser.name);
        try {
            ResponseEntity<ClientInfo> response = restTemplate.postForEntity(API_BASE_URL + "8080/user/register", newUser, ClientInfo.class);
//            System.out.println("newUser.getUserId() = "+response.getBody().getUserId());
            if(response.getStatusCode().is2xxSuccessful() && response.getStatusCode() == HttpStatus.CREATED) {
//                ClientInfo info = new ClientInfo(newUser.getUserId(), newUser.name, newUser.getAge());
//                System.out.println("Registered Successfully");
                ClientInfo info = new ClientInfo(response.getBody().getUserId(), response.getBody().name, response.getBody().getAge());
                MultiValueMap<String, ClientInfo> map = new LinkedMultiValueMap<>();
                map.add("userID", info);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String, ClientInfo>> request = new HttpEntity<>(map, headers);
                ResponseEntity<Ack> responseRegister = restTemplate.postForEntity(API_BASE_URL+"8081/register", info, Ack.class);
                if (responseRegister.getBody().getAck()){
                    PrintController.registerStatusPage(true);
                } else {
                    System.out.println("Register Failed");
                }
            } else {
                System.out.println("Received status code: " + response.getStatusCode());
                if (response.getBody() == null) {
                    System.out.println("Response body is null");
                }
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                PrintController.registerStatusPage(false);
            } else {
                System.out.println("HttpClientErrorException: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

}
