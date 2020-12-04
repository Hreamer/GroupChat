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
    private String title;
    private String filename;

    public Conversation(ArrayList<String> users, String title, String filename) {
        this.users = users;
        this.title = title;
        this.filename = filename;

    }
    public void setFilename(String filename) { this.filename = filename; }

    public String getFilename() { return this.filename; }

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

}
