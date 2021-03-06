package serveurRip;

import java.io.*;

public class User {
    private String nom = "";
    private boolean is_connected = false;
    private String chemin = "/";
    private int id = 0;

    public User() {
    }
    
    public void setNom(String nom) {
        // On ne peux pas changer le nom si l'utilisateur est connecté
        if (this.is_connected)
            return;
        this.nom = nom;
    }
    public String getNom() {
        return this.nom;
    }

    // ex: /mon_test (après un 'cd mon_test' de l'utilisateur)
    public String getChemin() {
        return this.chemin;
    }
    // ex: ./utilisateurs/seb
    public String getUserPath() {
        return new File(Serveur.getPath() + "/" + this.nom).getPath();
    }
    // ex: ./utilisateurs/seb/mon_test
    public String getAbsoluteChemin() {
        return getUserPath() + this.chemin; 
    }
    public void setChemin(String chemin) {
        if (chemin.length() == 0)
            this.chemin = "/";
        else
            this.chemin = chemin;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Vérifie que le mot de passe en argument est le même que celui du dossier utilisateur
    // retourne vrai si la connecion est validée
    public boolean Connect(String mdp) throws FileNotFoundException, IOException {
        if (this.is_connected || this.nom.isEmpty() || mdp.isEmpty())
            return false;

        // Récupération du mot de passe dans le dossier utilisateur
        FileReader inputFile = new FileReader(getUserPath() + "/mdp.txt");
        BufferedReader bufferReader = new BufferedReader(inputFile);
        String mdp_valid = bufferReader.readLine(); // Bon mot de passe
        bufferReader.close();
        
        if (!mdp.equals(mdp_valid))
            return false;

        this.is_connected = true;
        this.chemin = "/";
        return this.is_connected;
    }
    public void Disconnect() {
        this.is_connected = false;
    }
    public boolean IsConnected() {
        return this.is_connected;
    }

    // Un utilisateur est représenté par son nom
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return ((User)o).getNom().equals(this.nom);
    }
}
