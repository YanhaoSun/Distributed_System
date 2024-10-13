package service.girlsallowed;

import service.core.Constants;
import service.core.QuotationService;
import service.core.RemoteService;
import service.broker.RemoteBindService;

import java.rmi.registry.*;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        RemoteService remoteService = null;
        QuotationService gaqService = new GAQService();
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
            // Create the Remote Object
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(gaqService,0);
            remoteService =(RemoteService) registry.lookup(Constants.REMOTE_BIND_SERVICE);
            remoteService.registerService(Constants.GIRLS_ALLOWED_SERVICE, quotationService);
            // Register the object with the RMI Registry
//            registry.bind(Constants.GIRLS_ALLOWED_SERVICE, quotationService);
            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

}
