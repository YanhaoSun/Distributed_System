package org.login.service.deposit;

import org.login.service.account.Account;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import service.core.Ack;
import service.core.ClientInfo;
import org.login.controller.PrintController;
import org.login.frontend.service.Services;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Map;
import java.util.Scanner;

public class DepositMoney implements Services {
    private static RestTemplate restTemplate = new RestTemplate();
    private static final String API_BASE_URL = "http://localhost:";
    private static Scanner scanner = new Scanner(System.in);

    public static void depositMoney(ClientInfo user) {
        String message = "---Enter amount: ";
        double amount = PrintController.checkInputDouble(message);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("userID", String.valueOf(user.getUserId()));
        map.add("amount", String.valueOf(amount));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Ack> response = restTemplate.postForEntity(API_BASE_URL + "8081/deposit", request, Ack.class);
        Ack ack = response.getBody();
        Map<Double, Integer> balanceCredits = Account.getUserBalance(user);
        if (ack.getAck()) {
            PrintController.depositPage(true, balanceCredits);
        } else {
            PrintController.depositPage(false, balanceCredits);
        }
    }
}
