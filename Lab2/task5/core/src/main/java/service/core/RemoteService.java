package service.core;

public interface RemoteService extends java.rmi.Remote{
    public void registerService(String name, java.rmi.Remote service)throws java.rmi.RemoteException;
}
