# CS180-Final-Project
This final project is a GUI messaging app with a client and server. 

Detailed description for each class, including testing performed. 
UserAccount
- UserAccount is an object that stores the user's username and password. It is created by the Client to store said information on the client side.
  - Constructor
    - The constructor takes a username and password, and initilizes its own username and password variables with it. It also creates a new ArrayList with catagory Conversation
  - getUserName
    - This method returns the username of the UserAccount as a String
  - getConversations
    - This method returns an ArrayList<Conversation> which is the current list of conversations for this UserAccount
  - findConversations
    - This method returns the Conversation that the user wants by taking in the title of the conversation as a String and searching through the list of Conversations to find said conversation
  - addCo
    - This method takes in a Conversation and adds it to the current ArrayList of Conversations that exists in this UserAccount
  - getPassword
    - This method returns in the form of a String the password stored in this UserAccount.
  - setUserName 
    - This method takes in a String username and sets the username stored in this UserAccount to said username.
  - setPassWord 
    - This method takes in a String password and sets the password stored in this UserAccount to said password.
  - toString
    - This method overrides the toString method, allowing us return information fromt he UserAccount in this format (parantheses around variable names): UserAccount{userName='(username)', password='(password)'}
  
Client
- Client is a Class constructed by the user and generates the GUI used by the user for the rest of the program. It takes in a socket to connect to the server and will recieve information and update the GUI in real time.
  - public static void main (String[] args) throws UnknowHostException, IOException, ClassNotFoundException
    - The main method is the one that starts the whole system at the start. It creates a Socket and tries to find a corresponding socket at the server, and begins the Run method. The run method begins to generate the actual GUI that is used by the client, starting with the option to either sign in or sign up with an account. 
  - static ActionListener = new ActionListener()
    - An actionListener object is created along with the methods it uses
  - public void actionPerformed(ActionEvent e)
    - This method is created under actionListener, and will further adjust the GUI depending on what is needed. It can take input from you signing up, signing in, pressing back anywhere, confirming signup, sending a message, creating a new conversation, bringing up the options menu, changing your password, or deleting your account
    - TODO, add a detailed description of what happens with the button clicks. Reminder to do the same on the server side.
  - public static void createAccount(String userName, String password)
    - Asks the server if the given username isn't taken, if it is available tells the user that account has been created, else tell them that the username is already taken.
  - public static void getValidAccount(String userName, String password) throws UnknownHostException, IOException
    - This method is used when the User tries to log into their account. It takes the given username and password and asks server if it's a valid account. Used for signing in. If it is valid it creates a new userAccount and sets the currentUser to it. 
  - public static void createCo()
    - This method creates a new conversation for the user. It will prompt the user for the accounts that they want to add to said conversation. It sends a String with a command and the name of the file to the server, where the file is created. 
  - private static void connectUsers(ArrayList<String> names)
    - This method sends the server all of the users that are involved in a conversation from an inputted ArrayList of type String. 
  - public static boolean check(String name)
    - This method returns a boolean value depending on whether or not the user exists. It asks the server if said name exists.
  - public static void sendNewMessage(String message)
    - Sends the message to the server to send to everyone else who is in the conversation. If the message is empty, It will inform the user that the chat box is empty, and if it is over 100 characters long, the user will be requested to shorten the message.
  - private static void initJList(String userName)
    - Gets all conversations from the server that have the user as a participant.
  - public static void updateJList(String conversationTitle)
    - increases size of converservationTitles by one every time its invoked. Used for when the array reaches maximum capacity
  - public static void changePassword(String newPassword) 
    - lets user change their passowrd, sends command and new password to server where the file is changed. If the response is "Password Changed", informs user that their password has been changed, else lets them know it could not be changed.
  - public static void deleteAccount(String userName)
    - Asks server to delete the account associated with the given username
  - public static Conversation createConversationObject(String convoName)
    - Creates a new conversation from the input string. It splits the input String into an array containing the users of the conversation and creates a new Conversation object with this information
- ServerThread description
  - Method desriptions
- Conversation description
  - Method descriptions
- How to run the program / sequence in which things need to be performed in order to avoid bugs. 




