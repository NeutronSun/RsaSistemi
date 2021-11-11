import java.io.*;
import java.net.*;

public class ServerThreadReader implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private String clientKey;
    private Rsa rsa;
    public ServerThreadReader(Socket s, Rsa r) throws IOException{
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        rsa = r;
    }
    public void run() {
        while (true) {
            String s;
            try {
                s = in.readLine();
                if(s.startsWith("pk")){
                rsa.setFriendKey(s.substring(2));
                System.out.println("i gori the chi");
                }else
                System.out.println("<Client> " + rsa.decrypt(s));
            } catch (IOException e) {
                System.out.println("zeg");
                return;
            }
        }
    }
    
}
