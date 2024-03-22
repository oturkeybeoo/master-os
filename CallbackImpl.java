import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallbackImpl extends UnicastRemoteObject implements Callback {
    public CallbackImpl() throws RemoteException {
        super();
    }

    public void show(String text) throws RemoteException {
        System.out.println(text);
    }
}