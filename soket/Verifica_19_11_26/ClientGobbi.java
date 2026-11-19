import java.io.*;
import java.net.*;
import java.util.*;

public class ClientGobbi {

    static int port = 5000;
    static String host = "172.16.7.213";

    public static void main(String[] args) throws UnknownHostException, IOException {

        // connessione al server, creo il soket
        Socket s = new Socket(host, port); // IP + porta

        Scanner scn = new Scanner(System.in);
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        // in.readLine();
        boolean end = false;

        int id_proposta = 1;

        int correct = 0;
        String out_c = "";

        // TEMPO
        long t_ini = 0;
        long t_fin = 0;
        String t_ris = "0.00";

        // STATS_RESULT
        int tentativi = 0;
        int char_dig = 0;
        int correct_count = 0;
        String correct_ratio = "";
        double avg_chars = 0.0;
        double avg_cps = 0.0;
        String min_cps = "";
        String max_cps = "";

        do {
            // leggo la parola iniziale dal server
            String out_s = in.readLine().trim();
            // System.out.println(out_s);
            String text[] = out_s.split("[|]");
            switch (text[0].toUpperCase()) {

                case "WORD": // WORD|id-proposta|word
                    out_c = "\nParola: " + text[2]; // stampo il testo da copiare
                    id_proposta = Integer.parseInt(text[1]); // proposta
                    // stampo il tempo dell'arrivo del messaggio
                    t_ini = System.nanoTime(); // ms
                    break;

                case "RESULT": // RESULT|id-proposta|correct|tempo_impiegato_ms
                    id_proposta = Integer.parseInt(text[1]);
                    correct = Integer.parseInt(text[2]);
                    t_ris = text[3];
                    String giusto = correct == 1 ? "giusto" : "sbagliato";
                    out_c = "\nTentativo: "+id_proposta+"\n"+giusto+"\nTempo impiegato: "+t_ris;
                    break;

                case "STATS_RESULT": // STATS_RESULT|count|chars|correct-count|correct-ratio|avg-chars|avg-cps|min-cps|max-cps
                    tentativi = Integer.parseInt(text[1]);
                    char_dig = Integer.parseInt(text[2]);
                    correct_count = Integer.parseInt(text[3]);
                    correct_ratio = text[4];
                    avg_chars = Double.parseDouble(text[5]);
                    avg_cps = Double.parseDouble(text[6]);
                    min_cps = text[7];
                    max_cps = text[8];
                    out_c = "\nTentativi eseguiti: "+tentativi+"\nCaratteri digitati: "+char_dig+"\nTentativi corretti: "+correct_count+"\n% Tentativi: "+correct_ratio+"\nMedia caratteri per tentativo: "+avg_chars+"\nVelocità media: "+avg_cps+"\nVelocità minima: "+min_cps+"\nVelocità massima: "+max_cps;

                    break;

                case "ERROR": // ERROR|error-text
                    out_c = "Server < "+text[0]+": "+text[1];
                    end = true;
                    break;
            }

            // Stampo il comando del server
            System.out.println(out_c);

            if (text[0].toUpperCase().equals("WORD")) {

                // leggo il messaggio dall'utente (tastiera)
                String inp_c = scn.nextLine().trim();
                String inp_s = "";

                switch (inp_c) {

                    case "/stats":
                        inp_s = "STATS";
                        break;

                    case "/quit":
                        inp_s = "QUIT";
                        System.out.println("Chiusura della connessione...");
                        end = true;
                        break;

                    default: // inserimento della parola
                        // PLAY|id-proposta|tempo_impiegato_ms|parola_digitata
                        t_fin = System.nanoTime() - t_ini;
                        int t_imp = (int)(t_fin/1000000);
                        inp_s = "PLAY|"+id_proposta+"|"+t_imp+"|"+inp_c;
                }

                // System.out.println(inp_s);

                out.writeBytes(inp_s.trim() + '\n'); // invio il messaggio al server

            } // chiusura if

        } while (!end);

        s.close();
        System.out.println("Chiusura del soket. Arrivederci!");
    }
}

// odio i nanosecondi e chiunque abbia inventato questa unità di misura