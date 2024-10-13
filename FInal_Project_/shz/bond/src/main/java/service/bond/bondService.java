package service.bond;

import service.core.ClientInfo;
import service.core.Product;

/**
 * Implementation of the bonds service.
 */
public class bondService {
	
	public Product generateProduct(ClientInfo info) {
        String description = "Suitable for risk-averse investors, usually recommended for older clients or those with higher credit scores.";
		
		double recommendedInvestment = 0;
        if (info.age > 55 || info.creditScore > 750) {
            recommendedInvestment = info.balance * 0.4; // 40% of balance if older or high credit
        } else {
            recommendedInvestment = info.balance * 0.2; // 20% otherwise
        }
        return new Product(1, "bonds", 3, 0.1, description, recommendedInvestment);
	}
}
