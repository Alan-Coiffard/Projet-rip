package serveurRip;
import java.io.File;
import java.io.PrintStream;

public class CommandeLS extends Commande {

	public CommandeLS(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		String chemin = this.utilisateur.getAbsoluteChemin();

		File repertoire = new File(chemin);
		System.out.println(chemin);
		String[] listeFichiers;
		if (!repertoire.isDirectory()){
			ps.println("Ce n'est pas un dossier");
		} else {
			listeFichiers = repertoire.list();
			for (int i = 0; i < listeFichiers.length;i++)
				ps.println("1 " + listeFichiers[i]);
		}
		//ps.println("La commande ls n'est pas encoré implémentée");
	}

}
