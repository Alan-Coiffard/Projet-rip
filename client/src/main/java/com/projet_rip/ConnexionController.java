package com.projet_rip;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private TextField champ_mdp;

    Stage stage;

    public void fermer(ActionEvent e) {            
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void connexion(ActionEvent e) {
        System.out.println("connexion");
        if(champ_identifiant.getText() != null && !champ_identifiant.getText().equals("") && champ_mdp.getText() != null && !champ_mdp.getText().equals("")){
            String identifiant = champ_identifiant.getText();
            String mdp = champ_mdp.getText();

            champ_identifiant.setStyle("-fx-border-color: black ; -fx-border-width: 0px ;");
            champ_mdp.setStyle("-fx-border-color: black ; -fx-border-width: 0px ;");

            App.getClient().commandeUSER(identifiant);
            App.getClient().commandePASS(mdp);
        } else {
            champ_identifiant.setStyle("-fx-border-color: black ; -fx-border-width: 0px ;");
            champ_mdp.setStyle("-fx-border-color: black ; -fx-border-width: 0px ;");

            if(champ_identifiant.getText() == null || champ_identifiant.getText().equals("")) {
                champ_identifiant.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
            if (champ_mdp.getText() == null || champ_mdp.getText().equals("")) {
                champ_mdp.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            } 
        }
    }

}