import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.*;
/**
 * This class is to create a button for every conversation and it also sets a method for what to do when that button is
 * pressed.
 */

public class Conversation {
    private ArrayList<String> users;
    private JButton convo;
    private String title;
    private JFrame chatter;
    private ArrayList<String> chat;
    private int count;
    private Socket socket;

    public Conversation(ArrayList<String> users, String title) {
        this.users = users;
        this.chat = chat;
        this.chatter = chatter;
        this.title = title;
        this.count = count;
        this.socket = socket;
        convo = new JButton(title);
        convo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPressed(chat);
            }
        });
    }
    public int getCount() {
        return count;
    }
    public void buttonPressed(ArrayList<String> chat) {


        try {
            PrintWriter pt = new PrintWriter(socket.getOutputStream());
            BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pt.write(this.title);
            pt.flush();
        } catch (IOException io) {
            io.printStackTrace();
        }
        chatter.setVisible(true);
    }
    public void addChat(String line) {
        chat.add(line);
    }

    public ArrayList<String> getChat() {
        return chat;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JButton getButton() {
        return convo;
    }
}
