import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.awt.TextArea;

public class CallbackImpl extends UnicastRemoteObject implements Callback {
    public TextArea text;

    public CallbackImpl(TextArea t) throws RemoteException {
        super();
        text = t;
    }

    public void show(String text) throws RemoteException {
        System.out.println(text);
    }
}