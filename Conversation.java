import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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


    public Conversation(ArrayList<String> users, String title, JFrame chatter, ArrayList<String> chat) {
        this.chat = chat;
        this.chatter = chatter;
        this.title = title;
        convo = new JButton(title);
        convo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPressed(chat);
            }
        });

    }

    public void buttonPressed(ArrayList<String> chat) {

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