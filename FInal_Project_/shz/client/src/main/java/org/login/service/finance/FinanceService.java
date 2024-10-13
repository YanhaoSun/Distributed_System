package org.login.service.finance;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import service.core.Ack;
import service.core.ClientInfo;
import org.login.controller.PrintController;
import org.login.frontend.service.Services;
import org.login.service.account.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import service.core.Product;
import service.core.ProductList;
import service.core.PurchaseResult;
import service.core.TransactionResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FinanceService implements Services {
    private static RestTemplate restTemplate = new RestTemplate();

    private static final String API_BASE_URL = "http://localhost:";
    public static List<String> getFinance(ClientInfo user){
        String url = API_BASE_URL + "8081/finances/"+user.getUserId();
        ResponseEntity<Ack> response = restTemplate.getForEntity(url, Ack.class);
        Ack ack = response.getBody();
        List<?> list = (List<?>) ack.getObject();
        List<String> results = new ArrayList<>();;
        if (list instanceof List<?>) {
            List<?> resultList = (List<?>) list;
            if (!resultList.isEmpty()) {
                for (Object list1 : resultList) {
                    results.add((String) list1);
                }
            }
            PrintController.displayFinances(results);
        }
        return results;
    }

    public static Ack getFinanceProducts(ClientInfo user){
        int credits = 0;
        double balance = 0.0;
        Map<Double, Integer> balanceCredits = Account.getUserBalance(user);
        for (Map.Entry<Double, Integer> value: balanceCredits.entrySet()) {
            balance = value.getKey();
            credits = value.getValue();
        }
        ClientInfo clientInfo = new ClientInfo(user.getUserId(), user.name, user.getAge(), credits, balance);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

//        HttpEntity<ClientInfo> request = new HttpEntity<>(clientInfo, headers);

        ResponseEntity<ProductList> response = restTemplate.postForEntity(API_BASE_URL + "8082/products", clientInfo, ProductList.class);

        Product buyProduct = null;
        ProductList productList = response.getBody();
        buyProduct = PrintController.financeProductsPage(productList);
        if (buyProduct==null){
            return null;
        } else {
            Ack ack = new Ack(buyProduct, true);
            return ack;
        }
    }
    public static void buyFinanceProduct(Product product, ClientInfo user){
        String message = "Please enter amount:";
        double amount = PrintController.checkInputDouble(message);
        System.out.println("Product Id you wan to buy: "+product.id);
        ClientInfo clientInfo = new ClientInfo(user.getUserId(), user.getAge(), product);
        String url = API_BASE_URL+"8081/financesrequest?amount="+amount;
        ResponseEntity<Ack> response = restTemplate.postForEntity(url, clientInfo, Ack.class);
        Ack ack = response.getBody();
        String result = null;
        Map<Double, Integer> balanceCredits = Account.getUserBalance(user);
        double balance = 0.0;
        int credits = 0;
        for (Map.Entry<Double, Integer> value: balanceCredits.entrySet()) {
            balance = value.getKey();
            credits = value.getValue();
        }
        if (ack.getAck() && ack.getObject() instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) ack.getObject();
            result = (String)map.get("result");
        } else if (!ack.getAck()){
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) ack.getObject();
            result = (String)map.get("result");
        }
        PrintController.buyFinanceProduct(result, balance, credits);
    }
}
