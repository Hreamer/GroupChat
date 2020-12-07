import java.io.*;
import java.net.*;

public class Server {
    static final int PORT = 6174; //Kaprekar's routine final number

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket socket = serverSocket.accept();

            new ServerThread(socket).start();
        }
    }
}
