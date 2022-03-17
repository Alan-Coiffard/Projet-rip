package serveurRip;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeHELP extends Commande {

	public CommandeHELP(PrintStream ps, String commandeStr, User user) {
		super(ps, commandeStr, user);
	}

	public void execute() {
		if (this.commandeArgs.length == 0) {
            ps.println("1 cat - cd - echo - get - help - ls - mkdir - pass - pwd - stor - touch - user");
            return;
        }

        // Affichage de l'aide en fonction de l'argument
        switch (this.commandeArgs[0]) {
            case "cat":
                new CommandeCAT(ps, "", null).help();
            break;
            case "cd":
                new CommandeCD(ps, "", null).help();
            break;
            case "echo":
                new CommandeECHO(ps, "", null).help();
            break;
            case "get":
                new CommandeGET(ps, "", null).help();
            break;
            case "help":
                new CommandeHELP(ps, "", null).help();
            break;
            case "ls":
                new CommandeLS(ps, "", null).help();
            break;
            case "mkdir":
                new CommandeMKDIR(ps, "", null).help();
            break;
            case "pass":
                new CommandePASS(ps, "", null).help();
            break;
            case "pwd":
                new CommandePWD(ps, "", null).help();
            break;
            case "stor":
                new CommandeSTOR(ps, "", null).help();
            break;
            case "touch":
                new CommandeTOUCH(ps, "", null).help();
            break;
            case "user":
                new CommandeUSER(ps, "", null).help();
            break;
            case "rm":
                new CommandeRM(ps, "", null).help();
            break;
            default:
                ps.println("1 Commande inconnu.");
            break;
        }
	}

	public void help() {
		ps.println("1 -- Commande HELP --");
		ps.println("1 usage: help");
		ps.println("1 usage: help string:<commande>");
		ps.println("1 description: Indique la liste des commandes utilisable ou affiche les information sur une commande spécifique.");
	}
}
