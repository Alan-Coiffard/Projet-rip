package serveurRip;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeSTOR extends Commande {

	public CommandeSTOR(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		if (this.commandeArgs.length == 0) {
			ps.println("Il manque le nom du fichier à envoyer");
		}

		String[] nomFichier = this.commandeArgs[0].split("/");
		String chemin = this.utilisateur.getAbsoluteChemin().charAt(this.utilisateur.getAbsoluteChemin().length() - 1) == '/' 
				? this.utilisateur.getAbsoluteChemin() + nomFichier[nomFichier.length - 1] 
				: this.utilisateur.getAbsoluteChemin() + "/" + nomFichier[nomFichier.length - 1];
		System.out.println(chemin);
		File nouveauFichier = new File(chemin);

		try {
			if (nouveauFichier.createNewFile()) {
				System.out.println("Fichier créé!");

				FileOutputStream fileOut = new FileOutputStream(nouveauFichier);
				int port = 3000;

				ServerSocket s = new ServerSocket(port);
				System.out.println("envoie du port");
				ps.println("3 " + port);
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

			} else {
				ps.println("2 Le fichier existe déjà.");
				ps.println("2 Le fichier n'a pas été envoyé.");
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
