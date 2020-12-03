import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.Style;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.net.*;
import java.nio.Buffer;
import java.io.*;
import java.util.ArrayList;


public class Client extends JFrame {
    //connect to server
    static String hostname = "localhost";
    static int portNumber = 6174;
    //initial startup page
    static JButton signIn;
    static JButton signUp;
    static JLabel titleLabel;
    static JTextField userName;
    static JTextField password;
    static JFrame myFrame;
    static JPanel myTopPanel;
    static JPanel myMiddlePanel;
    static JPanel myBottomPanel;
    //signup frame
    static JFrame signUpFrame;
    static JPanel signUpMyTopPanel;
    static JPanel signUpMyMiddlePanel;
    static JPanel signUpMyBottomPanel;
    static JTextField signUpUsername;
    static JTextField signUpPassword;
    static JLabel signUpTopPanelLabel;
    static JButton backButton;
    static JButton confirmSignUp;

    //options board
    static JFrame optionsMenu;
    static JTextField passwordTextChange;
    static JButton confirmPasswordChange;
    static JPanel topPanelOptionsMenu;
    static JButton deleteAccount;
    static JButton backButtonToChat;
    static JPanel middlePanelForOptions;
    static JLabel optionsMenuLabel;
    static JLabel passwordChangeLabel;
    //private static PrintWriter writer = new PrintWriter(socket.getOutputStream());

    //message board
    private static JButton delete;
    private static JButton back;
    private static JTextField deleteMessage;
    private static JButton send;

    private static JTextField composeMessage;
    public static Socket socket;
    //static ArrayList<String> chats = new ArrayList<>();
    static UserAccount currentUser;
    static JPanel chatter;
    static Container content;
    static JTextArea textArea;
    static JPanel top;
    static JPanel bottom;

    //full display, including the chats and the open message
    private static JFrame fullFrame;
    private static JSplitPane splitPane;
    private static JPanel chatButtonFrame;
    private static JList<String> list;
    private static ArrayList<Conversation> conversations;
    private static String[] conversationTitles;
    private static JButton newConvo;

    //options panel if the user wants to edit username or password, or delete a conversation
    private static JFrame optionsPane;
    private static JButton options;
    private static JButton changePassword;
    private static JButton deleteConversation;

    private static Client client = new Client();

