package basic.service;

import basic.user.User;
import basic.transaction.Transaction;
import basic.finance.Finance;
import service.core.Ack;

import java.util.List;

public interface BasicService {

    ////////// Register //////////
    //////////////////////////////
    User register(int user_id);
    
    ////////// Basic Service //////////
    ///////////////////////////////////
    /**
     * Deposit service
     * @param user_id user id
     * @param amount amount of money to deposit
     * @return User type object which includes the updated balance
     */
    User deposit(int user_id, double amount);

    /**
     * Withdraw service
     * @param user_id user id
     * @param amount amount of money to withdraw
     * @return User type object which includes the updated balance
     */
    Ack<User> withdraw(int user_id, double amount);

    /**
     * add a product history
     * @param purchaseInfo
     * @return
     */
    void addFinanceHistory(Finance history);

    /**
     * Check balance service
     * @param user_id user id
     * @return User type object which includes the balance
     */
    double checkBalance(int user_id);

    /**
     * Check credit service
     * @param user_id
     * @return
     */
    int checkCredit(int user_id);

    /**
     * Check transaction history
     * @param user_id
     * @return
     */
    List<Transaction> checkTransactions(int user_id);

    ////////// Communicate with Finance Service //////////
    //////////////////////////////////////////////////////

    /**
     * Check finance product buying history
     * @param user_id
     * @return
     */
    List<String> checkFinances(int user_id);

    ////////// Other Functions //////////
    /////////////////////////////////////
    /**
     * Check if the user is exist
     * @param user_id
     * @return
     */
    Boolean IfExistUser(int user_id);

}
