package serveurRip;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommandExecutor {
	public static void executeCommande(PrintStream ps, String commande, User user) throws UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();

		if (user.IsConnected()) {
			if (commande.split(" ")[0].toLowerCase().equals("cd"))
				(new CommandeCD(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("get"))
				(new CommandeGET(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("ls"))
				(new CommandeLS(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("pwd"))
				(new CommandePWD(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("stor"))
				(new CommandeSTOR(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("mkdir"))
				(new CommandeMKDIR(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("touch"))
				(new CommandeTOUCH(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("help"))
				(new CommandeHELP(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("echo"))
				(new CommandeECHO(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("cat"))
				(new CommandeCAT(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("rm"))
				(new CommandeRM(ps, commande, user)).execute();
			else
				ps.println("1 Commande introuvable");
		} else {
			if (commande.split(" ")[0].toLowerCase().equals("pass"))
				(new CommandePASS(ps, commande, user)).execute();
			else if (commande.split(" ")[0].toLowerCase().equals("user"))
				(new CommandeUSER(ps, commande, user)).execute();
			else
				ps.println("0 Vous n'etes pas connecté.");
		}

		if (user.IsConnected())
			ps.println("0 " + user.getNom() + "@" + ip.getHostAddress() + ":~" + user.getChemin() + "$");
	}

}
