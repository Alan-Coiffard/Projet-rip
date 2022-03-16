package client_console;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	// static private String pwd = System.getProperty("user.dir");

	public static void main(String[] args) {

		// Server Host
		final String serverHost = "localhost";

		Socket socketOfClient = null;
		PrintStream ps = null;
		BufferedReader is = null;
		final Scanner sc = new Scanner(System.in);
		// pour lire à partir du clavier

		try {

			// Send a request to connect to the server is listening
			// on machine 'localhost' port 9999.
			socketOfClient = new Socket(serverHost, 3030);

			// Create output stream at the client (to send data to the server)
			ps = new PrintStream(socketOfClient.getOutputStream());

			// Input stream at Client (Receive data from the server).
			is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + serverHost);
			return;
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + serverHost);
			return;
		}

		try {

			// Read data sent from the server.
			// By reading the input stream of the Client Socket.
			/*
			 * String responseLine;
			 * while ((responseLine = is.readLine()) != null) {
			 * System.out.println(responseLine);
			 * if (responseLine.indexOf("OK") != -1) {
			 * break;
			 * }
			 * }
			 */
			String entree_client = "";
			String msg_serveur[];
			int i;

			while (!entree_client.equals("bye")) {

				if (entree_client.equals("cmd")) {
					System.out.println("Les Commandes disponnibles : ");
					System.out.println(" - cd 	: pour se déplacer 		(WIP)");
					System.out.println(" - get 	: télécharge un fichier 	(WIP)");
					System.out.println(" - ls	: afficher la source		(WIP)");
					System.out.println(" - stor	: upload un fichier		(WIP)");
					System.out.println(" - pwd	: affiche le répertoire courant");
					entree_client = sc.nextLine();
					continue;
				}

				// Affiche message serveur
				do {
					msg_serveur = is.readLine().split(" ");

					for (i = 1; i < msg_serveur.length; i++) {
						if (msg_serveur[0].charAt(0) == '3') {
							int port = Integer.parseInt(msg_serveur[1]);
							try {
								Socket s = new Socket(serverHost, port);
								String fichier = msg_serveur[2];
								FileInputStream inf = new FileInputStream(new File(fichier));
								ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
								byte buf[] = new byte[1024];
								int n;
								while ((n = inf.read(buf)) != -1) {
									out.write(buf, 0, n);
								}
								inf.close();
								out.close();
								s.close();
							} catch (IOException e) {
								Socket s = new Socket(serverHost, port);								
								s.close();
								System.err.println("Impossible de récuperer le fichier.");
							}
							break;
						} else if (msg_serveur[0].charAt(0) == '4') {
							try {
								int port = Integer.parseInt(msg_serveur[1]);
								Socket s = new Socket(serverHost, port);

								String chemin = System.getProperty("user.dir") + "/" + msg_serveur[2];
								System.out.println(chemin);
								File nouveauFichier = new File(chemin);

								System.out.println("Fichier créé...");

								FileOutputStream fileOut = new FileOutputStream(nouveauFichier);

								ObjectInputStream in = new ObjectInputStream(s.getInputStream());
								byte buf[] = new byte[1024];
								int n;
								while ((n = in.read(buf)) != -1) {
									// System.out.println(n);
									fileOut.write(buf, 0, n);
								}

								System.out.println("Fichier copié.");
								fileOut.close();
								s.close();
							} catch (IOException e) {
								System.err.println("Impossible de récuperer le fichier.");
							}
							break;

						} else if (msg_serveur[0].charAt(0) == '2')
							System.err.print(msg_serveur[i] + " ");
						else
							System.out.print(msg_serveur[i] + " ");
					}
					System.out.println("");
				} while (msg_serveur[0].charAt(0) != '0');

				entree_client = sc.nextLine();
				ps.println(entree_client);
				ps.flush();
			}

			/*
			 * // Ecriture
			 * os.write("HELO");
			 * // End of line
			 * os.newLine();
			 * // Flush data.
			 * os.flush();
			 */

			ps.close();
			is.close();
			socketOfClient.close();
		} catch (UnknownHostException e) {
			System.err.println("Trying to connect to unknown host: " + e);
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
	}

}