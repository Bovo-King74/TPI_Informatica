import java.io.*;
import java.net.*;
import java.util.*;

public class ClientCalc {

    public static void main(String[] args) {

        try {
            // connessione al server, creo il soket
            Socket s = new Socket("localhost", 3456); // IP + porta

            Scanner scn = new Scanner(System.in);
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            // s.getOutputStream() "flusso grezzo" dei dati
            // buffer vi permette di leggere una stringa intera e non singoli caratteri
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            String consegna = in.readLine();
            System.out.println(consegna);

            boolean end = true;

            while (end) {
                
                String op = scn.nextLine();

                // mando il messaggio al server
                out.writeBytes(op + '\n');

                if (op.toLowerCase().equals("fine")) {
                    end = false;
                }

                String ris = in.readLine();
                System.out.println("Server: " + ris);
            }

            s.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Host non trovato ");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server non trovato");
        }

    }

    // metodo per controllare l'input dell'utente
    /*public static String controlloInp() {
        Scanner scn = new Scanner(System.in);
        boolean ok = true;
        String operazione = "";
        while (ok) {
            operazione = scn.nextLine();

            // controllo che non ci siano lettere o caratteri speciali
            if (operazione.trim().matches("[0-9*+/\\-\\.]+")) {

                // controllo che sia scritta correttamente la stringa
                int stato = 0;
                boolean decimal = false;
                for (char c : operazione.toCharArray()) {
                    if (!(c >= '0' && c <= '9') || stato == -1) {
                        System.out.println("Operazione scritta in modo scorretto! Riprova: ");
                        break;
                    }
                    switch (stato) {
                        case 0:
                            if (c >= '0' && c <= '9') {
                                stato = 0;
                            } else if (c == '.') {
                                decimal = true;
                                stato = 1;
                            } else {
                                stato = 1;
                            }
                            break;

                        case 1:
                            if (c >= '0' && c <= '9') {
                                stato = 0;
                                decimal = false;
                            } else {
                                stato = -1;
                            }
                            break;
                    }
                }
                if (stato != -1) {
                    ok = false;
                }
            } else {
                System.out.println("Operazione contenente caratteri non validi! Riprova: ");
            }
        }

        return operazione;
    }*/
}
