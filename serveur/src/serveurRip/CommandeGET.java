package serveurRip;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {

	public CommandeGET(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		if (this.commandeArgs.length == 0) {
			ps.println("Il manque le nom du fichier à envoyer");
			return;
		}
		for (int i = 0; i < Math.min(10, this.commandeArgs.length); i++) {
			try {
				String nomFichier = this.commandeArgs[i];
				String[] nf = nomFichier.split("/");
				File fichierACopier = new File(this.utilisateur.getAbsoluteChemin() + "/" + nomFichier);
				String chemin = fichierACopier.getCanonicalPath();
				
				// Vérification de sécurite
				String user_path = this.utilisateur.getUserPath();
				if (!chemin.substring(0, user_path.length()).equals(user_path)) {
					ps.println("2 chemin de fichier en dehors du dossier de l'utilisateur");
					continue;
				}

				if (fichierACopier.exists() && fichierACopier.isFile()) {

					FileInputStream inf = new FileInputStream(fichierACopier);
					int port = 5000 + this.utilisateur.getId();

					ServerSocket s = new ServerSocket(port);
					System.out.println("envoie du port " + port + " et du nom du fichier " + nomFichier);
					ps.println("4 " + port + " " + nf[nf.length - 1] );
					Socket sv = s.accept();
					ObjectOutputStream out = new ObjectOutputStream(sv.getOutputStream());
					byte buf[] = new byte[1024];
					int n;
					while ((n = inf.read(buf)) != -1) {
						out.write(buf, 0, n);
					}
					inf.close();
					out.close();
					s.close();
				} else {
					ps.println("2 Le fichier \"" + nomFichier + "\" n'existe pas");
					ps.println("2 Pas de récuperation.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
