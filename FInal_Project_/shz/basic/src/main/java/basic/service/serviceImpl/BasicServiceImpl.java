package basic.service.serviceImpl;

import basic.user.User;
import basic.user.UserRepository;
import basic.transaction.Transaction;
import basic.transaction.TransactionRepository;
import basic.finance.Finance;
import basic.finance.FinanceRepository;
import basic.service.BasicService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import service.core.Ack;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasicServiceImpl implements BasicService{
    @Resource
    public UserRepository userRepository;
    @Resource
    public TransactionRepository transactionRepository;
    @Resource
    public FinanceRepository financeRepository;

    public static final String OUT = "OUT";
    public static final String IN = "IN";

    @Override
    public User register(int user_id){
        // create a new account
        System.out.println("user_id = "+user_id);
        User user = new User(user_id);
        // update balance in database
        User newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public User deposit(int user_id, double amount){
        User user = userRepository.findUserByUserId(user_id);
        // deposit
        user.setBalance(user.getBalance() + amount);
        // calculate and add credits
        user.setCredit((int)(user.getCredit() + amount*0.01));
        // update balance and credit in database
        userRepository.save(user);
        // add a transaction history to table transactions
        Transaction transaction = new Transaction(user_id, IN, amount);
        transactionRepository.save(transaction);
        
        return user;
    }

    @Override
    public Ack<User> withdraw(int user_id, double amount){
        User user = userRepository.findUserByUserId(user_id);
        // check if the balance is enough to withdraw
        if(user.getBalance() < amount){
            System.out.println("Insufficient funds. \nBalance: " + user.getBalance());
            //TODO: return sth to show the withdraw is false instead of print
            return new Ack(user, false);
        }
        user.setBalance(user.getBalance() - amount);
        // update balance in database
        userRepository.save(user);
        // add a transaction history to table transactions
        Transaction transaction = new Transaction(user_id, OUT, amount);
        transactionRepository.save(transaction);
        System.out.println("Withdraw Successful. \nBalance: " + user.getBalance());
        return new Ack(user, true);
    }

    @Override
    public void addFinanceHistory(Finance history){
        financeRepository.save(history);
    }

    @Override
    public double checkBalance(int user_id){
        User user = userRepository.findUserByUserId(user_id);
        double balance = user.getBalance();
        //System.out.println("Balance: " + balance);
        return balance;
    }

    @Override
    public int checkCredit(int user_id){
        User user = userRepository.findUserByUserId(user_id);
        int credit = user.getCredit();
        //System.out.println("Balance: " + balance);
        return credit;
    }

    @Override
    public List<Transaction> checkTransactions(int user_id){
        List<Transaction> transactions = transactionRepository.findByUserId(user_id);
        return transactions;
    }

    @Override
    public List<String> checkFinances(int user_id){
        List<Finance> finances = financeRepository.findByUserId(user_id);
        List<String> history = new ArrayList<>();
        for(Finance finance: finances){
            history.add(finance.getDescription());
        }
        return history;
    }

    @Override
    public Boolean IfExistUser(int user_id){
        User user = userRepository.findUserByUserId(user_id);
        if(user == null){
            return false;
        } else {
            return true;
        }
    }
}
