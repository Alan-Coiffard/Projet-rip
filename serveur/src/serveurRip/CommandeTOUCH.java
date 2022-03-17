package serveurRip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeTOUCH extends Commande {

	public CommandeTOUCH(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
        // Vérification de l'argument
		if (this.commandeArgs.length == 0) {
            ps.println("1 Aucun fichier.");
            return;
        }

        // Vérification du chemin
        File fichier;
        if (this.commandeArgs[0].charAt(0) == '/')
            fichier = new File(this.utilisateur.getUserPath() + "/" + this.commandeArgs[0]);
        else
            fichier = new File(this.utilisateur.getAbsoluteChemin() + "/" + this.commandeArgs[0]);

        try {
            String path_str = fichier.getCanonicalPath();
            String user_path = this.utilisateur.getUserPath();
            if (!path_str.substring(0, user_path.length()).equals(user_path)) {
                ps.println("2 Chemin incorrect");
                return;
            }
        } catch (IOException e) {
            ps.println("2 Chemin incorrect");
            return;
        }
        
        // Création du fichier
        try {
            if (!fichier.createNewFile()) {
                ps.println("2 Le fichier existe déjà.");
            }
        } catch (IOException e) {
            ps.println("2 Fichier incorrect.");
        }
	}

	public void help() {
		ps.println("1 -- Commande TOUCH --");
		ps.println("1 usage: touch path:<fichier>");
		ps.println("1 description: Crée un nouveau fichier vide.");
	}
}
