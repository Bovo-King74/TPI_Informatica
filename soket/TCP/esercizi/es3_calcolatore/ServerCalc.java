import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerCalc {

    public static void main(String[] args) throws IOException {
        // connessione al server
        ServerSocket server = new ServerSocket(3456); // attivazione porta
        System.out.println("SERVER in ascolto sulla porta 3456...");

        Socket client = server.accept(); // attende un client e crea il socket

        // creo lo stream di IO
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean end = true;

        

            // invio una consegna al server
        out.writeBytes("CALCOLATORE: Scrivere l'operazione che si vuole far eseguire (NON SONO AMMESSE LETTERE)" + '\n');

        // ciclo che mi permette di rimanere connesso
        while (end) {
            // aspetta il messaggio dal client
            String operazione = in.readLine();
            System.out.println("Ricevuto: " + operazione);

            if (operazione.toLowerCase().equals("fine")) {
                end = false;
                out.writeBytes("Connessione conclusa\n");
            } else {
                String ris = calcolo(operazione); // calcolo l'operazione e nìla mando al client
                out.writeBytes("Risultato dell'operazione: " + ris + '\n');
            }
        }

        client.close();
        System.out.println("SERVER: connessione chiusa");
    }

    public static String calcolo(String op) {
        double ris = 0.0;
        String numeri[] = op.trim().split(" ");

        ris += Double.parseDouble(numeri[0]);
        switch (numeri[1]) {
            case "+":
                ris += Double.parseDouble(numeri[2]);
                break;

            case "*":
                ris *= Double.parseDouble(numeri[2]);
                break;

            case "-":
                ris -= Double.parseDouble(numeri[2]);
                break;

            case "/":
                if (!numeri[2].equals("0")) {
                    ris /= Double.parseDouble(numeri[2]);
                }else{
                    System.out.println("Operazione non valida! (è impossibile dividere un numero per 0)");
                    return "errore";
                }
                break;

            default:
                break;
        }

        return String.valueOf(ris);
    }

}