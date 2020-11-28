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
    //chat board
    static JFrame chat;
    static JPanel jp;
    static JScrollPane sp;
    static Container cont;
    static JPanel up;
    static ArrayList<Conversation> con = new ArrayList<>();
    //message board
    private static JButton delete;
    private static JButton back;
    private static JTextField deleteMessage;
    private static JButton send;
    private static JButton create;

    private static JTextField composeMessage;
    public static Socket socket;
    static ArrayList<String> chats = new ArrayList<>();
    static BufferedReader reader;
    static PrintWriter pw;
    static UserAccount currentUser;
    static JFrame chatter;
    static Container content;
    static JTextArea textArea;
    static JPanel top;
    static JPanel bottom;



    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
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
                //conversation board

                chat = new JFrame("Conversations");
                cont = chat.getContentPane();
                cont.setLayout(new BorderLayout());
                chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jp = new JPanel();
                jp.setPreferredSize(new Dimension(500,400));
                sp = new JScrollPane(jp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                sp.setPreferredSize(new Dimension(500, 400));
                up = new JPanel();
                create = new JButton("Create Conversation");
                create.addActionListener(actionListener);
                for (int i = 0; i < con.size(); i++) {
                    jp.add(con.get(i).getButton());
                }
                top.add(create);
                content.add(up, BorderLayout.NORTH);
                content.add(sp, BorderLayout.CENTER);
                chat.setSize(500,500);
                chat.setResizable(false);
                chat.setVisible(false);

                //messenger board
                chatter = new JFrame("Conversation");
                composeMessage = new JTextField(20);
                chatter.setSize(500, 500);
                chatter.setResizable(false);
                content = chatter.getContentPane();
                content.setLayout(new BorderLayout());
                chatter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                textArea = new JTextArea(10, 20);
                top = new JPanel();
                bottom = new JPanel();
                back = new JButton("Back");
                back.addActionListener(actionListener);
                delete = new JButton("Delete");
                deleteMessage = new JTextField("What message would you like to delete?...");
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

                JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                for (int i = 0; i < chats.size(); i++) {
                    textArea.append("  " + chats.get(i));
                    textArea.append("\n");
                    textArea.append("\n");
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                }
                top.add(back);
                top.add(delete);
                top.add(deleteMessage);
                bottom.add(send);
                bottom.add(composeMessage);

                content.add(bottom, BorderLayout.SOUTH);
                content.add(top, BorderLayout.NORTH);
                content.add(scroll, BorderLayout.CENTER);
                chatter.setSize(500, 500);
                chatter.setVisible(false);
                chatter.setLocationRelativeTo(null);

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
            if (e.getSource() == back) {
                //add logic to hide panel
            }
            if (e.getSource() == create) {
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
            if (response.equals("Account Created")) {
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
            pw.write(userName);
            pw.write(password);
            pw.flush();

            String response = reader.readLine();
            if (response.equals("Valid User")) {
                ObjectInputStream get = new ObjectInputStream(socket.getInputStream());
                UserAccount user = (UserAccount) get.readObject();
                currentUser = user;
                con = user.getConversations();
                myFrame.setVisible(false);
                chat.setVisible(true);
                chatter.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Your username or password was incorrect.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Bad connection",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void createCo() {
        int finish = 1;
        ArrayList<String> names = new ArrayList<>();
        String name;
        String title;
        int count = currentUser.getConversations().size();
        do {
            name = JOptionPane.showInputDialog(null, "Enter the UserAccount", "Create Conversation",
                    JOptionPane.QUESTION_MESSAGE);
            if (check(name)) {
                names.add(name);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid User", "Create Conversation", JOptionPane.ERROR_MESSAGE);
            }
            finish = JOptionPane.showConfirmDialog(null, "Would you like add another user?", "Create Conversation", JOptionPane.YES_NO_OPTION);
        } while (finish == JOptionPane.YES_OPTION);

        title = JOptionPane.showInputDialog(null, "Enter the Title", "Create Conversation",
                JOptionPane.QUESTION_MESSAGE);

        Conversation n = new Conversation(names, title, chatter, chats, socket);
        chats = getConversation(title);
        con.add(n);

        jp.add(n.getButton());
        connectUsers(names);
    }
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

}
