package serveurRip;

import java.io.*;

public class CommandePASS extends Commande {

	public CommandePASS(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		if (commandeArgs.length == 0)
			return;
		try {			
			if (this.utilisateur.Connect(commandeArgs[0]))
				ps.println("1 Mot de passe accepté");
			else
			{
				ps.println("2 Mot de passe ou identifiant incorrect");
				ps.println("0");
			}
		} catch (Exception e) {
			System.out.println("Error while reading file line by line:" + e.getMessage());                      
		}
	}
}
