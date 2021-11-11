import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int portNumber = 77;
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Rsa encryptor = new Rsa();
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter putIncliet = new PrintWriter(clientSocket.getOutputStream(), true);  
            ServerThreadReader t = new ServerThreadReader(clientSocket, encryptor);
            new Thread(t).start();
            putIncliet.println(encryptor.getKey());   
            System.out.println(encryptor.keyFriend);           
            BufferedReader getFromItSelf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String userInput;
            while ((userInput = in.readLine()) != null) {
                System.out.println(encryptor.encrypt(userInput));
                putIncliet.write(encryptor.encrypt(userInput));
            }
        
    }
}