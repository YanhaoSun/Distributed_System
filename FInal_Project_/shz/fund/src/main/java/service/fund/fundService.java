package service.fund;

import service.core.ClientInfo;
import service.core.Product;

/**
 * Implementation of the funds service.
 */
public class fundService {
        String description = "Suitable for investors seeking moderate risk and returns, appropriate for most professions.";
	
	public Product generateProduct(ClientInfo info) {

        double recommendedInvestment = info.balance * 0.3; // 30% of balance for most cases
        return new Product(2, "funds", 2, 0.2, description, recommendedInvestment);
	}
}
