
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

public class ServerConnect {

    public static void main(String args[]) throws RemoteException, MalformedURLException {

        String ss = JOptionPane.showInputDialog("Enter Service Name");
        Naming.rebind("rmi://localhost/" + ss, new Server(ss));
    }
}
