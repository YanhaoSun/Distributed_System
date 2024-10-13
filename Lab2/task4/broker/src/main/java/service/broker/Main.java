package service.broker;
import service.core.BrokerService;
import service.core.Constants;
import service.core.QuotationService;
import java.rmi.registry.*;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
public class Main {
    public static void main(String[] args) {

        try {
            // Connect to the RMI Registry - creating the registry will be the
            // responsibility of the broker.
//            Registry registry = LocateRegistry.createRegistry(1099);
            Registry registry = null;
            if (args.length == 0) {
                registry = LocateRegistry.createRegistry(1099);
            } else {
                registry = LocateRegistry.getRegistry(args[0], 1099);
            }
            BrokerService brokerService = new LocalBrokerService(registry);
            // Create the Remote Object
            BrokerService brokerExportService = (BrokerService) UnicastRemoteObject.exportObject(brokerService,0);
            // Register the object with the RMI Registry
            registry.bind(Constants.BROKER_SERVICE, brokerExportService);
            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

}
