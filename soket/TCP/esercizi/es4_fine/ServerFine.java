import java.io.*;
import java.net.*;

public class ServerFine {

    public static void main(String[] args) throws IOException {
        // connessione al server
        ServerSocket server = new ServerSocket(3456); // attivazione porta
        System.out.println("SERVER < in ascolto sulla porta 3456...");

        Socket client = server.accept(); // attende un client e crea il socket
        System.out.println("SERVER < connessione stabilita con il client: " + client.getInetAddress().getHostAddress());

        // creo lo stream di IO
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String messaggio = "";
        int count = 0;

        // ciclo che mi permette di rimanere connesso
        while ((messaggio = in.readLine()) != null) {
            // aspetta il messaggio dal client
            String risposta = "";

            if (messaggio.toLowerCase().equals("fine")) {
                risposta = "Server < Ciao! Hai inviato " + count + " messaggi\n";
            } else {
                count++;
                risposta = "Server < " + count + ": " + messaggio.toUpperCase()+"\n";
            }
            out.writeBytes(risposta);
        }

        client.close();
        System.out.println("SERVER < connessione chiusa");
    }
}