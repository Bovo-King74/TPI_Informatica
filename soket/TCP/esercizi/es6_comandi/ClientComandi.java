import java.io.*;
import java.net.*;
import java.util.*;

public class ClientComandi{
	public static void main(String[] args){
		try{
			//se passo l'IP come argomento (java ClientComandi 192.168.1.23) si collega a quello
			String host = args.length > 0 ? args[0] : "localhost";
			
			Scanner sc = new Scanner(System.in);
			Socket s = new Socket(host, 6789);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			String mes;
			String risposta;
			do{
				System.out.println("Comando (ORA, MAIUSC x, CONTA x, INVERTI x, VOCALI x, ESCI): ");
				mes = sc.nextLine();
				out.writeBytes(mes+"\n");
				risposta = in.readLine();
				System.out.println("Server < "+risposta);
			}while(risposta != null && !risposta.equals("BYE"));
			
			s.close();
		}catch(UnknownHostException e){
			System.out.println("Host non trovato\n");
		}catch(IOException e){
			System.out.println("Server non trovato\n");
		}
	}
}