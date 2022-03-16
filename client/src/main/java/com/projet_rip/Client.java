package com.projet_rip;

import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client extends Thread {
    
    final String serverHost = "localhost";
    public Socket socketOfClient = null;
    public PrintStream ps = null;
    public BufferedReader is = null;

    private boolean continuer = true;

    private MessageClient msg_client = new MessageClient();

    public void run()
    {
        try {
            socketOfClient = new Socket(serverHost, 3030);
            ps = new PrintStream(socketOfClient.getOutputStream());
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
            
            String msg_serveur;

            while (this.continuer) {
                
                do {
                msg_serveur = is.readLine();
                
                    switch (msg_serveur.charAt(0)) {
                        case '0':       
                            System.out.println(msg_serveur);
                        break;
                        case '1':
                            System.out.println(msg_serveur);
                        break;
                        case '2':
                            System.err.println(msg_serveur);
                        break;
                    }
                } while (msg_serveur.charAt(0) != '0');          
                ps.println(msg_client.getMessage());
            }
            
            ps.close();
            socketOfClient.close();
        } catch (IOException e) {
            System.err.println("Client.java erreur!!");
        }
    }

    public synchronized void stopServeur()
    {
        this.continuer = false;        
        try {
            is.close();
            msg_client.notifyAll();
        } catch (IOException e) {
        }
    }

    public synchronized void commandeUSER(String nom) {
        msg_client.setMessage("user " + nom);
    }

    public synchronized void commandePASS(String mdp) {
        msg_client.setMessage("pass " + mdp);
    }

    public synchronized void commandeCD(String arg) {
        msg_client.setMessage("cd " + arg);
    }

    public synchronized void commandeLS() {
        msg_client.setMessage("ls");
    }
}
