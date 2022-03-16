package serveurRip;
/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.InetAddress;


public class Serveur extends Thread {
	public static final String path = "./utilisateurs/";
	public static ArrayList<Serveur> socketHandlers = new ArrayList<>();

	private static ServerSocket serveurFTP;
	private static InetAddress ip;
	private static int port;

	private static boolean continuer = true;

	private Socket user_socket;
	public User utilisateur;
	public Serveur(Socket sock) {
		user_socket = sock;
		utilisateur = new User();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Le Serveur FTP");
	    ip = InetAddress.getLocalHost();
	    port = 3030;
		serveurFTP = new ServerSocket(port);
		
		while (continuer) {
			Socket socket = serveurFTP.accept();
			Serveur socket_handler = new Serveur(socket);
			socketHandlers.add(socket_handler);

			socket_handler.start();
		}
	}

	public void run()
	{
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(user_socket.getInputStream()));
			PrintStream ps = new PrintStream(user_socket.getOutputStream());
			
			ps.println("1 Bienvenue sur un serveur FTP");
			ps.println("1 Adresse	: " + ip.getHostAddress());
			ps.println("1 Port	: " + port);
			ps.println("1 Commandes pour ce connecter à son espace personnel.");
			ps.println("1  - user + nom utilisateur");
			ps.println("0  - pass + mdp");

			String commande = "";

			// Attente de reception de commandes et leur execution
			while(!(commande=br.readLine()).equals("bye")) {
				if (commande.equals("stop")) {
					StopServeur();
					return;
				}
				System.out.println(">> "+commande);
				CommandExecutor.executeCommande(ps, commande, this.utilisateur);
			}
			
			user_socket.close();
			socketHandlers.remove(this);
		
		} catch (IOException e) {
			System.err.println("Erreur IOExcepetion dans une socket.");
		}
	}

	public static synchronized void StopServeur()
	{
		// fermer toutes les socket et terminer l'app;
	}

	
	public static synchronized boolean IsUserConnected(String nom) {
		for (Serveur proc : socketHandlers) {
			if (proc.utilisateur.getNom().equals(nom))
				return true;
		}
		return false;
	}

	public static String getPath() {
		return path;
	}
}
