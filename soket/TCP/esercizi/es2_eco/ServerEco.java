import java.io.*;
import java.net.*;

public class ServerEco {

    public static void main(String[] args) throws IOException {
        // connessione al server
        ServerSocket server = new ServerSocket(3456); // attivazione porta
        System.out.println("SERVER in ascolto sulla porta 3456...");

        //
        Socket client = server.accept(); // attende un client e crea il socket
        server.close(); // niente altri client

        // creo lo stream di IO
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        
        // IP client
        InetAddress ca = client.getInetAddress();
        // porta client
        Object cp = client.getPort();

        System.out.println("SERVER: connesso con "+ca+":"+cp);

        //String ricevuta = in.readLine();
        //System.out.println(ricevuta);
        
        //out.writeBytes(ricevuta + '\n');
        
        client.close();
        System.out.println("SERVER: connessione chiusa");
    }

}