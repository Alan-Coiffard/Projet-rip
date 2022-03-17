package serveurRip;

import java.io.*;

public class CommandePASS extends Commande {

	public CommandePASS(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		// vérification de l'argument
		if (commandeArgs.length == 0) {			
			ps.println("2 Aucun mot de passe en entrée");
			ps.println("0");
			return;
		}
		
		// Temptative de connexion,
		try {			
			if (this.utilisateur.Connect(commandeArgs[0])) {
				ps.println("5");
				ps.println("1 Mot de passe accepté");
			}
			else
			{
				ps.println("2 Mot de passe ou identifiant incorrect");
				ps.println("0");
			}
		} catch (Exception e) {
			System.out.println("Error while reading file line by line:" + e.getMessage());                      
		}
	}

	public void help() {
		ps.println("1 -- Commande PASS --");
		ps.println("1 usage: pass string:<mot_de_pass>");
		ps.println("1 description: Indique le mot de passe à utiliser pour se connecter au serveur FTP puis tempte la connection à celui-ci.");
	}
}
