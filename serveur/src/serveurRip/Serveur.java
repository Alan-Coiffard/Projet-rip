package serveurRip;
/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.InetAddress;


public class Serveur extends Thread {
	private static final String path = new File(System.getProperty("user.dir") + "/utilisateurs").getPath();
	private static ArrayList<Serveur> socketHandlers = new ArrayList<>();


	private static ServerSocket serveurFTP;
	private static InetAddress ip;
	private static int port;

	private static boolean continuer = true;

	public Socket user_socket;
	public User utilisateur;
	public Serveur(Socket sock) {
		user_socket = sock;
		utilisateur = new User();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("-- Serveur FTP --");

		Runtime.getRuntime().addShutdownHook(new ShutdownHook());

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

			System.err.println("Le client : '" + this.utilisateur.getNom() + "' s'est déconnecté.");
			user_socket.close();
			socketHandlers.remove(this);		
		} catch (Exception e) {
			System.err.println("Le client : '" + this.utilisateur.getNom() + "' s'est déconnecté.");
			socketHandlers.remove(this);
		}
	}

	public static void StopServeur()
	{
		for (Serveur s : socketHandlers) {
			try {
				System.out.println("Closed connection");
				s.user_socket.close();
			} catch (IOException e) {
				System.err.println("Serveur.StopServeur IOException");
			}
		}
		socketHandlers.clear();
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

class ShutdownHook extends Thread {
	@Override
	public void run() {
		Serveur.StopServeur();
	}
}
