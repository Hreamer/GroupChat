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
  - Main
    - The main method is the one that starts the whole system at the start. It creates a Socket and tries to find a corresponding socket at the server, and begins the Run method. The run method begins to generate the actual GUI that is used by the client, starting with the option to either sign in or sign up with an account. 
  - actionListener 
    - This is where an actionListener object is created, with methods that it uses being created inside of it.
  - actionPerformed 
    - This method is created under actionListener, and will further adjust the GUI depending on what is needed. It can take input from you signing up, signing in, pressing back anywhere, confirming signup, sending a message, creating a new conversation, bringing up the options menu, changing your password, or deleting your account
  - createAccount
    - This method takes in a username and password given through the GUI and sends a String to the server telling it to sign up said user for an account. If it doesnt recieve the response "Signup Successful", it will tell the user through the GUI that the username was taken.
  - getValidAccount
    - This method is used when the User tries to log into their account. It takes the users username and password from the GUI, and send it to the server in the form of a String. It looks for input back from the server if it was real or not, and if it is will tell the user so, if not it will tell the user their username or password was incorrect.
  - createCo
    - This method creates a new conversation for the user. It will prompt the user for the accounts that they want to add to said conversation, and once all users are inputted, will create a file to store their conversations in. It sends a String with a command and the name of the file to the server, where the file is created. 
  - connectUsers
    - This method creates 
- ServerThread description
  - Method desriptions
- Conversation description
  - Method descriptions
- How to run the program / sequence in which things need to be performed in order to avoid bugs. 




