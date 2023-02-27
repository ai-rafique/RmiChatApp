
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class ClientConnect {

    public static void main(final String args[]) throws NotBoundException, MalformedURLException, RemoteException {
        String name;
        name = JOptionPane.showInputDialog("Please Enter Name");

        String appender = JOptionPane.showInputDialog("Which Service Do you wish to connect to ?");
        String chatServerURI;
        chatServerURI = "rmi://localhost/" + appender;
        //chatServerURI = "rmi://192.168.43.10/ChatServer";
        ServerInterface chats = (ServerInterface) Naming.lookup(chatServerURI);
        new Thread(new Client(name, chats)).start();
    }
}
