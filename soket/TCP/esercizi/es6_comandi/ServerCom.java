import java.io.*;
import java.net.*;

public class ServerCom {

    public static void main(String[] args) throws IOException {
        // connessione al server
        ServerSocket server = new ServerSocket(3456); // attivazione porta
        System.out.println("SERVER < in ascolto sulla porta 3456...");

        Socket client = server.accept(); // attende un client e crea il socket
        System.out.println("SERVER < connessione stabilita con il client: " + client.getInetAddress().getHostAddress());

        // creo lo stream di IO
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        // dati
        String messaggio = "";
        int count = 0;
        String risposta = "";
        boolean end = false;

        // ciclo che mi permette di rimanere connesso
        while ((messaggio = in.readLine()) != null || !end) {
            // aspetta il messaggio dal client (considero che l'utente abbia un QI elevato e non erri l'invio del messaggio)
            
            String[] parti = messaggio.trim().split(" ", 2);
            switch (parti[0].toLowerCase()) {
                case "ora":
                    
                    break;
                case "maiusc":
                    if (contrArg(parti)) {
                        
                    }else{
                        risposta = "ERR: argomento mancante";
                    }
                    break;
                case "conta":
                    if (contrArg(parti)) {
                        
                    }else{
                        risposta = "ERR: argomento mancante";
                    }
                    break;
                case "inverti":
                    if (contrArg(parti)) {
                        
                    }else{
                        risposta = "ERR: argomento mancante";
                    }
                    break;
                case "esci":
                    
                    break;
                default:

                    ;
            }

            // stampo la risposta
            out.writeBytes(risposta+'\n');
            out.writeBytes(""+end+'\n');
        }

        client.close();
        System.out.println("SERVER < connessione chiusa");
    }

    public static boolean contrArg(String parti[]){
        if (parti.length < 2) {
            return false;
        }
        return true;
    }
}