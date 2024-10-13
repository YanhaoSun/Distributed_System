//package basic.controller;
//
//import basic.user.User;
//import service.core.Ack;
//import service.core.ClientInfo;
//import basic.transaction.Transaction;
//import basic.finance.Finance;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import java.util.Scanner;
//
//import java.util.List;
//
//@Component
//public class test implements CommandLineRunner{
//    private static final String API_BASE_URL = "http://localhost:8081";
//    private static RestTemplate restTemplate = new RestTemplate();
//    private static Scanner scanner = new Scanner(System.in);
//
//    @Override
//    public void run(String... args) throws Exception {
//        while (true) {
//            System.out.println("1: Deposit");
//            System.out.println("2: Withdraw");
//            System.out.println("3: Check Balance");
//            System.out.println("4: Check Transactions");
//            System.out.println("5: Check Transactions");
//            int action = scanner.nextInt();
//            scanner.nextLine(); // consume newline
//
//            switch (action) {
//                case 1:
//                    deposit();
//                    break;
//                case 2:
//                    withdraw();
//                    break;
//                case 3:
//                    balance();
//                    break;
//                case 4:
//                    transactions();
//                    break;
//                case 5:
//                    finances();
//                    break;
//                default:
//                    System.out.println("Invalid action. Please choose again.");
//            }
//        }
//    }
//
//
//    private static void deposit(){
//        System.out.println("deposit");
//        System.out.println("user id: ");
//        String userId = scanner.nextLine();
//        System.out.println("amount: ");
//        String amount = scanner.nextLine();
//
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("userID", userId);
//        map.add("amount", amount);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//
//        ResponseEntity<Ack> response = restTemplate.postForEntity(API_BASE_URL + "/deposit", request, Ack.class);
//        System.out.println("Response: " + response.getStatusCode() + " - " + response.getBody());
//        //System.out.println("User name = "+response.getBody().getUserName());
//        //System.out.println("User password = "+response.getBody().getPassword());
//    }
//
//    private static void withdraw(){
//        System.out.println("withdraw");
//        System.out.println("user id: ");
//        String userId = scanner.nextLine();
//        System.out.println("amount: ");
//        String amount = scanner.nextLine();
//
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("userID", userId);
//        map.add("amount", amount);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//
//        ResponseEntity<User> response = restTemplate.postForEntity(API_BASE_URL + "/withdraw", request, User.class);
//        System.out.println("Response: " + response.getStatusCode() + " - " + response.getBody());
//        //System.out.println("User name = "+response.getBody().getUserName());
//        //System.out.println("User password = "+response.getBody().getPassword());
//    }
//
//    private static void balance(){
//        System.out.println("check balance");
//        System.out.println("user id: ");
//        String userId = scanner.nextLine();
//
//        ResponseEntity<User> response = restTemplate.getForEntity(API_BASE_URL + "/balance/" + userId, User.class);
//        System.out.println("Response: " + response.getStatusCode() + " - " + response.getBody());
//        //System.out.println("User name = "+response.getBody().getUserName());
//        //System.out.println("User password = "+response.getBody().getPassword());
//    }
//
//    private static void transactions() {
//        System.out.println("Check transactions");
//        System.out.println("User id: ");
//        String userId = scanner.nextLine();
//
//        ResponseEntity<List<Transaction>> response = restTemplate.exchange(
//                API_BASE_URL + "/transactions/" + userId,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Transaction>>() {}
//        );
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            List<Transaction> transactions = response.getBody();
//            System.out.println("Transactions for user " + userId + ":");
//            for (Transaction transaction : transactions) {
//                System.out.println("User ID: " + transaction.getUserId());
//                System.out.println("Transaction Type: " + transaction.getTransactionType());
//                System.out.println("Transaction Amount: " + transaction.getAmount());
//            }
//        } else {
//            System.out.println("Failed to get transactions for user " + userId);
//        }
//    }
//
//    private static void finances(){
//        System.out.println("Check finances");
//        System.out.println("User id: ");
//        String userId = scanner.nextLine();
//
//        ResponseEntity<List<Finance>> response = restTemplate.exchange(
//                API_BASE_URL + "/finances/" + userId,
//                HttpMethod.GET,
//  null,
//                new ParameterizedTypeReference<List<Finance>>() {}
//        );
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            List<Finance> finances = response.getBody();
//            System.out.println("Transactions for user " + userId + ":");
//            for (Finance finance : finances) {
//                System.out.println("User ID: " + finance.getUserId());
//                System.out.println("Description: " + finance.getDescription());
//            }
//        } else {
//            System.out.println("Failed to get finances for user " + userId);
//        }
//    }
//}
