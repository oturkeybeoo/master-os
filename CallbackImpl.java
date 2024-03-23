import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.awt.TextArea;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class CallbackImpl extends UnicastRemoteObject implements Callback {
    public TextArea text;

    public CallbackImpl(TextArea t) throws RemoteException {
        super();
        text = t;
    }

    public void show(String text) throws RemoteException {
        this.text.append(text);
    }

    public void attach(String ip, int port) throws RemoteException {
        try {
            Socket csock = new Socket(ip, port);
            BufferedInputStream ois = new BufferedInputStream(csock.getInputStream());
            System.out.println(ois.read());
            csock.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        this.text.append("Receive file from client");
    }
}