package service.broker;

import service.core.RemoteService;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

public class RemoteBindService implements RemoteService {
    public Registry registry = null;
    public RemoteBindService(Registry registry){
        this.registry = registry;
    }

    public void registerService(String name, java.rmi.Remote service) throws java.rmi.RemoteException{
        try{
            registry.bind(name, service);
        }catch (RemoteException e){
            throw new RuntimeException(e);
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
