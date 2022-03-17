package com.projet_rip;

import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.*;

import javafx.scene.control.TextArea;

public class Client extends Thread {

    final String serverHost = "localhost";
    public Socket socketOfClient = null;
    public PrintStream ps = null;
    public BufferedReader is = null;

    private boolean continuer = true;

    private MessageClient msg_client = new MessageClient();

    private StringBuilder logs = new StringBuilder("");
    private TextArea console_log = null;

    public void run() {
        try {
            // Connexion au serveur
            socketOfClient = new Socket(serverHost, 3030);
            ps = new PrintStream(socketOfClient.getOutputStream());
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

            String msg_serveur;
            String[] msg_serveur_split;
            String msg = "";
            int port;

            // Gestion de la réception des paquets
            while (this.continuer && !msg.equals("bye")) {
                do {
                    msg_serveur = is.readLine();
                    msg_serveur_split = msg_serveur.split(" ");

                    switch (msg_serveur.charAt(0)) {
                        case '0':
                        case '1':
                            System.out.println(msg_serveur);
                            if (msg_serveur.length() >= 2)
                                logMessage(msg_serveur.substring(2));
                            break;
                        case '2':
                            System.err.println(msg_serveur);
                            if (msg_serveur.length() >= 2)
                                logMessage(msg_serveur.substring(2));
                            break;
                        case '3':
                            port = Integer.parseInt(msg_serveur_split[1]);
                            try {
                                Socket s = new Socket(serverHost, port);
                                String fichier = msg_serveur_split[2];
                                FileInputStream inf = new FileInputStream(new File(fichier));
                                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                                byte buf[] = new byte[1024];
                                int n;
                                while ((n = inf.read(buf)) != -1) {
                                    out.write(buf, 0, n);
                                }
                                inf.close();
                                out.close();
                                s.close();
                            } catch (IOException e) {
                                Socket s = new Socket(serverHost, port);
                                s.close();
                                System.err.println("Impossible de récuperer le fichier.");
                            }
                            break;
                        case '4':
                            try {
                                port = Integer.parseInt(msg_serveur_split[1]);
                                Socket s = new Socket(serverHost, port);

                                String chemin = System.getProperty("user.dir") + "/" + msg_serveur_split[2];
                                System.out.println(chemin);
                                File nouveauFichier = new File(chemin);

                                System.out.println("Fichier créé...");

                                FileOutputStream fileOut = new FileOutputStream(nouveauFichier);

                                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                                byte buf[] = new byte[1024];
                                int n;
                                while ((n = in.read(buf)) != -1) {
                                    // System.out.println(n);
                                    fileOut.write(buf, 0, n);
                                }

                                System.out.println("Fichier copié.");
                                fileOut.close();
                                s.close();
                            } catch (IOException e) {
                                System.err.println("Impossible de récuperer le fichier.");
                            }
                            break;
                        case '5':
                            App.setRoot("primary");
                            break;
                    }
                } while (msg_serveur.charAt(0) != '0');

                // Envoie du prochain message
                msg = msg_client.getMessage();
                logMessage(">> " + msg);
                ps.println(msg);
            }

            // Fermeture
            is.close();
            ps.close();
            socketOfClient.close();
        } catch (IOException e) {
            System.err.println("Client déconnecter");
        }
    }

    // Ajout d'un message dans les logs
    public synchronized void logMessage(String msg) {
        this.logs.append(msg + "\n");
        if (console_log != null) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
            }
            this.console_log.setText(logs.toString());
            console_log.appendText("");
        }
    }

    // Connexion à l'UI des logs
    public void setupConsoleLog(TextArea textarea) {
        this.console_log = textarea;
        this.console_log.setText(logs.toString());
    }

    // Arret serveur
    public synchronized void stopServeur() {
        this.continuer = false;
        msg_client.setMessage("bye");
    }

    // -- Commandes --

    public synchronized void commandeUSER(String nom) {
        msg_client.setMessage("user " + nom);
    }

    public synchronized void commandePASS(String mdp) {
        msg_client.setMessage("pass " + mdp);
    }

    public synchronized void commande(String cmd) {
        msg_client.setMessage(cmd);
    }
}
