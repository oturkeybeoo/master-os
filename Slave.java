import java.net.Socket;
import java.io.BufferedOutputStream;
import java.io.File;

public class Slave extends Thread {
    Socket ssock;

    public Slave(Socket s) {
        this.ssock = s;
    }

    public void run() {
        try {
            String path = "D:/Master/Operating System/master-os-part2/files/vinh/vinh.txt";
            File file = new File(path);
            byte[] bytes = new byte[(int) file.length()];

            BufferedOutputStream oos = new BufferedOutputStream(ssock.getOutputStream());
            oos.write(bytes);

            ssock.close();
        } catch (Exception e) {
            System.out.println("An error has occurred ...");
        }
    }
}