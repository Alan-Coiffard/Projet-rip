package serveurRip;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeLS extends Commande {

	public CommandeLS(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		String chemin;

		// Vérification du chemin
		if (commandeArgs.length == 0)
			chemin = this.utilisateur.getAbsoluteChemin();
		else {
			try {
				if (this.commandeArgs[0].charAt(0) == '/')
					chemin = new File(this.utilisateur.getUserPath() + "/" + this.commandeArgs[0]).getCanonicalPath();
				else
					chemin = new File(this.utilisateur.getAbsoluteChemin() + "/" + this.commandeArgs[0]).getCanonicalPath();
			} catch (IOException e) {
				ps.println("1 Ce n'est pas un dossier");
				return;
			}
		}

		// Vérification du chemin
		File repertoire = new File(chemin);
		try {
			String path_str = repertoire.getCanonicalPath();
			String user_path = this.utilisateur.getUserPath();
			if (!path_str.substring(0, user_path.length()).equals(user_path)) {
				ps.println("2 Chemin incorrect");
				return;
			}
		} catch (IOException e) {
			ps.println("2 Chemin incorrect");
			return;
		}

		// Affichage
		if (!repertoire.isDirectory()) {
			ps.println("1 Ce n'est pas un dossier");
		} else {
			String[] listeFichiers = repertoire.list();
			for (int i = 0; i < listeFichiers.length; i++)
				ps.println("1 " + listeFichiers[i]);
		}
	}

	public void help() {
		ps.println("1 -- Commande LS --");
		ps.println("1 usage: ls ?path:<destination>");
		ps.println("1 description: Affiche la liste des dossiers et fichier à l'endroit actuelle sur le serveur ou optionellement à partir d'un répertoire passé en argument.");
	}

}
