package serveurRip;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeSTOR extends Commande {

	public CommandeSTOR(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		// Vérifiation de l'argument
		if (this.commandeArgs.length == 0) {
			ps.println("Il manque le nom du fichier à envoyer");
		}

		// Execution de la commande pour chaque arguments
		for (int i = 0; i < Math.min(10, this.commandeArgs.length); i++) {
			// Récupération du chemin
			String[] nomFichier = this.commandeArgs[i].split("/");
			String chemin = this.utilisateur.getAbsoluteChemin() + "/" + nomFichier[nomFichier.length - 1];
			File nouveauFichier = new File(chemin);

			// Sécurité
			if (nomFichier[nomFichier.length - 1].equals("mdp.txt"))
				continue;

			// Récupération du contenu du fichier
			try {
				if(nouveauFichier.exists()) {
					ps.println("1 Le fichier existe déjà, il a été écrasé");
				}
				System.out.println("Fichier créé!");

				FileOutputStream fileOut = new FileOutputStream(nouveauFichier);
				int port = 3000 + this.utilisateur.getId();

				ServerSocket s = new ServerSocket(port);
				System.out.println("envoie du port");
				ps.println("3 " + port + " " + this.commandeArgs[i]);
				Socket sv = s.accept();

				ObjectInputStream in = new ObjectInputStream(sv.getInputStream());
				byte buf[] = new byte[1024];
				int n;
				while ((n = in.read(buf)) != -1) {
					// System.out.println(n);
					fileOut.write(buf, 0, n);
				}

				ps.println("1 Fichier bien copié.");
				fileOut.close();
				s.close();

			} catch (IOException e) {
				nouveauFichier.delete();
				System.err.println("Erreur de CommandeSTOR");
			}
		}
	}	

	public void help() {
		ps.println("1 -- Commande STOR --");
		ps.println("1 usage: stor path:<fichier> . . .");
		ps.println("1 description: Télécharge un ou plusieurs fichiers du client dans le répertoire actuelle du serveur.");
	}
}
