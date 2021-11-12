import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        String hostName = "127.0.0.1";
        int portNumber = 77;
        Socket echoSocket = new Socket(hostName, portNumber);
        PrintWriter putInServer = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Rsa rsa = new Rsa();
        putInServer.println(rsa.getKey()); 
        ServerClientReader rdr = new ServerClientReader(echoSocket, rsa);
        new Thread(rdr).start();
        String userInput;
        while ((userInput = in.readLine()) != null) {
            putInServer.println(rsa.encrypt(userInput));
        }
    }
}