import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        int portNumber = 77;
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("waiting someone to talk with" + new StringBuilder().appendCodePoint(0x1F62A).toString());
            Rsa encryptor = new Rsa();
            Socket clientSocket = serverSocket.accept();
            System.out.println("matched with a stranger" + new StringBuilder().appendCodePoint(0x1F497).toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter putIncliet = new PrintWriter(clientSocket.getOutputStream(), true);  
            ServerThreadReader t = new ServerThreadReader(clientSocket, encryptor);
            new Thread(t).start();
            putIncliet.println(encryptor.getKey());      
            String userInput;
            
            while(encryptor.keyFriend == null){Thread.currentThread().sleep(1000);}
            
            putIncliet.println(encryptor.encrypt("We can talk now"));
            while ((userInput = in.readLine()) != null) {
                putIncliet.println(encryptor.encrypt(userInput));
            }
        
    }
    
}