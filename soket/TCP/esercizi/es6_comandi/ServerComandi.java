import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;

public class ServerComandi{
	public static void main(String[] args){
		try{
			ServerSocket server = new ServerSocket(6789);
			System.out.println("SERVER in ascolto sulla porta 6789...");
			
			while(true){
				Socket client = server.accept(); //un client alla volta
				System.out.println("Client connesso: "+client.getInetAddress()+":"+client.getPort());
				
				try{
					DataOutputStream out = new DataOutputStream(client.getOutputStream());
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					
					String riga;
					while((riga = in.readLine()) != null){
						System.out.println("Client > "+riga);
						String messaggioInv;
						
						String[] parti = riga.trim().split(" ", 2);
						String comando = parti[0].toUpperCase();
						String arg = parti.length > 1 ? parti[1] : "";
						
						switch(comando){
							case "ORA":
								messaggioInv = "OK "+LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
								break;
							case "MAIUSC":
								if(arg.equals("")){
									messaggioInv = "ERR argomento mancante";
								}else{
									messaggioInv = "OK "+arg.toUpperCase();
								}
								break;
							case "CONTA":
								if(arg.equals("")){
									messaggioInv = "ERR argomento mancante";
								}else{
									messaggioInv = "OK "+arg.length();
								}
								break;
							case "INVERTI":
								if(arg.equals("")){
									messaggioInv = "ERR argomento mancante";
								}else{
									messaggioInv = "OK "+new StringBuilder(arg).reverse().toString();
								}
								break;
							case "VOCALI":
								if(arg.equals("")){
									messaggioInv = "ERR argomento mancante";
								}else{
									int vocali = 0;
									for(char c : arg.toLowerCase().toCharArray()){
										if("aeiou".indexOf(c) != -1){
											vocali++;
										}
									}
									messaggioInv = "OK "+vocali;
								}
								break;
							case "ESCI":
								messaggioInv = "BYE";
								break;
							default:
								messaggioInv = "ERR comando sconosciuto";
						}
						
						out.writeBytes(messaggioInv+"\n");
						System.out.println("Server < "+messaggioInv);
						
						if(comando.equals("ESCI")){
							break;
						}
					}
				}catch(IOException e){
					System.out.println("Errore con il client: "+e.getMessage());
				}
				
				client.close(); //NON server.close()
				System.out.println("Client disconnesso, in attesa del prossimo...\n");
			}
		}catch(IOException e){
			System.out.println("Errore di comunicazione: "+e.getMessage());
		}
	}
}