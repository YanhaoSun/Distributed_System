package basic.finance;
import basic.user.User;

import javax.persistence.*;

@Table(name = "finance_transactions")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Finance {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int transactionId;

    @Column(name = "user_id")
    public int userId;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    @Column(name = "user_id")
//    public User userId;

    @Column(name = "product")
    public String productDescription;

    public Finance(){}

    public Finance(int userId, String productDescription){
        this.userId = userId;
        this.productDescription = productDescription;
    }

    public int getFinanceId(){
        return transactionId;
    }

    public int getUserId(){
        return userId;
    }

    public String getDescription(){
        return productDescription;
    }
}
