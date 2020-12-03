# CS180-Final-Project
This final project is a GUI messaging app with a client and server. 

Detailed description for each class, including testing performed. 

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
  
- Client description
  
  - Method desriptions
- ServerThread description
  - Method desriptions
- Conversation description
  - Method descriptions
- How to run the program / sequence in which things need to be performed in order to avoid bugs. 




