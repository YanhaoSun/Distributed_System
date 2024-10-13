package org.login.controller;

import org.login.controller.PrintController;
import service.core.Ack;
import org.login.frontend.service.Services;
import org.login.service.account.Account;
import org.login.service.deposit.DepositMoney;
import org.login.service.finance.FinanceService;
import org.login.service.login.LogIn;
import org.login.service.withdraw.WithdrawMoney;
import service.core.ClientInfo;
import service.core.Product;

public class basicServices implements Services {

    public static void basicServicesSelection(ClientInfo user){
        while (true){
            int action = PrintController.basicServicePage(user);
            switch (action) {
                case 1:
                    Account.checkAccount(user);
                    break;
                case 2:
                    DepositMoney.depositMoney(user);
                    break;
                case 3:
                    WithdrawMoney.withdrawMoney(user);
                    break;
                case 4:
                    Ack ack = FinanceService.getFinanceProducts(user);
                    if (ack != null){
                        FinanceService.buyFinanceProduct((Product) ack.getObject(), user);
                    }
                    break;
                case 5:
                    LogIn.logOutUser();
                    break;
                default:
                    System.out.println("Invalid action. Please choose again.");
            }
        }
    }
    public static void accountServiceSelection(ClientInfo user){
        while (true){
            int action = PrintController.accountServicePage();
            switch (action) {
                case 1:
                    Account.getUserTransactions(user);
                    break;
                case 2:
                    FinanceService.getFinance(user);
                    break;
                case 3:
                    basicServicesSelection(user);
                    break;
                default:
                    System.out.println("Invalid action. Please choose again.");
            }
        }
    }
}
