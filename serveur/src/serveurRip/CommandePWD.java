package serveurRip;
import java.io.PrintStream;

public class CommandePWD extends Commande {
	
	public CommandePWD(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		ps.println("1 " + this.utilisateur.getChemin());
	}

	public void help() {
		ps.println("1 -- Commande PWD --");
		ps.println("1 usage: pwd");
		ps.println("1 description: Affiche le répertoire actuel du client sur le serveur");
	}
}
