package service.core;

import java.io.Serializable;

public class PurchaseInfo implements Serializable {
    public PurchaseInfo(int purchaseId, int userId, String productName, double investment) {
		this.purchaseId = purchaseId;
		this.userId = userId;
		this.productName = productName;
		this.investment = investment;
	}
	
	public PurchaseInfo() {}

	public int purchaseId, userId;
	public String productName;
	public double investment;
}
