import java.util.*;
import java.io.*;

public class UserAccount {
    private String userName;
    private String password;
    private ArrayList<String> convos = new ArrayList<String>();
    private ArrayList<ArrayList<String>> convoMembers = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> convoMessages = new ArrayList<ArrayList<String>>();
    // index place of convoMembers will be the same as convos for each group of messages
    // ConvoMessages is meant to store the actual messages, I plan to edit the write all messages into arrayList so as to more easily remove and add earlier ones. This is unless we 
    // can find a way to access specific lines within the files and remove them there.
    // convos is the name of the conversations
    public UserAccount(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "UserAccount{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public void readConvo(String convo) {
    	File f = new File(convo);
    	try {
    		f.createNewFile();
    		BufferedReader bfr = new BufferedReader(new FileReader(f));
    		
    	} catch (IOException e) {
    		//shouldn't happen
    	}
    	
    	convos.add(convo);
    }
    public ArrayList<String> getConvo() {
    	return convos;
    }
    public void addMessage() {
    	
    }
}
