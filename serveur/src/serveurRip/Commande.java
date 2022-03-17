package serveurRip;

import java.io.PrintStream;

public abstract class Commande {

	protected PrintStream ps;
	protected String commandeNom = "";
	protected String[] commandeArgs;
	protected User utilisateur;
	static private String pwd = System.getProperty("user.dir");

	public static String getPwd() {
		return pwd;
	}

	public Commande(PrintStream ps, String commandeStr, User user) {
		this.utilisateur = user;
		this.ps = ps;
		String[] args = commandeStr.split(" ");
		commandeNom = args[0];
		commandeArgs = new String[args.length - 1];

		for (int i = 0; i < commandeArgs.length; i++) {
			commandeArgs[i] = args[i + 1];
		}
	}

	public abstract void execute();
	
	public abstract void help();
}
