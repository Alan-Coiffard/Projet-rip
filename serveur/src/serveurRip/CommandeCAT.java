package serveurRip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeCAT extends Commande {

	public CommandeCAT(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
        // Aucun arguments
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

        // Affichage du contenu du fichier
        if (fichier.exists() && fichier.isFile()) {
            try {
                FileReader fr = new FileReader(fichier);
                BufferedReader br = new BufferedReader(fr);

                String line;
                while ((line = br.readLine()) != null) {
                    ps.println("1 " + line);
                }
                br.close();
                fr.close();
            } catch (IOException e) {
                ps.println("2 Erreur lors de la lecture du fichier.");
            }
        } else {
            ps.println("2 Fichier incorrect.");
        }

	}

	public void help() {
		ps.println("1 -- Commande CAT --");
		ps.println("1 usage: cat path:<fichier>");
		ps.println("1 description: Affiche le contenu d'un fichier");
	}
}
