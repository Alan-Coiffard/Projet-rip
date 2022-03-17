package serveurRip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeRM extends Commande {

	public CommandeRM(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		// Vérifiation de l'argument
		if (this.commandeArgs.length == 0 || this.commandeArgs[0].contains("mdp.txt")) {
            ps.println("1 Aucun fichier.");
            return;
        }

        // Supression du fichier
        File fichier;
        if (this.commandeArgs[0].charAt(0) == '/')
            fichier = new File(this.utilisateur.getUserPath() + "/" + this.commandeArgs[0]);
        else
            fichier = new File(this.utilisateur.getAbsoluteChemin() + "/" + this.commandeArgs[0]);
        
        try {
            if (!fichier.getCanonicalPath().substring(0, utilisateur.getUserPath().length()).equals(utilisateur.getUserPath())) {
                ps.println("2 chemin incorrect.");
                return;
            }

            if (fichier.exists() && fichier.isFile() && fichier.delete()) {
                ps.println("1 Fichier supprimé.");
            } else {
                ps.println("2 Fichier incorrect.");
            }
        } catch (IOException e) {
            ps.println("2 Erreur de fichier.");
        }

	}

	public void help() {
		ps.println("1 -- Commande RM --");
		ps.println("1 usage: rm path:<fichier>");
		ps.println("1 description: Supprime un fichier.");
	}
}
