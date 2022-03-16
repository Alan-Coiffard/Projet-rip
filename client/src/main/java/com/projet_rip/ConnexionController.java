package com.projet_rip;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConnexionController {

    @FXML
    private Button quitterButton;
    @FXML
    private AnchorPane scenePane;

    Stage stage;

    public void fermer(ActionEvent e) {
        System.out.println("bye");
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void connexion(ActionEvent e) {
        System.out.println("connexion");
    }

}