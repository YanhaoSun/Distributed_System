package basic.controller;

import basic.user.User;
import basic.transaction.Transaction;
import basic.finance.Finance;
import service.core.Product;
import service.core.PurchaseInfo;
import service.core.PurchaseResult;
import service.core.Ack;
import service.core.ClientInfo;
import service.core.TransactionResult;
import basic.service.BasicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class BasicServiceController {

    // configuration

    @Resource
    private BasicService basicService;

    @Autowired 
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Value("${server.port}")
    private int port;

    private int purchase_id = 0;

    ////////// Register //////////
    //////////////////////////////
    /**
     * add a new account
     * @param clientInfo
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Ack<ClientInfo>> register(@RequestBody ClientInfo info){
        int userID = info.getUserId();
//        System.out.println("register userID = "+userID);
        //register
        User user = basicService.register(userID);
//        System.out.println("Register Successfully. userID: "+user.getUserId());
        if (user != null){
            //update ClientInfo for transferring back
            info.setBalance(0);
            info.setCreditScore(0);
            //create an Ack
            Ack<ClientInfo> ack = new Ack<ClientInfo>(info, true);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        } else {
            Ack<ClientInfo> ack = new Ack<ClientInfo>(info, false);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        }
    }

    ////////// Basic Service //////////
    ///////////////////////////////////

    /**
     * Check Balance using Get Mapping
     * @param clientInfo
     * @return
     */
    @GetMapping("/balance/{userID}")
    public ResponseEntity<Ack<ClientInfo>> GetBalance(@PathVariable int userID){
        //check if the user is exist
        if (basicService.IfExistUser(userID) != null){
            //check balance
            double balance = basicService.checkBalance(userID);
            int credit = basicService.checkCredit(userID);
            //update info for transferring back
            ClientInfo info = new ClientInfo(userID, credit, balance);
            //create an Ack
            Ack<ClientInfo> ack = new Ack<ClientInfo>(info, true);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        } else {
            ClientInfo info = new ClientInfo(userID, 0, 0);
            Ack<ClientInfo> ack = new Ack<ClientInfo>(info, false);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        }
    }

    /**
     * deposit money
     * @param clientInfo
     * @param amount amount of money to deposit
     * @return
     */
    @PostMapping("/deposit")
    public ResponseEntity<Ack<ClientInfo>> deposit(@RequestParam int userID, @RequestParam double amount){
        //check if the user is exist
        if (basicService.IfExistUser(userID) != null){
            User user = basicService.deposit(userID, amount);
            //update info for transferring back
            ClientInfo info = new ClientInfo(userID, user.getCredit(), user.getBalance());
            //create an Ack
            Ack<ClientInfo> ack = new Ack<ClientInfo>(info, true);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        } else {
            // fake info
            ClientInfo info = new ClientInfo(userID, 0, 0);
            Ack<ClientInfo> ack = new Ack<ClientInfo>(info, false);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        }
    }
    /*@PostMapping("/deposit")
    public ResponseEntity<ClientInfo> deposit(@RequestParam int userID, @RequestParam double amount){
        //check if the user is exist
        if (basicService.IfExistUser(userID) != null){
            User user = basicService.deposit(userID, amount);
            //update info for transferring back
            ClientInfo info = new ClientInfo(userID, user.getCredit(), user.getBalance());
            return ResponseEntity.status(HttpStatus.OK).body(info);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }*/

    /**
     * withdraw money
     * @param clientInfo
     * @param amount
     * @return
     */
    @PostMapping("/withdraw")
    public ResponseEntity<Ack<ClientInfo>> withdraw(@RequestParam int userID, @RequestParam double amount){
        //check if the user is exist
        if (basicService.IfExistUser(userID) != null){
            Ack<User> ack = basicService.withdraw(userID, amount);
            User user = ack.getObject();
            //update info for transferring back
            ClientInfo info = new ClientInfo(userID, user.getCredit(), user.getBalance());
            if (ack.getAck()){
                Ack<ClientInfo> ack1 = new Ack<ClientInfo>(info, true);
                return ResponseEntity.status(HttpStatus.OK).body(ack1);
            } else {
                Ack<ClientInfo> ack1 = new Ack<ClientInfo>(info, false);
                return ResponseEntity.status(HttpStatus.OK).body(ack1);
            }
        } else {
            // fake info
            ClientInfo info = new ClientInfo(userID, 0, 0);
            Ack<ClientInfo> ack = new Ack<ClientInfo>(info, false);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        }
    }

    /**
     * check transactions
     * @param clientInfo
     * @return
     */
    @GetMapping("/transactions/{userID}")
    public ResponseEntity<Ack<List<TransactionResult>>> GetTransactions(@PathVariable int userID){
        //create a transactionResult list to transfer back
        List<TransactionResult> transactionResults = new ArrayList<>();
        //check if the user is exist
        if (basicService.IfExistUser(userID) != null){
            List<Transaction> transactions = basicService.checkTransactions(userID);
            for(Transaction transaction: transactions){
                transactionResults.add(new TransactionResult(transaction.getTransactionID(), userID, transaction.getTransactionType(), transaction.getAmount()));
            }
            //create an Ack
            Ack<List<TransactionResult>> ack = new Ack<List<TransactionResult>>(transactionResults, true);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        } else {
            Ack<List<TransactionResult>> ack = new Ack<List<TransactionResult>>(null, false);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        }
    }

    ////////// Finance Service //////////
    /////////////////////////////////////
    /**
     * Check finance product transaction hitory
     * @param ClientInfo
     * @return
     */
    @GetMapping("/finances/{userID}")
    public ResponseEntity<Ack<List<String>>> GetFinances(@PathVariable int userID){
        //int userID = info.getUserId();
        //check if the user is exist
        if (basicService.IfExistUser(userID) != null){
            List<String> finances = basicService.checkFinances(userID);
            //create an Ack
            Ack<List<String>> ack = new Ack<List<String>>(finances, true);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        } else {
            Ack<List<String>> ack = new Ack<List<String>>(null, false);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        }
    }

    /**
     * Get a request from user service
     * Check if the balance is enough to buy the finance product
     * if enough: send the ID of product to finance service
     * after send: listen to the finance service for a period,
     * if receive ack, withdraw money; if not, send rejection to user service
     * if not enough: send rejection to user service
     * @param info
     * @param amount
     * @return ResponseEntity<Ack<PurchaseResult>>
     */
    @PostMapping("/financesrequest")
    public ResponseEntity<Ack<PurchaseResult>> IfEnoughToBuy(@RequestBody ClientInfo info, @RequestParam double amount){
        System.out.println("------test1");
        int userID = info.getUserId();
        //get product info
        Product product = info.getProduct();
        //check if the user is exist
        if (basicService.IfExistUser(userID) != null){
            System.out.println("------test2");
            //check balance
            double balance = basicService.checkBalance(userID);
            //check if enough
            if(balance >= amount){
                System.out.println("------test3");
                //Enough: Send request to finance service
                //Create a product request
                PurchaseInfo purchaseInfo = new PurchaseInfo(purchase_id, userID, product.getProductName(), amount);

//                String result = "Failed purchase";
                String purchaseUrl = System.getenv("BROKER_URL") + "/purchase";
                String failureHandleUrl = System.getenv("BROKER_URL") + "/failure";

                ResponseEntity<PurchaseResult> response = restTemplate.postForEntity(purchaseUrl, purchaseInfo, PurchaseResult.class);
                PurchaseResult responseResult = response.getBody();
                System.out.println(responseResult.purchaseId);
                System.out.println(purchase_id);
                if (responseResult!= null && responseResult.purchaseId == purchase_id) {
                    System.out.println("------test4");
                    System.out.println(responseResult.result);
                    if (Objects.equals(responseResult.result, "Purchase successful")) {
                        System.out.println("------test5");
                        purchase_id++;
                        System.out.println(purchase_id);
                        System.out.println(basicService.checkBalance(userID));
                        //withdraw
//                        product.amount
                        withdraw(userID, amount);
                        System.out.println(basicService.checkBalance(userID));
                        //add history
                        System.out.println("----------");
                        System.out.println(basicService.checkFinances(userID));
                        Finance history = new Finance(userID, product.getProductName());
                        basicService.addFinanceHistory(history);
                        System.out.println(basicService.checkFinances(userID));
                        //send ack
                        Ack<PurchaseResult> ack = new Ack<PurchaseResult>(responseResult, true);
                        return ResponseEntity.status(HttpStatus.OK).body(ack);
                    } else { // failed purchase, send the failed result back
                        if (responseResult.result == "Failed purchase") {
                            ResponseEntity<Boolean> failureHandle = restTemplate.postForEntity(failureHandleUrl, purchaseInfo, Boolean.class);
                        }
                        Ack<PurchaseResult> ack = new Ack<PurchaseResult>(responseResult, false);
                        return ResponseEntity.status(HttpStatus.OK).body(ack);
                    }

                    //Duplicate Transaction
                } else {
                    ResponseEntity<Boolean> failureHandle = restTemplate.postForEntity(failureHandleUrl, purchaseInfo, Boolean.class);
                    Ack<PurchaseResult> ack = new Ack<PurchaseResult>(responseResult, false);
                    return ResponseEntity.status(HttpStatus.OK).body(ack);
                }


            } else {
                //Not Enough: Send rejection to user service
                String result = "balance not enough, you can not buy this product";
                PurchaseResult purchaseResult = new PurchaseResult(result);
                Ack<PurchaseResult> ack = new Ack<PurchaseResult>(purchaseResult, false);
                return ResponseEntity.status(HttpStatus.OK).body(ack);
            }

        } else {
            //user not exist
            Ack<PurchaseResult> ack = new Ack<PurchaseResult>(null, false);
            return ResponseEntity.status(HttpStatus.OK).body(ack);
        }
  
    }

    /**
     * Send finance product buying request
     * @param purchaseInfo
     */
//    @PostMapping("/finances-request-ok")
    public PurchaseResult EnoughToBuy_Send(PurchaseInfo purchaseInfo, int userID, double amount){

        String result = "Failed purchase";
        String purchaseUrl = "http://localhost:8082/purchase";
        String failureHandleUrl = "http://localhost:8082/failure";
        System.out.println("-------test1---------");
        ResponseEntity<PurchaseResult> response = restTemplate.postForEntity(purchaseUrl, purchaseInfo, PurchaseResult.class);
        System.out.println("-------test2---------");
        PurchaseResult responseResult = response.getBody();
        System.out.println(responseResult.purchaseId+responseResult.result);
        if (responseResult!= null && responseResult.purchaseId == purchase_id) {
            if (responseResult.result == "Purchase successful") { 
                purchase_id++;
                //withdraw
                withdraw(userID,amount);
                //add history

            } else if (responseResult.result == "Failed purchase") { 
                ResponseEntity<Boolean> failureHandle = restTemplate.postForEntity(failureHandleUrl, purchaseInfo, Boolean.class);
            }

        } else {
            ResponseEntity<Boolean> failureHandle = restTemplate.postForEntity(failureHandleUrl, purchaseInfo, Boolean.class);
        }
        result = (responseResult==null? result : responseResult.result);
        System.out.println(responseResult.purchaseId+responseResult.result);
        return responseResult;

    }

}
