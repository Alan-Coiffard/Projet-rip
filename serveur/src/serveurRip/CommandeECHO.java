package serveurRip;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeECHO extends Commande {

	public CommandeECHO(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		if (this.commandeArgs.length == 0) {
            ps.println("1 ");
            return;
        }
        if (this.commandeArgs[1].equals("<<")) {

        } else {
            ps.println("1 " + String.join(" ", this.commandeArgs));
        }
	}

	public void help() {
		ps.println("1 -- Commande ECHO --");
		ps.println("1 usage: echo string:<texte>");
		ps.println("1 usage: echo path:<fichier_cible> string:'<<' string:<texte>");
		ps.println("1 description: Affiche du texte dans la console ou l'écrit dans un fichier.");
	}
}
