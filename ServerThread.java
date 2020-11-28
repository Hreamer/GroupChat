import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private Socket socket;

    // When relaunching server we should open up the files to get the usernames that are active rn, with an arraylist that encompases it.
    ArrayList<String> usernames = new ArrayList<String>();
    ArrayList<String> passwords = new ArrayList<String>();
    ArrayList<UserAccount> accounts = new ArrayList<UserAccount>();
    public final String userFile = "users.txt";
    public final String conversationsFile = "conversations.txt";

    public ServerThread (Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        /*Here will go the implementation for
         * Each Client  */

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
                    continue;
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
                    addUser(arguements[1], arguements[2]);
                    System.out.println(arguements[1] + " was added to users.txt");

                    writer.write("Signup Successful");
                    writer.println();
                    writer.flush(); // Ensure data is sent to the client.
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
                }
            }

        } catch (IOException e) {
            System.out.println("Connection error closing thread");
        }
    }

    public String isValidLogin(String userName, String password) throws IOException {
        File f = new File("users.txt");
        FileReader fos = new FileReader(f);
        BufferedReader br = new BufferedReader(fos);

        String user = br.readLine();
        while(user != null) {
            String[] userInfo = user.split(" - ");
            if (userInfo[0].equals(userName) && userInfo[1].equals(password)) {
                return "Valid User";
            }

            user = br.readLine();
        }
        return "Invalid User";
    }

    public void addUser(String userName, String password) throws FileNotFoundException {
        File f = new File("users.txt");
        FileOutputStream fos = new FileOutputStream(f, true);
        PrintWriter pw = new PrintWriter(fos);

        pw.println(userName + " - " + password);
    }

    // Reads the file for account info
    public ArrayList<UserAccount> readNameFile(String fileName) {
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
                accounts.add(new UserAccount(currentAccount[0], currentAccount[1]));
                line = bfr.readLine();
            }
        } catch (IOException e) {
            System.out.println("Main File doesn't currently Exist");
        }
        return accounts;
    }

    //assuming we already asked the user if they are sure on the client side
    public void deleteAccount(String username, String password) {
        File f = new File(userFile);
        usernames.remove(username);
        passwords.remove(password);
        accounts.remove(new UserAccount(username, password));
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f))){
            for (int x = 0; x < usernames.size(); x++) {
                pw.println(usernames.get(x) + " - " + passwords.get(x));
            }
        } catch (IOException e) {
            System.out.println("Server had an error deleting the user");
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
    }

    //depends on how we get this to work
    public void deleteConvo(String convo, String filename) {

    }
    //The starting arrayList should be of all the members in this convo, with the person creating the convo first and anyone else in order of addit
    public void createConvo(ArrayList<String> members) {

    }

    public void sendMessage(String message, String convo) {

    }
}

