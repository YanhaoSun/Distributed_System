package service.broker;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.LinkedList;
import java.util.List;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
//import registry.ServiceRegistry;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
public class LocalBrokerService implements BrokerService, Serializable {
	public Registry registry = null;
	public LocalBrokerService(Registry registry){
		this.registry = registry;
	}
	public List<Quotation> getQuotations(ClientInfo info) throws RemoteException {
		List<Quotation> quotations = new LinkedList<Quotation>();
//		Registry registry;
//		Registry registry = LocateRegistry.createRegistry(1099);
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);

		for (String name : registry.list()) {
			if (name.startsWith("qs-")) {
				QuotationService service = null;
				try {
					service = (QuotationService) registry.lookup(name);
				} catch (NotBoundException e) {
					throw new RuntimeException(e);
				}
				quotations.add(service.generateQuotation(info));
			}
		}
		return quotations;
	}
}
