package serveurRip;
import java.io.File;
import java.io.PrintStream;

public class CommandeUSER extends Commande {
	
	public CommandeUSER(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		// Vérification de l'argument
		if (commandeArgs.length == 0) {
			ps.println("2 Aucun utilisateur en entrée");
			ps.println("0");
			return;
		}

		// Ce serveur accepte uniquement le user personne
		String[] listeFichiers = new File(Serveur.getPath()).list();
		if (listeFichiers == null) // aucun dosier trouver
		{
			System.err.println("CommandeUSER: Aucun dossiers utilisateurs trouvés.");
			ps.println("2 Erreur serveur");
			ps.println("0");
			return;
		}

		boolean existe = false;

		// Verifie l'existance de l'utilisateur dans la liste des dossiers
		String nom_user;
		for (int i = 0; i < listeFichiers.length; i++) {
			nom_user = commandeArgs[0].toLowerCase();
			System.out.println("user>> " + listeFichiers[i]);
			if (listeFichiers[i].toLowerCase().equals(nom_user)) {
				if (Serveur.IsUserConnected(nom_user))
					break;
				existe = true;
				this.utilisateur.setNom(nom_user);
				ps.println("0  utilisateur OK");
				break;
			}
		}
		
		if (!existe) {
			ps.println("2 L'utilisateur " + commandeArgs[0] + " n'existe pas ou est déjà connecté.");
			ps.println("0");
		}		
	}

	public void help() {
		ps.println("1 -- Commande USER --");
		ps.println("1 usage: user string:<nom_utilisateur>");
		ps.println("1 description: Indique le nom d'utilisateur pour pouvoir se connecter au serveur ftp.");
	}

}
