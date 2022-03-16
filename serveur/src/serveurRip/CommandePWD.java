package serveurRip;
import java.io.PrintStream;

public class CommandePWD extends Commande {
	
	public CommandePWD(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		ps.println("1 " + Commande.getPwd() + this.utilisateur.getAbsoluteChemin().substring(1));
	}
}
