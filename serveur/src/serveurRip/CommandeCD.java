package serveurRip;

import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {

	public CommandeCD(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	static int existe(String T[], String val) {
		for (int i = 0; i < T.length; i++) {
			if (T[i].equals(val))
				// retourner la position courante
				return i;
		}
		System.out.println("La valeur recherchée n'existe pas");
		return -1;
	}

	public void execute() {
		String[] test = this.commandeArgs[0].split("/");
		// System.out.println(test.length);
		String chemin = this.utilisateur.getChemin();
		for (int i = 0; i < test.length; i++) {
			System.out.println("1-" + chemin);
			if (test[i].equals("..") && chemin.equals("/")) {
				ps.println("2 Vous ne pouvez pas remonter plus loin");
			} else {
				if (test[i].equals("..")) {
					String[] s = chemin.split("/");
					chemin = "/";
					for (int j = 0; j < s.length - 1; j++)
						chemin = chemin + s[j];
				} else {
					String pwd = this.utilisateur.getUserPath().charAt(this.utilisateur.getUserPath().length()-1) == '/' 
							? this.utilisateur.getUserPath() + test[i] 
							: this.utilisateur.getUserPath() + "/" + test[i];
					System.out.println(pwd);
					File new_pwd = new File(pwd);
					// File[] liste = ra.listFiles();
					if (new_pwd.exists() && new_pwd.isDirectory()) {
						chemin = chemin.charAt(chemin.length()-1) == '/' 
								? chemin + test[i] 
								: chemin + "/" + test[i];
					} else {
						ps.println("2 " + test[i] + " n'existe pas ou n'est pas un dossier");
					}
				}
			}
		}
		this.utilisateur.setChemin(chemin);		
	}

}
