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
			File path = new File(this.utilisateur.getAbsoluteChemin() + "/" + this.commandeArgs[0]);
			String path_str = path.getCanonicalPath();
			String user_path = this.utilisateur.getUserPath();

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

}
