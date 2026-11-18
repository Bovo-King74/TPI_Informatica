import java.io.*;
import java.net.*;
import java.util.*;

public class ClientInd {

    public static void main(String[] args) {

        try {
            // connessione al server, creo il soket
            Socket s = new Socket("localhost", 3456); // IP + porta

            Scanner scn = new Scanner(System.in);
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            // leggo la consegna
            System.out.println(in.readLine());

            // in.readLine();
            String messaggio = "";
            boolean trovato = false;

            do {
                // leggo il messaggio dall'utente
                messaggio = scn.nextLine();
                out.writeBytes(messaggio + '\n'); // mando il messaggio al server

                // ricevo la risposta dal server (PIU ALTO - PIU BASSO - ERRORE)
                String ris = in.readLine();
                trovato = Boolean.parseBoolean(in.readLine());
                System.out.println(ris);

            } while(!trovato);    

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
