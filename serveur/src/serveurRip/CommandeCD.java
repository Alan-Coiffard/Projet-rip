package serveurRip;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeCD extends Commande {

	public CommandeCD(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		if (this.commandeArgs.length == 0)
			return;

		try {
			// Récupération du chemin
			File path;
			if (this.commandeArgs[0].charAt(0) == '/')
				path = new File(this.utilisateur.getUserPath() + "/" + this.commandeArgs[0]);
			else
				path = new File(this.utilisateur.getAbsoluteChemin() + "/" + this.commandeArgs[0]);
				
			String path_str = path.getCanonicalPath();
			String user_path = this.utilisateur.getUserPath();

			// Vérification du chemin
			if (path.exists() && path.isDirectory()) {
				try {
					if (path_str.substring(0, user_path.length()).equals(user_path))
						this.utilisateur.setChemin(path_str.substring(user_path.length()));
				} catch (java.lang.StringIndexOutOfBoundsException e) {
					this.utilisateur.setChemin("/");
				}
			}
		} catch (IOException e) {
			System.err.println("IOexecption erreur dans commandeCD");
		}

	}

	public void help() {
		ps.println("1 -- Commande CD --");
		ps.println("1 usage: cd path:<destination>");
		ps.println("1 description: Déplace le répertoire courant du client sur le serveur à l'emplacement donné en argument.");
	}
}
