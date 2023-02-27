
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends UnicastRemoteObject implements ClientInterface, Runnable {

    private final ServerInterface cs;
    private String name = null;
    private String History = "";
    private final JFrame f;
    private static JTextArea jta;
    private final JTextField jtf;
    private final JButton send, history, exit;
    HistoryHandler handler;
    ExitHandler handle;
    SendHandler handle2;

    Client(String name, ServerInterface cs) throws RemoteException {
        this.name = name;
        this.cs = cs;
        cs.setChatClient(this);
        f = new JFrame(name + " Chat Side");
        send = new JButton("Send");
        history = new JButton("History");
        exit = new JButton("Exit");
        
        jta = new JTextArea("", 50, 50);
        Font font = new Font("Courier", Font.HANGING_BASELINE, 14);
        jta.setFont(font);
        jta.setForeground(Color.ORANGE);

        jtf = new JTextField(50);
        f.setSize(50, 50);
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(jta);
        f.getContentPane().add(jtf);
        f.getContentPane().add(send);
        f.getContentPane().add(history);
        f.getContentPane().add(exit);
        f.pack();
        f.setVisible(true);
        handler = new HistoryHandler();
        handle = new ExitHandler();
        history.addActionListener(handler);
        exit.addActionListener(handle);
    }

    private class SendHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                cs.sendMessage(name + " :" + jtf.getText());
                History += jtf.getText() + "\n";
                jtf.setText("");
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class HistoryHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            printHistory();
        }
    }

    private class ExitHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                cs.sendMessage(name + " Has left the chat");
                send.setEnabled(false);
                UnicastRemoteObject.unexportObject(cs, true);
            } catch (NoSuchObjectException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void printHistory() {
        JFrame h = new JFrame(name + "'s Chat History");
        JTextArea textarea1 = new JTextArea("", 50, 50);
        h.setSize(50, 50);
        h.getContentPane().setLayout(new FlowLayout());
        textarea1.setText(History);
        h.getContentPane().add(textarea1);
        h.pack();
        h.setVisible(true);
        System.out.println(History);
    }

    @Override
    public void getMessage(String message) throws RemoteException {
        System.out.println(message);
        if (message.equalsIgnoreCase(name + " has joined the chatroom\n")) {
        } else {
            jta.append(message + "\n");
        }
    }

    @Override
    public void run() {
        try {
            jta.setText(name + " has joined the chatroom\n");
            cs.sendMessage(name + " has joined the chatroom\n");
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        handle2 = new SendHandler();
        send.addActionListener(handle2);
    }
}
