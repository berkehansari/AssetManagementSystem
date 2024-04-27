package com.example.prototest2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("fxml/LoginScreen.fxml"));
        loader.load();
    }
}