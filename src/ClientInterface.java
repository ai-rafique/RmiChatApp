
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void getMessage(String message) throws RemoteException;
}
