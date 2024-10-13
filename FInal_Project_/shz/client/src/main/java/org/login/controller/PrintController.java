package org.login.controller;

import service.core.ClientInfo;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import service.core.Product;
import service.core.ProductList;
import service.core.PurchaseResult;
import service.core.TransactionResult;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PrintController {
    private static final Scanner scanner = new Scanner(System.in);
    static String line = new String(new char[40]).replace("\0", "=");
    private static String printHeader(String message){
        String header = new String(new char[(40 - message.length()) / 2]).replace("\0", " ");
        return header + message + header;
    }
    public static int welcomePage(){
        String bankName = "No Money Bank";

        String welcomeMessage = "Welcome to " + bankName + "!";

        String instructions = "Please select services:";

        System.out.println(line);
        System.out.println(printHeader(welcomeMessage));
        System.out.println(line);
        System.out.println(instructions);
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("3: Exit Bank System");
        String message = "Please select your service: ";
        int action = checkInputInt(message);
        return action;
    }
    public static MultiValueMap<String, String> logInPage(){
        Console console = System.console();
        if (console == null) {
            throw new IllegalStateException("Console not available");
        }

        String username = console.readLine("Enter username: ");
        char[] passwordArray = console.readPassword("Enter password: ");
        String password = new String(passwordArray);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user_name", username);
        map.add("password", password);
        return map;
    }
    public static void logInStatusPage(boolean success){
        if (success){
            System.out.println(line);
            String message = "Log in successful";
            System.out.println(printHeader(message));
        } else {
            System.out.println(line);
            String message = "Username or Password incorrect";
            System.out.println(printHeader(message));
        }
    }
    public static void logOutPage(){
        System.out.println(line);
        String message = "You have logged out!";
        System.out.println(printHeader(message));
        CommandLineClient.start();
    }
    public static ClientInfo registerPage(){
        String message = "Enter age: ";
        int age = checkInputInt(message);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return new ClientInfo(username, password, age);
    }
    public static void registerStatusPage(boolean success){
        if (success){
            System.out.println(line);
            String message = "Registered Successfully";
            System.out.println(printHeader(message));
        } else {
            System.out.println(line);
            String message = "This username already registered";
            System.out.println(printHeader(message));
        }
    }
    public static int basicServicePage(ClientInfo user){
        String userMessage = "Welcome, " + user.name + "!";
        String instructions = "Please select services:";

        System.out.println(line);
        System.out.println(printHeader(userMessage));
        System.out.println(line);
        System.out.println(instructions);

        System.out.println("1: Check Account");
        System.out.println("2: Lodgement");
        System.out.println("3: Withdraw");
        System.out.println("4: Finance Services");
        System.out.println("5: Log Out");
        String message = "Please select your service: ";
        int action = checkInputInt(message);
        return action;
    }
    public static void depositPage(boolean success, Map<Double, Integer> balanceCredits){
        double balance = 0.0;
        int credits = 0;
        for (Map.Entry<Double, Integer> value: balanceCredits.entrySet()) {
            balance = value.getKey();
            credits = value.getValue();
        }
        if (success){
            System.out.println(line);
            System.out.println("---Deposit Successfully");
            System.out.println("---Balance: "+balance);
            System.out.println("---Credits: "+credits);
        } else {
            System.out.println(line);
            System.out.println("---Deposit Failed");
            System.out.println("---Balance: "+balance);
            System.out.println("---Credits: "+credits);
        }
    }
    public static void withdrawPage(boolean success, Map<Double, Integer> balanceCredits){
        double balance = 0.0;
        int credits = 0;
        for (Map.Entry<Double, Integer> value: balanceCredits.entrySet()) {
            balance = value.getKey();
            credits = value.getValue();
        }
        if (success){
            System.out.println(line);
            System.out.println("---Withdraw Successfully");
            System.out.println("---Balance: "+balance);
            System.out.println("---Credits: "+credits);
        } else {
            System.out.println(line);
            System.out.println("---Withdraw Failed");
            System.out.println("---Balance: "+balance);
            System.out.println("---Credits: "+credits);
        }
    }
    public static void accountPage(ClientInfo user){
        String userMessage = "Account Information";
        System.out.println(line);
        System.out.println(printHeader(userMessage));
        System.out.println(line);
        System.out.println("Name = "+user.name);
        System.out.println("Age = "+user.age);
        System.out.println("Account balance: "+user.balance);
        System.out.println("Credit score: "+user.creditScore);
    }
    public static void transactionPage(List<TransactionResult> transactionResults){
        System.out.println(line);
        if (transactionResults.isEmpty()){
            System.out.println("---Sorry, You have no transactions now!");
        } else {
            System.out.println("---Transactions: ");
            for (TransactionResult transaction: transactionResults){
                System.out.println("----------------");
                System.out.println("--Type: "+transaction.getTransactionType());
                System.out.println("--Amount: "+transaction.amount);
            }
        }
    }
    public static int accountServicePage(){
        String instructions = "Check Transactions or Finance Products?";
        System.out.println(line);
        System.out.println(printHeader(instructions));
        System.out.println(line);

        System.out.println("1: Transactions");
        System.out.println("2: Finance Products");
        System.out.println("3: Go Back");
        String message = "Please select your service or back to previous page: ";
        int action = checkInputInt(message);
        return action;
    }

    public static double checkInputDouble(String message){
//        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        double number = 0;
        while (!validInput) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                number = Double.parseDouble(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid value. Please try again.");
            }
        }
        return number;
    }
    public static int checkInputInt(String message){
//        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        int number = 0;
        while (!validInput) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                number = Integer.parseInt(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid value, please try again.");
            }
        }
        return number;
    }
    public static Product financeProductsPage(ProductList productList){
        Product buyProduct = null;
        String userMessage = "Products Information";
        System.out.println(line);
        System.out.println(printHeader(userMessage));
        System.out.println(line);
        ArrayList<Integer> productIds = new ArrayList<>();
        for(Product product: productList.products){
            productIds.add(product.id);
            System.out.println("---Product Id: "+product.id);
            System.out.println("---Product Name: "+product.name);
            System.out.println("---Product Term: "+product.term);
            System.out.println("---Product rate: "+product.interestRate);
            System.out.println("---Product Recommended Inverstment: "+product.recommendedInverstment);
            System.out.println("---Produce Description: "+product.description);
            System.out.println(line);
        }
        while (true){
            String message = "If you want to buy any product, please type the Product Id, or type '0' to back previous page: ";
            int typeValue = checkInputInt(message);
            if (typeValue == 0){
                break;
            } else if (productIds.contains(typeValue)){
                for (Product product: productList.products){
                    if (product.id == typeValue){
                        buyProduct = product;
                    }
                }
                break;
            } else {
                System.out.println("Please type the Product Id or '0' to back previous page");
            }
        }
        return buyProduct;
    }
    public static void displayFinances(List<String> finances){
        System.out.println(line);
        if (finances.isEmpty()){
            System.out.println("---Sorry, You have not bought any finance products yet!");
        } else {
            System.out.println("Finance products you bought are:");
            for (String finance: finances){
                System.out.println("---"+finance);
            }
        }
    }
    public static void buyFinanceProduct(String purchaseResult, double balance, int credits){
        System.out.println(line);
        System.out.println("Your "+purchaseResult);
        System.out.println("Your current balance: "+balance);
        System.out.println("Your current credits: "+credits);
    }
}
