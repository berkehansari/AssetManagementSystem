package com.example.prototest2;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Dashboard {
    @FXML
    private Label aid;


    @FXML
    public void AssetSearch() throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxml/AssetSearch.fxml"));
        AnchorPane dashboardPane = loader.load();

        // Load the new scene and set it to the stage
        Scene dashboardScene = new Scene(dashboardPane);
        Stage stage = (Stage) ((Node) aid).getScene().getWindow(); // assuming idearea is a Node in the current scene
        stage.setScene(dashboardScene);
        stage.show();




    }
}
