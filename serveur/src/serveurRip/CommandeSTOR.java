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
		for (int i = 0; i < Math.min(10, this.commandeArgs.length); i++) {

			String[] nomFichier = this.commandeArgs[i].split("/");
			String chemin = this.utilisateur.getAbsoluteChemin() + "/" + nomFichier[nomFichier.length - 1];
			/*
			 * String chemin = this.utilisateur.getAbsoluteChemin()
			 * .charAt(this.utilisateur.getAbsoluteChemin().length() - 1) == '/'
			 * ? this.utilisateur.getAbsoluteChemin() + nomFichier[nomFichier.length - 1]
			 * : this.utilisateur.getAbsoluteChemin() + "/" + nomFichier[nomFichier.length -
			 * 1];
			 */
			System.out.println(chemin);
			File nouveauFichier = new File(chemin);

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

}
