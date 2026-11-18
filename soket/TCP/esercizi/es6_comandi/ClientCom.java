import java.io.*;
import java.net.*;
import java.util.*;

public class ClientCom {

    public static void main(String[] args) throws UnknownHostException, IOException{

            // connessione al server, creo il soket
            Socket s = new Socket("localhost", 3456); // IP + porta

            Scanner scn = new Scanner(System.in);
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            // in.readLine();
            String messaggio = "";
            boolean end = false;

            do {
                // leggo il messaggio dall'utente 1
                messaggio = scn.nextLine();
                out.writeBytes(messaggio + '\n'); // mando il messaggio al server 2
                System.out.println("Client > "+messaggio);

                // ricevo la risposta dal server 3
                String risp = in.readLine();
                end = Boolean.parseBoolean(in.readLine());
                System.out.println("Server < "+ risp);

            } while(!end);    

            s.close();
    }
}
