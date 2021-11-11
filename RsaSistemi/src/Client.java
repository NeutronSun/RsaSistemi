import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        String hostName = "127.0.0.1";
        int portNumber = 77;

        Socket echoSocket = new Socket(hostName, portNumber);
        PrintWriter putInServer = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader getFromServer = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Rsa rsa = new Rsa();
        Rsa rsaf = new Rsa();
        putInServer.println(rsa.getKey()); 
        System.out.println("sent");
        ServerClientReader rdr = new ServerClientReader(echoSocket, rsa);
        new Thread(rdr).start();
        System.out.println(rsa.keyFriend);
        String userInput;
        while ((userInput = in.readLine()) != null) {
            putInServer.println(rsa.encrypt(userInput));
        }
       
    }
}