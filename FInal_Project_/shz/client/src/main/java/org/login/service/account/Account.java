package org.login.service.account;

import org.login.controller.basicServices;
import org.login.service.finance.FinanceService;
import service.core.Ack;
import service.core.ClientInfo;
import org.login.controller.PrintController;
import org.login.frontend.service.Services;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import service.core.Product;
import service.core.TransactionResult;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transaction;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Account implements Services {
    private static RestTemplate restTemplate = new RestTemplate();

    private static final String API_BASE_URL = "http://localhost:";
    public static void checkAccount(ClientInfo user){
        double balance = 0.0;
        int credits = 0;
        Map<Double, Integer> balanceCredits = getUserBalance(user);
        for (Map.Entry<Double, Integer> value: balanceCredits.entrySet()) {
            balance = value.getKey();
            credits = value.getValue();
        }
        ClientInfo clientInfo = new ClientInfo(user.getUserId(), user.name, user.getAge(), credits, balance);
        PrintController.accountPage(clientInfo);
        basicServices.accountServiceSelection(user);
    }
    public static List<TransactionResult> getUserTransactions(ClientInfo user) {
        String url = API_BASE_URL + "8081/transactions/"+user.getUserId();
        ResponseEntity<Ack> response = restTemplate.getForEntity(url, Ack.class);
        List<?> list = (List<?>) response.getBody().getObject();
        List<TransactionResult> results = new ArrayList<>();;
        if (list instanceof List<?>){
            List<?> resultList = (List<?>) list;
            if (!resultList.isEmpty()) {
                for (Object list1: resultList){
                    if (list1 instanceof Map<?, ?>) {
                        Map<?, ?> itemMap = (Map<?, ?>) list1;
                        TransactionResult transactionResult = new TransactionResult();
                        transactionResult.setTransactionId((Integer) itemMap.get("transactionId"));
                        transactionResult.setUserId((Integer) itemMap.get("userId"));
                        transactionResult.setTransactionType((String) itemMap.get("transactionType"));
                        transactionResult.setAmount((Double) itemMap.get("amount"));
                        results.add(transactionResult);
                    }
                }
            }
            PrintController.transactionPage(results);
        } else {
            System.out.println("not List");
        }
        return results;
    }
    public static Map<Double, Integer> getUserBalance(ClientInfo user) {
        double balance = 0.0;
        int credits = 0;
        String url = API_BASE_URL + "8081/balance/" + user.getUserId();
        ResponseEntity<Ack> response = restTemplate.getForEntity(url, Ack.class);
        Ack ack = response.getBody();
        ClientInfo info = new ClientInfo();
        if (ack.getAck() && ack.getObject() instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) ack.getObject();
            info.setBalance((Double) map.get("balance"));
            info.setCreditScore((Integer) map.get("creditScore"));
            balance = info.getBalance();
            credits = info.getCreditScore();
        }
        HashMap<Double, Integer> balanceCredits = new HashMap<>();
        balanceCredits.put(balance, credits);
        return balanceCredits;
    }
}