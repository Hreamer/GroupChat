# CS180-Final-Project
This final project is a GUI messaging app with a client and server. 

Detailed description for each class, including testing performed. 
UserAccount
- UserAccount is an object that stores the user's username and password. It is created by the Client to store said information on the client side.
  - public UserAccount(String userName, String password)
    - The constructor takes a username and password, and initilizes its own username and password variables with it. It also creates a new ArrayList with catagory Conversation
  - public String getUserName()
    - This method returns the username of the UserAccount as a String
  - public ArrayList<Conversation> getConversations()
    - This method returns an ArrayList<Conversation> which is the current list of conversations for this UserAccount
  - public Conversation findConversation(String title)
    - This method returns the Conversation that the user wants by taking in the title of the conversation as a String and searching through the list of Conversations to find said conversation
  - public void addCo(Conversation conversation)
    - This method takes in a Conversation and adds it to the current ArrayList of Conversations that exists in this UserAccount
  - public String getPassword()
    - This method returns in the form of a String the password stored in this UserAccount.
  - public void setUserName(String userName)
    - This method takes in a String username and sets the username stored in this UserAccount to said username.
  - public void setPassword(String password)
    - This method takes in a String password and sets the password stored in this UserAccount to said password.
  - public String toString()
    - This method overrides the toString method, allowing us return information fromt he UserAccount in this format (parantheses around variable names): UserAccount{userName='(username)', password='(password)'}
  
Client
- Client is a Class constructed by the user and generates the GUI used by the user for the rest of the program. It takes in a socket to connect to the server and will recieve information and update the GUI in real time.
  - public static void main (String[] args) throws UnknowHostException, IOException, ClassNotFoundException
    - The main method is the one that starts the whole system at the start. It creates a Socket and tries to find a corresponding socket at the server, and begins the Run method. The run method begins to generate the actual GUI that is used by the client, starting with the option to either sign in or sign up with an account. 
  - static ActionListener = new ActionListener()
    - An actionListener object is created along with the methods it uses
  - public void actionPerformed(ActionEvent e)
    - This method is created under actionListener, and will further adjust the GUI depending on what is needed. It can take input from you signing up, signing in, pressing back anywhere, confirming signup, sending a message, creating a new conversation, bringing up the options menu, changing your password, or deleting your account
    - SignUp
      - makes the signUpFrame visible
    - backButton
      - sets the signin frame to be visible from the signUpFrame
    - confirmSignUp
      - calls createAccount with input from signUpUsername and signUpPassword
    - send
      - calls sendNewMessage with input from composeMessage
    - new Convo
      - calls createCo
    - options
      - sets the option menu to be visible
    - backButtonToChat
      - leaves option menu and goes to chat menu
    - confirmPasswordChange
      - calls changePassword with input from passwordTextChange
    - deleteAccount
      - calls deleteAccount using the current username
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
    - updates the current Jlist to let users see the new list of conversations available to them.
  - public static void changePassword(String newPassword) 
    - lets user change their passowrd, sends command and new password to server where the file is changed. If the response is "Password Changed", informs user that their password has been changed, else lets them know it could not be changed.
  - public static void deleteAccount(String userName)
    - Asks server to delete the account associated with the given username
  - public static Conversation createConversationObject(String convoName)
    - Creates a new conversation from the input string. It splits the input String into an array containing the users of the conversation and creates a new Conversation object with this information, which is returned.
  
Conversation
- Conversation is an class meant to store the users, name, and file of a conversation.
  - public Conversation(ArrayList<String> users, String title, String filename)
    - initializes the object Conversation
  - public void setFilename(String filename)
    - sets the filename to the input
  - public String getFilename()
    - returns the filename
  - public ArrayList<String> getUsers()
    - returns the ArrayList of users
  - public void setUsers(ArrayList<String> users)
    - sets the ArrayList of users to the input
  - public String getTitle()
    - returns the title
  - public void setTItle(String title)
    - sets the title to the input
Server
- The server class is just there to create a server for the program to run off of
  - public static void main (String[] args) throws IOException
    - creates the serverthread for as many connections as needed.

ServerThread
- ServerThread is the main part of the backend side of the program. It is created for every client that is connected to the server.
  - public ServerThread(Socket socket)
    - sets the socket to the input socket
  - public void run()
    - This is where most of the server runs, and as such each command it can recieve will be its own line. It will wait for a command from the server and then react to it.
    - Login
      - This is used to check if the login is valid or not
    - SignUp
      - This is used to sign up a new user, if the username is taken it will not sign up successfully. Returns to the client a value depending on whether or not it succeeded.
    - DeleteUser
      - This is used to delete a user from a conversation so they do not see anything from it anymore.
    - CheckValidUser
      - This is used to check if the user the client gives is a valid one or not.
    - getAllConversationsInvolved
      - This is used to get all conversations the user is involved in.
    - startConversation
      - This is used to create a new conversation, a file is made for the conversation with its name being the reformatted string of users(variable fileName).
    - ChangePassword
      - This is used to change the password for a user in the file containing all users and passwords
    - updateConversation
      - This is used to update messages in a conversation and add them to the logs.
    - allConversations  
      - This is used to retrieve all conversations that a user is involved with.
  - public String isValidLogin(String userName, String password) throws IOException
    - checks if the username and password is a valid login. Returns "Valid User" or "Invalid User"
  - public boolean addUser(String userName, String password) throws IOException
    - adds a new user to the list of users, returns true is successful, false if not


- How to run the program / sequence in which things need to be performed in order to avoid bugs.
Usernames and passwords should not contain spaces in them. An account is needed to access most other functions, which is created from signing up. Once a chat is deleted, you cannot try to recreate said chat without causing errors. You also cannot create a chat with yourself without causing errors. You cannot edit the coversations.txt file at all or it will cause problems. 

- Manual Testing
As all of our methods involve either JFrames or Socket, we have had to do manual testing to see if everything was working correctly.
Tests will be listed in this format:
"More generic testing"
- Test Details
(Method Test)
- 

"Entering SignUp Menu"
Note: Unless specified specifically, it can be assumed that all tests listed below happen in order, and that the information in the files still remain there
- Clicked on Signup button from the SignIn menu which brought up the signUp Menu. Successfully entered SignUp Menu.
  (createAccount) Successful
- Entered username "Steve" and password "Rong", successfully sent to server and written in file as Steve - Rong
  (createAccount) failure
- With the input from the previous test still there, the same username and password is inputted again, but this time the client displays the message "The username you created was taken,please try another one"
  (getValidAccount) Success
- Tried using the username "Steve" and password "Rong", login is successful and 
  (getValidAccount) Failure
- Tried using the username "Rong" and password "Steve", Client displays message "Your username or password was incorrect"
// A second client was created here with username "alphahail" and password "test1"
  (createCo) Success
- Tried to input the other account "alphahail" on the account Steve, opened up new conversation between the two. Conversation name is in the Conversations.txt file and a new file was created for the conversation. Both sides see a new conversation option with the name of the other account.
  (createCo) Failure
- Tried to input account "Rong" for accounts to connect to, failed to open and client displayed "Invalid User"
  (sendNewMessage) Success
- Sent a message from alphahail "try 1", shows up on both ends
  (sendNewMessage) Failure
- Sent a message from alphahail of 102 words, fails to send and client displays message
  (changePassword)
- Changed password for alphahail to test2, password change is visible in the files.
  (deleteAccount)
- Deleted account alphahail through the button, account no longer visible in the files.




