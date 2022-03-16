package serveurRip;
import java.io.File;
import java.io.PrintStream;

public class CommandeUSER extends Commande {
	
	public CommandeUSER(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		if (commandeArgs.length == 0)
			return;

		// Ce serveur accepte uniquement le user personne
		String[] listeFichiers = new File(Serveur.path).list();
		if (listeFichiers == null) // aucun dosier trouver
		{
			System.err.println("CommandeUSER: Aucun dossiers utilisateurs trouvés.");
			ps.println("2 Erreur serveur");
			ps.println("0");
			return;
		}

		boolean existe = false;

		// Verifie l'existance de l'utilisateur dans la liste des dossiers
		for (int i = 0; i < listeFichiers.length; i++) {
			System.out.println(listeFichiers[i]);
			if (commandeArgs[0].toLowerCase().equals(listeFichiers[i].toLowerCase())) {
				this.utilisateur.setNom(commandeArgs[0].toLowerCase());
				existe = true;
				ps.println("0 Commande user OK");
				break;
			}
		}
		
		if (!existe) {
			ps.println("2 L'utilisateur " + commandeArgs[0] + " n'existe pas");
			ps.println("0");
		}		
	}

}
