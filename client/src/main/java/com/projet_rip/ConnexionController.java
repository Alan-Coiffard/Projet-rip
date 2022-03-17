package com.projet_rip;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConnexionController {

    @FXML
    private Button quitterButton;
    @FXML
    private VBox scenePane;
    @FXML
    private TextField champ_identifiant;
    @FXML
    private PasswordField champ_mdp;

    Stage stage;

    // Fermeture de l'application
    public void fermer(ActionEvent e) {            
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    // Envoie des identifiants de connexion
    public void connexion(ActionEvent e) {
        System.out.println("connexion");
        App.getClient().commandeUSER(champ_identifiant.getText());
        App.getClient().commandePASS(champ_mdp.getText());
    }
}