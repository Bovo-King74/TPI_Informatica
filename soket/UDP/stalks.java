import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class stalks {

    static public int destport = 50000;
    static public int bufsize = 512;
    static public final int timeout = 15000; // 15 secondi di timeout

    static public void main(String args[]) throws IOException {
        // Creo il socket associato alla porta
        DatagramSocket s = new DatagramSocket(destport);

        // Abilito il broadcast (nel caso di risposte o configurazioni broadcast)
        s.setBroadcast(true);
        // Imposto il timeout massimo di attesa per receive()
        s.setSoTimeout(timeout);

        System.err.println("Server in ascolto sulla porta UDP " + destport + "...");

        // Buffer di ricezione
        byte[] buffer = new byte[bufsize];
        DatagramPacket msg = new DatagramPacket(buffer, buffer.length);
            while (true) {
                try {
                    msg.setLength(buffer.length); // ripristina la dimensione massima prima di ogni receive
                    s.receive(msg); // attende il pacchetto

                    String mit = msg.getAddress().getHostAddress() + ":" + msg.getPort() + ">";

                    // Conversione conforme alle indicazioni di
                    // esercizio_scrittura_bacheca_broadcast_UDP.md:
                    // new String(dati, offset, lunghezza, StandardCharsets.UTF_8)
                    String str = new String(msg.getData(), msg.getOffset(), msg.getLength(), StandardCharsets.UTF_8);
                    System.out.print(mit + " | " + str);

                    if (str.trim().equalsIgnoreCase("/quit")) {
                        System.err.println("Ricevuto comando /quit. Chiusura...");
                        break;
                    }
                } catch (SocketTimeoutException e) {
                    // Il timeout è scaduto: permette di non bloccare il flusso indefinitamente
                    System.err.println("In attesa di messaggi (timeout scaduto)...");
                }
            }
        s.close();
        System.err.println("Server terminato.");
    }
}