import java.io.*;
import java.net.*;
import java.util.*;

public class ServerInd {

    public static void main(String[] args) throws IOException {
        // connessione al server
        ServerSocket server = new ServerSocket(3456); // attivazione porta
        System.out.println("SERVER < in ascolto sulla porta 3456...");

        Socket client = server.accept(); // attende un client e crea il socket
        System.out.println("SERVER < connessione stabilita con il client: " + client.getInetAddress().getHostAddress());

        // creo lo stream di IO
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        // invio la consesgna al client
        out.writeBytes("SERVER < INDOVINA IL NUMERO DA 1 A 100 CHE STO PENSANDO\n");

        // dati
        int segreto = new Random().nextInt(100) + 1;
        System.out.println("penso a: "+segreto);
        String messaggio = "";
        int count = 0;
        String risposta = "";
        boolean trovato = false;

        // ciclo che mi permette di rimanere connesso
        while ((messaggio = in.readLine()) != null || !trovato) {
            // aspetta il messaggio dal client (considero che l'utente abbia un QI elevato e non erri l'invio del messaggio)
            int num = Integer.parseInt(messaggio.trim());

            if(num > 100 || num < 1){
                risposta = "NUMERO ERRATO\n";
            }else if (num == segreto) {
                count++;
                risposta = "INDOVINATO in "+count+" tentativi\n";
                trovato = true;
            }else if (num > segreto) { // PIU BASSO
                risposta = "PIU BASSO\n";
                count++;
            }else{                     // PIU ALTO
                risposta = "PIU ALTO\n";
                count++;
            }
            // stampo la risposta
            out.writeBytes(risposta);
            out.writeBytes(""+trovato+'\n');
        }

        client.close();
        System.out.println("SERVER < connessione chiusa");
    }
}