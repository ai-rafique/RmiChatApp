
import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Server extends UnicastRemoteObject implements ServerInterface {

    static JTextArea textarea1;
    private final ArrayList<ClientInterface> chatClient;

    Server(String ss) throws RemoteException {
        chatClient = new ArrayList<>();
        JFrame f = new JFrame(ss+" Server ");
        f.setSize(100, 100);
        f.getContentPane().setLayout(new FlowLayout());
        textarea1 = new JTextArea("", 50, 50);
        Font font = new Font("TimesRoman", Font.HANGING_BASELINE, 20);
        textarea1.setFont(font);
        textarea1.setForeground(Color.RED);
        f.getContentPane().add(textarea1);
        f.pack();
        f.setVisible(true);
    }

    @Override
    public void setChatClient(ClientInterface ci) throws RemoteException {
        this.chatClient.add(ci);
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        int i = 0;
        while (i < chatClient.size()) {
            chatClient.get(i++).getMessage(message);
        }
        try {
            textarea1.append(message + " " + "\n ");
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(message);
    }
}
