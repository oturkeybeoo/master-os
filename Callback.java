import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback extends Remote {
    public void show(String text) throws RemoteException;
}