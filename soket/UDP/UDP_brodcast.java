import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDP_brodcast {
    static public BufferedReader bin;
    static public int destport = 50000;
    static public int bufsize = 512;
    static public final int timeout = 15000; // 15 secondi di timeout
    static public String nikname = "gobbi";

    static public void main(String args[]) throws UnknownHostException, IOException, InterruptedException {
        
        // Creo il socket associato alla porta
        DatagramSocket s = new DatagramSocket(destport);

        // Abilito il broadcast (nel caso di risposte o configurazioni broadcast)
        s.setBroadcast(true);
        // Imposto il timeout massimo di attesa per receive()
        s.setSoTimeout(timeout);

        // Indirizzo brodcast
        InetAddress dest = InetAddress.getByName("172.16.255.255");

        // THREAD PER GESTIRE IL CLIENT
        Thread tc = new Thread(() -> {
            try {
                bin = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    String buf = bin.readLine();

                    if (buf == null)
                        break; // EOF (CTRL+D o fine input)

                    String riga = nikname + " | " + buf;
                    byte[] bbuf = riga.getBytes(StandardCharsets.UTF_8);

                    if (buf.trim().equalsIgnoreCase("/quit"))
                        break;

                    // Crea il pacchetto con l'indirizzo, la porta e i byte corretti
                    DatagramPacket msg = new DatagramPacket(bbuf, bbuf.length, dest, destport);
                    s.send(msg);

                    
                }
            } catch (IOException e) {
                System.err.println("Errore I/O nel client: " + e.getMessage());
            }
        });
        // THREAD PER GESTIRE IL SERVER
        Thread ts = new Thread(() -> {
            // Buffer di ricezione
            byte[] buffer = new byte[bufsize];
            DatagramPacket msgr = new DatagramPacket(buffer, buffer.length);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    msgr.setLength(buffer.length); // ripristina la dimensione massima prima di ogni receive
                    s.receive(msgr); // attende il pacchetto

                    String str = new String(msgr.getData(), msgr.getOffset(), msgr.getLength(), StandardCharsets.UTF_8);
                    System.out.println(str);

                } catch (SocketTimeoutException e) {
                    // Il timeout è scaduto: permette di non bloccare il flusso indefinitamente
                    System.err.println("In attesa di messaggi (timeout scaduto)...");
                } catch (IOException e) {
                    System.err.println("Errore di rete: " + e.getMessage());
                    break;
                }
            }
        });

        tc.start();
        ts.start();
        tc.join();
        
        ts.interrupt();
        

        s.close();
        System.err.println("Server terminato.");
    }
}
