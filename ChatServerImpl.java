import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    public static String ip = "localhost";
    public static int port = 1099;
    Map<String, Callback> callbackMap = new HashMap<>();

    public ChatServerImpl() throws RemoteException {
        super();
    }

    public void enter(String name, Callback cb) throws RemoteException {
        for (Map.Entry<String, Callback> entry : callbackMap.entrySet()) {
            Callback callback = entry.getValue();
            try {
                callback.show(name + " has entered the chat.\n");
            } catch (RemoteException e) {
                System.out.println(
                        "A RemoteException occurred when sending enter message to " + entry.getKey() + " :"
                                + e.getMessage());
            }

        }
        callbackMap.put(name, cb);
    }

    public void leave(String name) throws RemoteException {
        callbackMap.remove(name);
        for (Map.Entry<String, Callback> entry : callbackMap.entrySet()) {
            Callback callback = entry.getValue();
            try {
                callback.show(name + " has left the chat.\n");
            } catch (RemoteException e) {
                System.out.println(
                        "A RemoteException occurred when sending leave message to " + entry.getKey() + " :"
                                + e.getMessage());
            }
        }
    }

    public String[] who() throws RemoteException {
        int size = callbackMap.size();
        String[] people = new String[size];

        int index = 0;
        for (Map.Entry<String, Callback> entry : callbackMap.entrySet()) {
            String name = entry.getKey();
            people[index] = name;
            index++;
        }
        return people;
    }

    public void write(String name, String text) throws RemoteException {
        for (Map.Entry<String, Callback> entry : callbackMap.entrySet()) {
            String other = entry.getKey();
            Callback callback = entry.getValue();

            if (!other.equals(name)) {
                try {
                    callback.show(name + " says: " + text + "\n");
                } catch (RemoteException e) {
                    System.out.println(
                            "A RemoteException occurred when sending message to " + other + " :" + e.getMessage());
                }
            }
        }
    }

    public void attach(String name, String ip, int port) throws RemoteException {
        for (Map.Entry<String, Callback> entry : callbackMap.entrySet()) {
            String other = entry.getKey();
            Callback callback = entry.getValue();

            if (!other.equals(name)) {
                try {
                    callback.attach(ip, port);
                } catch (RemoteException e) {
                    System.out.println(
                            "A RemoteException occurred when sending file to " + other + " :" + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServerImpl();
            LocateRegistry.createRegistry(port);
            Naming.rebind("//" + ip + "/chatserver", server);
            System.out.println("Server is running at " + ip + ":" + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}