package serveurRip;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {

	public CommandeGET(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		if (this.commandeArgs.length == 0) {
			ps.println("Il manque le nom du fichier à envoyer");
			return;
		}

		String nomFichier = this.commandeArgs[0];
		String chemin = this.utilisateur.getAbsoluteChemin() + nomFichier;
		System.out.println(chemin);
		File fichierACopier = new File(chemin);

		try {
			if (fichierACopier.exists() && fichierACopier.isFile()) {

				FileInputStream inf = new FileInputStream(fichierACopier);
				int port = 5000;

				ServerSocket s = new ServerSocket(port);
				System.out.println("envoie du port");
				ps.println("4 " + port);
				Socket sv = s.accept();
				System.out.print("qfg");
				ObjectOutputStream out = new ObjectOutputStream(sv.getOutputStream());
				byte buf[] = new byte[1024];
				int n;
				while ((n = inf.read(buf)) != -1) {
					out.write(buf, 0, n);
				}
				inf.close();
				out.close();
				s.close();
			} else {
				ps.println("2 Le fichier \"" + nomFichier + "\" n'existe pas");
				ps.println("2 Pas de récuperation.");
				// ps.println("Voulez-vous l'ecraser ? oui/non");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * TODO : faire une condition si le fichier existe déjà pour
		 * demander ecraser ou changer le nom
		 */
	}
}
