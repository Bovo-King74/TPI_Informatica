import java.net.*;
import java.nio.charset.StandardCharsets;

public class AttaccoDOS {

    public static void main(String[] args) throws Exception {

        int porta = 50000;
        String broadcast = "172.16.255.255";

        DatagramSocket socket = new DatagramSocket();

        for (int i = 1; i <= 10000; i++) {

            String messaggio = "ciccio |" + i;

            byte[] dati = messaggio.getBytes(
                StandardCharsets.UTF_8
            );

            InetAddress indirizzo =
                InetAddress.getByName(broadcast);

            DatagramPacket pacchetto =
                new DatagramPacket(
                    dati,
                    dati.length,
                    indirizzo,
                    porta
                );

            socket.send(pacchetto);

            System.out.println("Inviato messaggio " + i);

            Thread.sleep(500);
        }

        socket.close();

        System.out.println("Test terminato!");
    }
}
