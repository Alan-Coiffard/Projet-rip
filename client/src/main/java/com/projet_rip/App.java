package com.projet_rip;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private static Client client;
    public static Client getClient() { return client; }

    @Override
    public void start(Stage stage) throws IOException {
        client = new Client();

        client.start();

        scene = new Scene(loadFXML("connexion"), 640, 480);
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
        
    }

    @Override
    public void stop(){
        client.stopServeur();
    }
}