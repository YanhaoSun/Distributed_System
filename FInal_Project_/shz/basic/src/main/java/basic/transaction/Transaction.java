package basic.transaction;
import javax.persistence.*;

@Table(name = "transactions")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int transactionId;

    @Column(name = "user_id")
    public int userId;

    @Column(name = "transaction_type")
    public String transactionType;

    @Column(name = "amount")
    public double amount;

    public Transaction(){}

    public Transaction(int userId, String transactionType, double amount){
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public int getTransactionID(){
        return transactionId;
    }
    
    public int getUserId(){
        return userId;
    }

    public String getTransactionType(){
        return transactionType;
    }

    public double getAmount(){
        return amount;
    }
}
