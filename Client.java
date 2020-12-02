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


    //message board
    private static JButton delete;
    private static JButton back;
    private static JTextField deleteMessage;
    private static JButton send;

    private static JTextField composeMessage;
    public static Socket socket;
    //static ArrayList<String> chats = new ArrayList<>();
    static BufferedReader reader;
    static PrintWriter pw;
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


    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
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
                if (conversationTitles != null && conversationTitles.length > 0) {
                    list = new JList<String>(conversationTitles);
                    JScrollPane listScroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    list.setLayoutOrientation(JList.VERTICAL);
                    list.setVisibleRowCount(-1);
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


            }
        });

        try {
            serverClient();
        } catch (UnknownHostException un) {
            JOptionPane.showMessageDialog(null,
                    "A connection with the server couldn't be established.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null,
                    "A connection with the server couldn't be established.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    static ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == signIn) {
                getValidAccount(userName.getText(), password.getText());
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
        }
    };

    public static void createAccount(String userName, String password) {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            String sentToServer = "SignUp - " + userName + " - " + password;
            writer.write(sentToServer);
            writer.println();
            writer.flush();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

    public static void getValidAccount(String userName, String password) {

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());

            String stringReturned = "Login - " + userName + " - " + password;
            pw.write(stringReturned);
            pw.println();
            pw.flush();

            String response = reader.readLine();
            if (response.equals("Valid User")) {
                myFrame.setVisible(false);
                fullFrame.setVisible(true);
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
        String title;
        //int count = currentUser.getConversations().size();
        do {
            name = JOptionPane.showInputDialog(null, "Enter the UserAccount", "Create Conversation",
                    JOptionPane.QUESTION_MESSAGE);
            if (check(name)) {
                names.add(name);
                System.out.println("name " + name + " added");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid User", "Create Conversation", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("gets to finish");
            finish = JOptionPane.showConfirmDialog(null, "Would you like add another user?", "Create Conversation", JOptionPane.YES_NO_OPTION);
        } while (finish == JOptionPane.YES_OPTION);

        title = JOptionPane.showInputDialog(null, "Enter the Title", "Create Conversation",
                JOptionPane.QUESTION_MESSAGE);

        Conversation n = new Conversation(names, title);
        //chats = getConversation(title);
        conversations.add(n);
        currentUser.getConversations().add(n);
        updateJList(n.getTitle());
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
        try {
            PrintWriter pr = new PrintWriter(socket.getOutputStream());
            for (int i = 0; i < names.size(); i++) {
                pr.write(names.get(i));
                pr.flush();
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    public static boolean check(String name) { //this will check if the user exists
        boolean checker = false;
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.write(name);
            writer.flush();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            if (response.equals(name)) {
                checker = true;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return checker;
    }

    public static void serverClient() throws UnknownHostException, IOException {
        socket = new Socket(hostname, portNumber);
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

    private static void updateJList(String conversationTitle) {
        //if a chat is created
        String[] tempArray = new String[conversationTitles.length + 1];
        for (int i = 0; i < conversationTitles.length; i++) {
            tempArray[i] = conversationTitles[i];
        }
        tempArray[tempArray.length - 1] = conversationTitle;
        conversationTitles = tempArray;
        //list.ensureIndexIsVisible(list.getLength());
    }
    public static void changePassword(String newPassword) {
        newPassword = ""; //change to text field when it is created
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            writer.write("ChangePassword" + " - " + currentUser.getUserName() + " - " + newPassword);
            writer.println();
            writer.flush();
            reader.readLine();
            String response = reader.readLine();

            if (response.equals("Password Changed")) {
                JOptionPane.showMessageDialog(null, "Your password has been successfully changed"
                        , "Error", JOptionPane.OK_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "Your password wasn't able to be changed"
                        , "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException io) {
            System.out.println("Password did not change.");
        }
    }


}
