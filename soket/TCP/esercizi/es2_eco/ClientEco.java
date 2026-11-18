import java.io.*;
import java.net.*;

public class ClientEco {

    public static void main(String[] args) {
        try {
            // connessione al server, creo il soket
            Socket s = new Socket("localhost", 3456); // IP + porta

            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            // s.getOutputStream() "flusso grezzo" dei dati
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            // buffer vi permette di leggere una stringa intera  e non singoli caratteri


            System.out.println("CLIENT: porta locale "+s.getLocalPort()+", server "+s.getInetAddress()+":"+s.getPort());



            // out.writeBytes("Hello" + '\n'); 
            // \n permette di concludere il messaggio 
            // altrimenti il server resta in attesa
            //String messaggio = in.readLine();
            //System.out.println("ECO: " + messaggio);
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