    private static BufferedReader reader;
    private static PrintWriter writer;

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        socket = new Socket(hostname, portNumber);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                conversationTitles = new String[0];
                fullFrame = new JFrame("Messages");
                fullFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fullFrame.setSize(500, 500);
                fullFrame.setResizable(false);
                chatButtonFrame = new JPanel();
                chatter = new JPanel();
                textArea = new JTextArea(23, 35);
                composeMessage = new JTextField(20);
                top = new JPanel();
                bottom = new JPanel();
                back = new JButton("Back");
                back.addActionListener(actionListener);
                delete = new JButton("Delete");
                deleteMessage = new JTextField("What message would you like to delete?...");
                deleteMessage.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (deleteMessage.getText().equals("What message would you like to delete?...")) {
                            deleteMessage.setText("");
                        }
                    }
                });
                send = new JButton("Send");
                send.addActionListener(actionListener);
                newConvo = new JButton("+");
                newConvo.addActionListener(actionListener);
                chatButtonFrame.add(newConvo);
                options = new JButton("options");
                options.addActionListener(actionListener);
                chatButtonFrame.add(options);
                if (conversationTitles != null && conversationTitles.length > 0) {
                    list = new JList<String>(conversationTitles);
                    JScrollPane listScroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    list.setLayoutOrientation(JList.VERTICAL);
                    list.setVisibleRowCount(-1);
                    MouseListener mouseListener = new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            if (e.getClickCount() == 1) {

                                //TODO general algorithm for what happens when a conversation is clicked

                                /*
                                if (list.getSelectedValue().equals(conversationTitles[0])) {
                                    textArea.setText("hello");
                                } else if (list.getSelectedValue().equals(conversationTitles[1])) {
                                    textArea.setText("hi");
                                } else if (list.getSelectedValue().equals(conversationTitles[2])) {
                                    textArea.setText("what's up");
                                } else if (list.getSelectedValue().equals(conversationTitles[3])) {
                                    textArea.setText("the sky");
                                }
                                 */

                            }
                        }
                    };
                    list.addMouseListener(mouseListener);
                    chatButtonFrame.add(list);
                }
                JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                top.add(back);
                top.add(delete);
                top.add(deleteMessage);
                bottom.add(send);
                bottom.add(composeMessage);

                chatter.add(top, BorderLayout.NORTH);
                chatter.add(scroll, BorderLayout.CENTER);
                chatter.add(bottom, BorderLayout.SOUTH);

                splitPane = new JSplitPane();
                fullFrame.setSize(700,500);
                splitPane.setDividerLocation(190);
                splitPane.setDividerSize(20);

                splitPane.setEnabled(false);
                splitPane.setRightComponent(chatter);
                splitPane.setLeftComponent(chatButtonFrame);
                fullFrame.add(splitPane);
                //fullFrame.setVisible(true);


                myFrame = new JFrame("Welcome");
                myFrame.setLayout(new BorderLayout());
                myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                myFrame.setSize(500, 500);
                myFrame.setVisible(true);
                myFrame.getContentPane().setBackground(Color.blue);
                myFrame.setResizable(false);

                signIn = new JButton("Sign in");
                //signIn.setFont(new Font(null, 0, 30));
                signIn.setPreferredSize(new Dimension(150, 40));
                signIn.addActionListener(actionListener);

                signUp = new JButton("Sign up");
                //signUp.setFont(new Font(null, 0, 30));
                signUp.setPreferredSize(new Dimension(150, 40));
                signUp.addActionListener(actionListener);

                password = new JTextField("Password");
                password.setFont(new Font(null, 0, 15));
                password.setMaximumSize(new Dimension(300, 40));
                password.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (password.getText().equals("Password")) {
                            password.setText(null);
                        }
                    }
                });

                userName = new JTextField("Username");
                userName.setFont(new Font(null, 0, 15));
                userName.setMaximumSize(new Dimension(300, 40));
                userName.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (userName.getText().equals("Username"))
                            userName.setText(null);
                    }
                });

                titleLabel = new JLabel("Please enter your information:");
                titleLabel.setFont(new Font(null, 0, 25));
                myTopPanel = new JPanel();
                myTopPanel.add(titleLabel);

                myBottomPanel = new JPanel();
                myBottomPanel.add(signUp);
                myBottomPanel.add(signIn);

                myMiddlePanel = new JPanel();
                myMiddlePanel.setLayout(new BoxLayout(myMiddlePanel, BoxLayout.Y_AXIS));
                myMiddlePanel.add(userName);
                myMiddlePanel.add(password);
                myMiddlePanel.setBackground(Color.blue);

                myFrame.add(myBottomPanel, BorderLayout.SOUTH);
                myFrame.add(myMiddlePanel, BorderLayout.CENTER);
                myFrame.add(myTopPanel, BorderLayout.NORTH);

                signUpFrame = new JFrame("Sign Up");
                signUpFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                signUpFrame.setSize(500, 500);
                signUpFrame.setResizable(false);

                signUpFrame.setTitle("Sign Up");
                signUpFrame.setLayout(new BorderLayout());
                signUpFrame.getContentPane().setBackground(Color.blue);

                signUpMyTopPanel = new JPanel();
                signUpMyMiddlePanel = new JPanel();
                signUpMyMiddlePanel.setLayout(new BoxLayout(signUpMyMiddlePanel, BoxLayout.Y_AXIS));
                signUpMyMiddlePanel.setBackground(Color.blue);
                signUpMyBottomPanel = new JPanel();

                signUpTopPanelLabel = new JLabel();
                signUpTopPanelLabel.setText("Please enter your information below in order to sign up:");
                signUpTopPanelLabel.setFont(new Font(null, 0, 20));
                signUpMyTopPanel.add(signUpTopPanelLabel);
                signUpTopPanelLabel.setFont(new Font(null, 0, 15));

                signUpUsername = new JTextField("Username:");
                signUpUsername.setFont(new Font(null, 0, 15));
                signUpMyMiddlePanel.add(signUpUsername);
                signUpUsername.setMaximumSize(new Dimension(300, 40));

                signUpPassword = new JTextField("Password:");
                signUpPassword.setFont(new Font(null, 0, 15));
                signUpMyMiddlePanel.add(signUpPassword);
                signUpPassword.setMaximumSize(new Dimension(300, 40));

                backButton = new JButton("Back");
                backButton.setSize(new Dimension(signUpMyTopPanel.getWidth(), signUpMyTopPanel.getHeight()));
                backButton.addActionListener(actionListener);
                backButton.setPreferredSize(new Dimension(150, 40));

                confirmSignUp = new JButton("Sign Up");
                confirmSignUp.setSize(new Dimension(signUpMyTopPanel.getWidth(), signUpMyTopPanel.getHeight()));
                confirmSignUp.addActionListener(actionListener);
                signUpMyBottomPanel.add(confirmSignUp);
                confirmSignUp.setPreferredSize(new Dimension(150, 40));
                signUpMyBottomPanel.add(backButton);

                signUpFrame.add(signUpMyMiddlePanel, BorderLayout.CENTER);
                signUpFrame.add(signUpMyBottomPanel, BorderLayout.SOUTH);
                signUpFrame.add(signUpMyTopPanel, BorderLayout.NORTH);
                signUpFrame.setVisible(false);

                //options menu config
                optionsMenu = new JFrame();
                optionsMenu.setVisible(false);
                optionsMenu.setLayout(new BorderLayout());
                optionsMenu.setTitle("Options");
                optionsMenu.setSize(500, 500);
                optionsMenu.setResizable(false);

                topPanelOptionsMenu = new JPanel();
                deleteAccount = new JButton("Delete Account");
                deleteAccount.setMaximumSize(new Dimension(300, 40));
                deleteAccount.addActionListener(actionListener);

                middlePanelForOptions = new JPanel();
                middlePanelForOptions.setBackground(Color.blue);
                middlePanelForOptions.setLayout(new BoxLayout(middlePanelForOptions, BoxLayout.Y_AXIS));


                passwordTextChange = new JTextField("New Password");

                confirmPasswordChange = new JButton("Change Password");
                confirmPasswordChange.setMaximumSize(new Dimension(300, 40));
                confirmPasswordChange.addActionListener(actionListener);
                passwordTextChange.setMaximumSize(new Dimension(300, 40));


                middlePanelForOptions.add(passwordTextChange);
                middlePanelForOptions.add(confirmPasswordChange);
                middlePanelForOptions.add(deleteAccount);


                backButtonToChat = new JButton("Back");
                backButtonToChat.setFont(new Font(null, 0, 15));
                backButtonToChat.addActionListener(actionListener);
                topPanelOptionsMenu.add(backButtonToChat);

                optionsMenuLabel = new JLabel("Account Options");
                optionsMenuLabel.setFont(new Font(null, 0, 15));
                topPanelOptionsMenu.add(optionsMenuLabel);


                optionsMenu.add(topPanelOptionsMenu, BorderLayout.NORTH);
                optionsMenu.add(middlePanelForOptions, BorderLayout.CENTER);

            }
        });

    }


    static ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == signIn) {
                try {
                    getValidAccount(userName.getText(), password.getText());
                } catch (Exception g) {
                    g.printStackTrace();
                }
            }
            if (e.getSource() == signUp) {
                myFrame.setVisible(false);
                signUpFrame.setVisible(true);
            }
            if (e.getSource() == backButton) {
                myFrame.setVisible(true);
                signUpFrame.setVisible(false);
            }
            if (e.getSource() == confirmSignUp) {
                createAccount(signUpUsername.getText(), signUpPassword.getText());
            }
            if (e.getSource() == send) {
                sendNewMessage(composeMessage.getText());
            }
            if (e.getSource() == newConvo) {
                createCo();
            }
            if (e.getSource() == options) {
                optionsMenu.setVisible(true);
                fullFrame.setVisible(false);
            }
            if(e.getSource() == backButtonToChat) {
                fullFrame.setVisible(true);
                optionsMenu.setVisible(false);
            }
            if (e.getSource() == confirmPasswordChange) {
                changePassword(passwordTextChange.getText());
            }
            if(e.getSource() == deleteAccount) {
                deleteAccount(currentUser.getUserName());
            }
        }
    };

    public static void createAccount(String userName, String password) {
        try {
            String sentToServer = "SignUp - " + userName + " - " + password;
            writer.write(sentToServer);
            writer.println();
            writer.flush();

            String response = reader.readLine();

            if (response.equals("Signup Successful")) {
                JOptionPane.showMessageDialog(null, "Your account has " +
                        "been created, return to the login screen.");
            } else {
                JOptionPane.showMessageDialog(null, "The user name you created was taken," +
                        "please try another one.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static void getValidAccount(String userName, String password) throws UnknownHostException, IOException {
        String username = userName;
        try {

            String stringReturned = "Login - " + userName + " - " + password;
            writer.write(stringReturned);
            writer.println();
            writer.flush();

            String response = reader.readLine();
            if (response.equals("Valid User")) {
                myFrame.setVisible(false);
                fullFrame.setVisible(true);
                currentUser = new UserAccount(username, password);
                initJList(username);
            } else {
                JOptionPane.showMessageDialog(null, "Your username or password was incorrect.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Bad connection",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void createCo() {
        int finish = -20;
        ArrayList<String> names = new ArrayList<String>(0);
        String name;
        //int count = currentUser.getConversations().size();
        names.add(currentUser.getUserName());
        do {
            name = JOptionPane.showInputDialog(null, "Enter the UserAccount", "Create Conversation",
                    JOptionPane.QUESTION_MESSAGE);
            if (check(name)) {
                names.add(name);
                System.out.println(name + " added");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid User", "Create Conversation", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("gets to finish");
            finish = JOptionPane.showConfirmDialog(null, "Would you like add another user?", "Create Conversation", JOptionPane.YES_NO_OPTION);
        } while (finish == JOptionPane.YES_OPTION);

        //Conversation n = new Conversation(names, title);
        //conversations.add(n);
        //currentUser.addCo(n);
        String line = "startConversation - ";
        for (int i = 0; i < names.size(); i++) {
            if (i != names.size() - 1) {
                line = line + names.get(i) + " - ";
            } else {
                line = line + names.get(i);
            }
        }
        System.out.println("line sent to startConversation: " + line);
        writer.write(line);
        writer.println();
        writer.flush();

        //updateJList(title);
        //System.out.println("sent update JList" + title);
        //conversations.add(n);
        //currentUser.getConversations().add(n);
        //updateJList(n.getTitle());
        connectUsers(names);

    }
    /*
    private static ArrayList<String> getConversation(String title) {
        do {
            for (int i = 0; i < chats.size(); i++) {
                chats.remove(i);
            }
        } while (!chats.isEmpty());
        try {
            PrintWriter pt = new PrintWriter(socket.getOutputStream());
            BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pt.write(title);
            pt.flush();
            String line = read.readLine();
            String[] split = line.split("\n");
            for (int i = 0; i < split.length; i++) {
                chats.add(split[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chats;
    }
     */
    private static void connectUsers(ArrayList<String> names) { // this sends the users that are in a conversation in order to connect them all
        for (int i = 0; i < names.size(); i++) {
            writer.write(names.get(i));
            writer.println();
            writer.flush();
        }
    }

    /*
    public static boolean check (String name) { //this will check if the user exists
        boolean checker = false;
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
            System.out.println("Sent");
            writer.println("checkValidUser - " + name);
            //writer.println();
            writer.flush();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println(response);
            if (response.equals("User is Valid " + name)) {
            }
     */

    public static boolean check(String name) { //this will check if the user exists
        boolean checker = false;
        try {
            writer.write("checkValidUser - " + name);
            writer.println();
            writer.flush();
            String response = reader.readLine();
            if (response.equals("User is Valid " + name)) {
                checker = true;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }

        return checker;
    }

    public static void sendNewMessage(String message) {
        if (message.equals("")) {
            JOptionPane.showMessageDialog(null, "Your message is empty, add a value first to send."
                    , "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            String[] messageWordLimitTest = message.split(" ");
            if (messageWordLimitTest.length > 100) {
                JOptionPane.showMessageDialog(null, "Your message is " + (messageWordLimitTest.length - 100) + "word(s) too long."
                        + "Please shorten it.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // else send message to server
    }

    private static void initJList(String userName) {
        try {
            writer.write("allConversations - " + userName);
            writer.println();
            writer.flush();
            String conversationsNonSplit = reader.readLine();
            if (conversationsNonSplit != null) {
                String[] conversationsSplit = conversationsNonSplit.split("Conversation - ");
                for (int i = 0; i < conversationsSplit.length; i++) {
                    //TODO make sure allConversations sends over the right data
                    //conversations.add(new Conversation(/*data received from server*/));
                    //add to conversationTitles, call the updateJlsit
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }

        client.repaint();

    }

    public static void updateJList(String conversationTitle) {
        if(conversationTitles != null) {
            String[] tempArray = new String[conversationTitles.length + 1];
            for (int i = 0; i < conversationTitles.length; i++) {
                tempArray[i] = conversationTitles[i];
            }
            tempArray[tempArray.length - 1] = conversationTitle;
            conversationTitles = tempArray;
        }
    }

    public static void changePassword(String newPassword) {
        try {
            writer.write("ChangePassword" + " - " + currentUser.getUserName() + " - " + newPassword);
            writer.println();
            writer.flush();
            String response = reader.readLine();

            if (response.equals("Password Changed"))
                JOptionPane.showMessageDialog(null, "Your password has been successfully changed"
                        , "Success", JOptionPane.OK_OPTION);
            else {
                JOptionPane.showMessageDialog(null, "Your password wasn't able to be changed"
                        , "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException io) {
            System.out.println("Password did not change.");
            io.printStackTrace();
        }
    }
    public static void deleteAccount(String userName) {
        writer.write("DeleteUser" + " - " + currentUser.getUserName());
        writer.println();
        writer.flush();
    }
}
