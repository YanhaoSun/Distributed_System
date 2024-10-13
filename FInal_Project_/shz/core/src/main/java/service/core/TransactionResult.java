package service.core;

public class TransactionResult {
    public int transactionId;

    public int userId;

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String transactionType;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double amount;

    public TransactionResult(){}

    public TransactionResult(int transactionId, int userId, String transactionType, double amount){
        this.transactionId = transactionId;
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public int getTransactionId(){
        return transactionId;
    }
    public void setTransactionId(int transactionId){
        this.transactionId = transactionId;
    }
    
    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getTransactionType(){
        return transactionType;
    }

    public double getAmount(){
        return amount;
    }
}
