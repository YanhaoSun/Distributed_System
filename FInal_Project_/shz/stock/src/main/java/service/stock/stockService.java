package service.stock;

import service.core.ClientInfo;
import service.core.Product;

/**
 * Implementation of the stock service.
 */
public class stockService {
	
	public Product generateProduct(ClientInfo info) {

                String description = "Suitable for investors with a high risk tolerance, usually recommended for younger clients or those with a higher balance.";

                double recommendedInvestment = 0;
                if (info.age < 40 && info.balance > 20000) {
                        recommendedInvestment = info.balance * 0.5; // 50% of balance for younger, high balance
                } else {
                        recommendedInvestment = info.balance * 0.3; // 10% otherwise
                }

                return new Product(3, "stocks", 1, 0.3, description, recommendedInvestment);
	}
}
