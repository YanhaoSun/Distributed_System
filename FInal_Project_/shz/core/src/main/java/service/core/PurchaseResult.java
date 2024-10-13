package service.core;

public class PurchaseResult {
    public PurchaseResult(int purchaseId, int userId, String productName, String result) {
        this.purchaseId = purchaseId;
        this.userId = userId;
        this.productName = productName;
        this.result = result;
    }

    public PurchaseResult(String result) {
        this.result = result;
    }

    public PurchaseResult() {}

    public int purchaseId, userId;
    public String productName;
    public String result;

    public String getResult(){
        return result;
    }
}
