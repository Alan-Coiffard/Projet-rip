package serveurRip;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeMKDIR extends Commande {

	public CommandeMKDIR(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		if (this.commandeArgs.length == 0)
			return;

		try {
			// Récupération du chemin
			File path = new File(this.utilisateur.getAbsoluteChemin() + "/" + this.commandeArgs[0]);
			String path_str = path.getCanonicalPath();
			String user_path = this.utilisateur.getUserPath();

            try {
				// Vérification et création
                if (path_str.substring(0, user_path.length()).equals(user_path)) {
                    if (path.mkdir()) {
                        ps.println("1 Creation reussi!");
                    } else {
                        ps.println("2 Impossible de creer le dossier");
                    }
                } else {
                    ps.println("2 Chemin incorrect");
                }                    
            } catch (java.lang.StringIndexOutOfBoundsException e) {
                System.err.println("StringIndexOutOfBoundsException erreur dans commandeMKDIR");
            }
		} catch (IOException e) {
			System.err.println("IOexecption erreur dans commandeMKDIR");
		}
	}

	public void help() {
		ps.println("1 -- Commande MKDIR --");
		ps.println("1 usage: mkdir path:<nouveau_dossier>");
		ps.println("1 description: Créer un nouveau dossier à l'endroit indiqué en argument.");
	}

}
