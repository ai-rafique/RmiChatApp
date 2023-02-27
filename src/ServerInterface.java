
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote{
    void setChatClient(ClientInterface ci) throws RemoteException;
    void sendMessage(String message) throws RemoteException; 
}
