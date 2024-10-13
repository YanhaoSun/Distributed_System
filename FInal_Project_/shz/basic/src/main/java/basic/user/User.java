package basic.user;
import javax.persistence.*;

@Table(name = "users")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="user_type",
//        discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int userId;

    @Column(name = "balance", nullable = false)
    public double balance;

    @Column(name = "credits", nullable = false)
    public int credit;

    public User() {}

    public User(int userId) {
        this.userId = userId;
        this.balance = 0;
        this.credit = 0;
    }

    public int getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
