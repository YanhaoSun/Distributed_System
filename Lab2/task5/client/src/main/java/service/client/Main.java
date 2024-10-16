package service.client;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.NumberFormat;

import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.dodgygeezers.DGQService;
import service.core.Constants;
import service.girlsallowed.GAQService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
//import service.registry.ServiceRegistry;

public class Main {
	private static Registry registry;

//	static {
//		// Create the services and bind them to the registry.
//		try {
//			registry.bind(Constants.GIRLS_ALLOWED_SERVICE, new GAQService());
//			registry.bind(Constants.AULD_FELLAS_SERVICE, new AFQService());
//			registry.bind(Constants.DODGY_GEEZERS_SERVICE, new DGQService());
//			registry.bind(Constants.BROKER_SERVICE, new LocalBrokerService());
//		} catch (RemoteException e) {
//			throw new RuntimeException(e);
//		} catch (AlreadyBoundException e) {
//			throw new RuntimeException(e);
//		}
//	}
	
	/**
	 * This is the starting point for the application. Here, we must
	 * get a reference to the Broker Service and then invoke the
	 * getQuotations() method on that service.
	 * 
	 * Finally, you should print out all quotations returned
	 * by the service.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BrokerService brokerService = null;
		Registry registry = null;
		if (args.length == 0) {
			try {
				registry = LocateRegistry.createRegistry(1099);
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				registry = LocateRegistry.getRegistry(args[0], 1099);
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			brokerService =(BrokerService) registry.lookup(Constants.BROKER_SERVICE);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		} catch (NotBoundException e) {
			throw new RuntimeException(e);
		}


		// Create the broker and run the test data
		for (ClientInfo info : clients) {
			displayProfile(info);
			
			// Retrieve quotations from the broker and display them...
			try {
				for(Quotation quotation : brokerService.getQuotations(info)) {
					displayQuotation(quotation);
				}
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}

			// Print a couple of lines between each client
			System.out.println("\n");
		}
	}
	
	/**
	 * Display the client info nicely.
	 * 
	 * @param info
	 */
	public static void displayProfile(ClientInfo info) {
		System.out.println("|=================================================================================================================|");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println(
				"| Name: " + String.format("%1$-29s", info.name) + 
				" | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
				" | Age: " + String.format("%1$-30s", info.age)+" |");
		System.out.println(
				"| Weight/Height: " + String.format("%1$-20s", info.weight+"kg/"+info.height+"m") + 
				" | Smoker: " + String.format("%1$-27s", info.smoker?"YES":"NO") +
				" | Medical Problems: " + String.format("%1$-17s", info.medicalIssues?"YES":"NO")+" |");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println("|=================================================================================================================|");
	}

	/**
	 * Display a quotation nicely - note that the assumption is that the quotation will follow
	 * immediately after the profile (so the top of the quotation box is missing).
	 * 
	 * @param quotation
	 */
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.company) + 
				" | Reference: " + String.format("%1$-24s", quotation.reference) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
		System.out.println("|=================================================================================================================|");
	}
	
	/**
	 * Test Data
	 */
	public static final ClientInfo[] clients = {
		new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false),
		new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 1.6, 100, true, true),
		new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 21, 1.78, 65, false, false),
		new ClientInfo("Rem Collier", ClientInfo.MALE, 49, 1.8, 120, false, true),
		new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 1.9, 75, true, false),
		new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 0.45, 1.6, false, false)
	};
}
