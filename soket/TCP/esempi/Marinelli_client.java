import java.io.*;
import java.net.*;

public class Marinelli_client {

    public static void main(String[] args) {
        try {
            // connessione al server, creo il soket
            Socket s = new Socket("172.16.7.202", 2007); // IP + porta

            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            // s.getOutputStream() "flusso grezzo" dei dati

            //lettura
            // buffer vi permette di leggere una stringa intera  e non singoli caratteri
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.writeBytes("sono bello"+'\n'); // \n permette di concludere il messaggio 
            // altrimenti il server resta in attesa

            String messaggio = in.readLine();
            System.out.println("Server: " + messaggio);
            s.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Host non trovato ");

        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server non trovato");
        }
    }
}
