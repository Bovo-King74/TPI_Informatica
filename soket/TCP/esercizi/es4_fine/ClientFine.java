import java.io.*;
import java.net.*;
import java.util.*;

public class ClientFine {

    public static void main(String[] args) {

        try {
            // connessione al server, creo il soket
            Socket s = new Socket("localhost", 3456); // IP + porta

            Scanner scn = new Scanner(System.in);
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            // in.readLine();
            boolean end = true;
            String messaggio = "";

            do {
                // leggo il messaggio dall'utente
                messaggio = scn.nextLine();

                out.writeBytes(messaggio + '\n'); // mando il messaggio al server

                // ricevo la risposta dal server
                String ris = in.readLine();
                System.out.println(ris);

            } while (!messaggio.toLowerCase().equals("fine"));

            s.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Host non trovato ");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server non trovato");
        }
    }
}
