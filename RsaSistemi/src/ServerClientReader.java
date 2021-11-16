import java.io.*;
import java.net.*;

public class ServerClientReader implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private String serverKey;
    private Rsa rsa;
    public ServerClientReader(Socket s, Rsa r) throws IOException{
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
                }else
                    System.out.println("<Server> " + rsa.decrypt(s));

            } catch (IOException e) {
                System.out.println("zeg");
                return;
            }
        }
    }
    
}
