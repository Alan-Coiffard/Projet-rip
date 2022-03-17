package com.projet_rip;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PrimaryController {

    @FXML
    private TextField console_entry;

    @FXML
    private TextArea console_log;

    // Connexion des logs avec le client
    public void initialize() {
        App.getClient().setupConsoleLog(this.console_log);
    }

    // Envoie d'une commande
    @FXML
    private void onEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            App.getClient().commande(console_entry.getText());
            console_entry.setText("");
        }
    }
}
