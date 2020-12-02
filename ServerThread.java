import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private Socket socket;

    // When relaunching server we should open up the files to get the usernames that are active rn, with an arraylist that encompases it.
    ArrayList<String> usernames = new ArrayList<String>();
    ArrayList<String> passwords = new ArrayList<String>();
    public final String userFile = "users.txt";
    public final String conversationsFile = "conversations.txt";

    public ServerThread(Socket socket) {
        this.socket = socket;
        //readNameFile(userFile);
    }

    @Override
    public void run() {
        /*Here will go the implementation for each Client  */

        //try catch statement for the input and output streams and connection
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            String command = "";
            while (true) {
                /*Inside this for loop it will essentially listen to
                 * commands from the client than send out any thing that is needed*/
                command = reader.readLine();

                //if nothing is read than we restart the loop and check again
                if (command == null) {
                    System.out.println("command = null");
                    break;
                }
                String[] arguements = command.split(" - ");

                //Login - Username - Password
                if (arguements[0].equals("Login")) {
                    //Checking to see if its a valid login
                    String stringToClient = isValidLogin(arguements[1], arguements[2]);

                    //sending to client
                    writer.write(stringToClient);
                    writer.println();
                    writer.flush(); // Ensure data is sent to the client.
                    System.out.println("Client was sent data: " + stringToClient);
                }

                //SignUp - Username - Password
                else if (arguements[0].equals("SignUp")) {
                    if (addUser(arguements[1], arguements[2])) {
                        System.out.println(arguements[1] + " was added to users.txt");

                        writer.write("Signup Successful");
                        writer.println();
                        writer.flush(); // Ensure data is sent to the client.
                    } else {
                        System.out.println("User already exists");

                        writer.write("Signup Unsuccessful");
                        writer.println();
                        writer.flush(); // Ensure data is sent to the client.
                    }
                }

                //DeleteUser - username
                else if (arguements[0].equals("DeleteUser")) {
                    File f = new File(userFile);
                    FileReader fr = new FileReader(f);
                    BufferedReader br = new BufferedReader(fr);

                    FileOutputStream fos = new FileOutputStream(f);
                    PrintWriter pw = new PrintWriter(fos);

                    /*Finding all the usernames besides the one we want deleted and adding
                     * it to a varibale*/
                    String line = br.readLine();
                    String withoutUser = "";
                    while(line != null) {
                        String[] parts = line.split(" - ");
                        if(!parts[0].equals(arguements[1])) {
                            withoutUser += line + "/n";
                        } else {
                            break;
                        }
                        line = br.readLine();
                    }

                    /*Now we take that variable and just write it back to the file
                     * because we are not in append mode in our fos we just overwrite the
                     * whole file */
                    pw.print(withoutUser);
                }

                //checkValidUser - user
                else if(arguements[0].equals("checkValidUser")) {
                    File f = new File(userFile);
                    FileReader fr = new FileReader(f);
                    BufferedReader br = new BufferedReader(fr);

                    boolean found = false;
                    String line = br.readLine();
                    while(line != null) {
                        String[] parts = line.split(" - ");

                        if (arguements[1].equals(parts[0])) {
                            writer.write("User is Valid " + parts[0]);
                            System.out.println("User is Valid " + parts[0] );
                            writer.println();
                            writer.flush(); // Ensure data is sent to the client.
                            found = true;
                            break;
                        }

                        line = br.readLine();
                    }

                    if(found == false) {
                        writer.write("User is Invalid");
                        writer.println();
                        writer.flush(); // Ensure data is sent to the client.
                    }

                    fr.close();
                    br.close();
                }

                //startConversation - user - user - user - ...
                else if (arguements[0].equals("startConversation")) {
                    String fileName = "";
                    for(int i = 1; i < arguements.length; i++) {
                        if (i != arguements.length -1) {
                            fileName += arguements[i] + " - ";
                        }
                        fileName += arguements[i];
                    }
                    fileName += ".txt";

                    File f = new File(fileName);
                    f.createNewFile();

                    //adding the new file to the list of conversations
                    File f2 = new File(conversationsFile);
                    FileOutputStream fos = new FileOutputStream(f2, true);
                    PrintWriter pw = new PrintWriter(fos);

                    String[] conversationName = fileName.split("\\.");
                    pw.println(conversationName[0]);

                    fos.close();
                    pw.close();
                }

                //ChangePassword - user - newPassword
                else if (arguements[0].equals("ChangePassword")) {
                    //opening user file to read from
                    File f = new File(userFile);
                    FileReader fr = new FileReader(f);
                    BufferedReader br = new BufferedReader(fr);

                    String totalFile = "";

                    //Read the line and if it equals the username add it to totalConversation
                    //with the new password. else just add the line with the user that doesnt equal
                    //what the client said
                    String line = br.readLine();
                    while (line != null) {
                        String[] parts = line.split(" - ");

                        if(parts[0].equals(arguements[1])) {
                            totalFile += parts[0] + " - " + arguements[2] + "\n";
                        } else {
                            totalFile += line + "\n";
                        }

                        line = br.readLine();
                    }

                    //closing the streams
                    fr.close();
                    br.close();

                    //writing to the userfile. Using just a print cause everyline we add a
                    //newline character to the end of the line so no need to use println
                    try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)))) {
                        pw.print(totalFile);
                    }

                    writer.write("Password Changed");
                    writer.println();
                    writer.flush(); // Ensure data is sent to the client.
                }

                //updateConversation - message - userWhoSent - user - user - user - ..."
                else if (arguements[0].equals("updateConversation")) {
                    String fileName = "";
                    for(int i = 1; i < arguements.length; i++) {
                        if (i != arguements.length -1) {
                            fileName += arguements[i] + " - ";
                        }
                        fileName += arguements[i];
                    }
                    fileName += ".txt";

                    File f = new File(fileName);
                    FileOutputStream fos = new FileOutputStream(f, true);
                    PrintWriter pw = new PrintWriter(fos);

                    //User who sent - message - deleted by user - ...
                    pw.println(arguements[2] + " - " + arguements[1]);

                    fos.close();
                    pw.close();
                }

                //allConversations - user
                else if (arguements[0].equals("allConversations")) {
                    ArrayList<String> conversationList = new ArrayList<String>();
                    String user = arguements[1];
                    String allConversations = "";

                    File f = new File(conversationsFile);
                    FileReader fr = new FileReader(f);
                    BufferedReader br = new BufferedReader(fr);

                    String conversation = br.readLine();
                    while (conversation != null) {
                        String[] parts = conversation.split("\\.");
                        for (int i = 0; i < parts.length; i++) {
                            if(parts[i].equals(user)) {
                                conversationList.add(conversation);
                            }
                        }

                        conversation = br.readLine();
                    }

                    fr.close();
                    br.close();

                    for(String convoName: conversationList) {
                        File f1 = new File(convoName + ".txt");
                        FileReader fr1 = new FileReader(f1);
                        BufferedReader br1 = new BufferedReader(fr1);

                        allConversations += convoName + ", ";

                        String transcript = br1.readLine();
                        while(transcript != null) {
                            allConversations += transcript;

                            transcript = br1.readLine();
                        }

                        fr1.close();
                        br1.close();
                    }

                    writer.write(allConversations);
                    writer.println();
                    writer.flush(); // Ensure data is sent to the client.
                }
            }

        } catch (IOException e) {
            System.out.println("Connection error closing thread");
        }
    }

    public String isValidLogin(String userName, String password) throws IOException {
        File f = new File(userFile);
        FileReader fos = new FileReader(f);
        BufferedReader br = new BufferedReader(fos);

        String user = br.readLine();
        while(user != null) {
            String[] userInfo = user.split(" - ");
            if (userInfo[0].equals(userName)) {
                fos.close();
                br.close();
                return "Valid User";
            }

            user = br.readLine();
        }
        fos.close();
        br.close();
        return "Invalid User";
    }

    public boolean addUser(String userName, String password) throws IOException {
        File f = new File(userFile);

        FileReader fos = new FileReader(f);
        BufferedReader br = new BufferedReader(fos);

        String user = br.readLine();
        while(user != null) {
            String[] userInfo = user.split(" - ");
            if (userInfo[0].equals(userName)) {
                fos.close();
                br.close();
                return false;
            }
            user = br.readLine();
        }
        fos.close();
        br.close();

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f, true)))) {
            pw.println(userName + " - " + password);
        } catch (IOException e) {
            System.out.println("File couldn't write");
        }
        return true;
    }

    /*
    // Reads the file for account info
    public void readNameFile(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                //TODO implement the JOptionPane for error messages, "
            }
        }
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line = bfr.readLine();
            while (line != null) {
                String[] currentAccount = line.split(" - ");
                usernames.add(currentAccount[0]);
                passwords.add(currentAccount[1]);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            System.out.println("Main File doesn't currently Exist");
        }
    }

    //Please format the ArrayList to make it so each line is the proper input for the file. If this isn't easy to do let me know @steve
    //This is to handle most writing into files, since the updating of convos is best done outside of the file and written in. This should add only one line in
    // and will be used to handle adding new lines into the convo
    public void writeToFile(String fileName, String input) {
        File f = new File(fileName);
        try {
            f.createNewFile();
        } catch (IOException e) {
            //TODO implement the JOptionPane for error messages, "
        }
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)))) {
            pw.println(input);
        } catch (IOException e) {
            // not sure what to put here
        }
    }

    // this one should be used to rewrite entire files, such as editing out a single message from the list
    public void writeToFile(String fileName, ArrayList<String> input) {
        File f = new File(fileName);
        try {
            f.delete();
            f.createNewFile();
        } catch (IOException e) {
            //TODO implement the JOptionPane for error messages, "
        }
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f))){
            for (int x = 0; x < input.size(); x++) {
                pw.println(input.get(0));
            }
        } catch (IOException e) {

        }
    }

    //reads the personal convo files, should be input the convo that they requested to access, maybe do a check with the server side to see if it can verify if file exists?
    public ArrayList<String> getMembers(String convo) {
        File f = new File(convo);
        ArrayList<String> messages = new ArrayList<String>();
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                //TODO implement the JOptionPane for error messages, "
            }
        }
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line = bfr.readLine();
            String[] string = line.split(" - ");
            for (int x = 0; x < string.length; x++) {
                messages.add(string[x]);
            }
        } catch (IOException e) {
            System.out.println("Convo File doesn't currently Exist");
            // Change above to joption later
        }
        return messages;
    } */
}
