import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class stalkc {

    static public BufferedReader bin;
    static public int destport = 50000;

    static public void main(String args[]) throws UnknownHostException, IOException {
        String desthost = "localhost";
        if (args.length >= 1)
            desthost = args[0];

        bin = new BufferedReader(new InputStreamReader(System.in));

        System.err.print("Looking up address of " + desthost + "...");
        InetAddress dest = InetAddress.getByName(desthost); // DNS query o broadcast IP
        System.err.println(" got it! (" + dest.getHostAddress() + ")");

        DatagramSocket s = new DatagramSocket();
        
        // Abilita il broadcast per permettere l'invio all'indirizzo broadcast della rete
        s.setBroadcast(true);

        System.err.println("Our own port is " + s.getLocalPort());
        System.err.println("Scrivi un messaggio e premi Invio (digita /quit per uscire):");

        while (true) {
            String buf = bin.readLine();

            if (buf == null)
                break; // EOF (CTRL+D o fine input)

            buf = buf + "\n"; // aggiungi newline
            byte[] bbuf = buf.getBytes(StandardCharsets.UTF_8);

            // Crea il pacchetto con l'indirizzo, la porta e i byte corretti
            DatagramPacket msg = new DatagramPacket(bbuf, bbuf.length, dest, destport);
            s.send(msg);

            if (buf.trim().equalsIgnoreCase("/quit")) {
                break;
            }
        } // while

        s.close();
        System.err.println("Client terminato.");
    }
}