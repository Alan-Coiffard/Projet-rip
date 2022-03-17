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
	// chemin du dossier utilisateur
	private static final String path = new File(System.getProperty("user.dir") + "/utilisateurs").getPath();

	// Liste des processus sur lesquelles le client se connecte.
	private static ArrayList<Serveur> socketHandlers = new ArrayList<>();

	private static ServerSocket serveurFTP;
	private static InetAddress ip;
	private static int port;

	private static boolean continuer = true;
	private static int id_count = 0;


	public Socket user_socket;
	public User utilisateur;
	public Serveur(Socket sock) {
		user_socket = sock;
		utilisateur = new User();
		utilisateur.setId(id_count++ % 100); // l'id ne peux pas dépasser 100
	}

	public static void main(String[] args) throws Exception {
		System.out.println("-- Serveur FTP --");

		// Gestion des pannes du seveur
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());

		// Initialisation serveur
	    ip = InetAddress.getLocalHost();
	    port = 3030;
		serveurFTP = new ServerSocket(port);
		
		while (continuer) {
			// pour chaque nouvelle connexion on lance un nouveau Thread.
			Socket socket = serveurFTP.accept();
			Serveur socket_handler = new Serveur(socket);
			socketHandlers.add(socket_handler);

			socket_handler.start();
		}
	}

	// Thread serveur.
	public void run()
	{
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(user_socket.getInputStream()));
			PrintStream ps = new PrintStream(user_socket.getOutputStream());
			
			// Message d'accueil
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

			// Déconnexion
			System.err.println("Le client : '" + this.utilisateur.getNom() + "' s'est déconnecté.");
			user_socket.close();
			socketHandlers.remove(this);		
		} catch (Exception e) {
			System.err.println("Le client : '" + this.utilisateur.getNom() + "' s'est déconnecté.");
			socketHandlers.remove(this);
		}
	}

	// Lorsque le seveur se déconnecte
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
	
	// Vérifie si un des thread est utilisé par un utilisateur.
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

// Gestion des pannes
class ShutdownHook extends Thread {
	@Override
	public void run() {
		Serveur.StopServeur();
	}
}
