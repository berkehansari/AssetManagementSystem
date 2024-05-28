package com.example.prototest2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdateAssetForm {

    @FXML
    private TextField assetNameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField reportedField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField whereField;

    private Asset selectedAsset;

    public void setAsset(Asset asset) {
        this.selectedAsset = asset;
        assetNameField.setText(asset.getAName());
        quantityField.setText(String.valueOf(asset.getAQuantity()));
        typeField.setText(asset.getAType());
        reportedField.setText(asset.getAReported());
        userNameField.setText(asset.getAccountUserName());
        whereField.setText(asset.getAWhere());
    }

    @FXML
    private void handleSaveButtonAction() {
        String updatedName = assetNameField.getText();
        int updatedQuantity = Integer.parseInt(quantityField.getText());
        String updatedType = typeField.getText();
        String updatedReported = reportedField.getText();
        String updatedUserName = userNameField.getText();
        String updatedWhere = whereField.getText();

        final String DB_URL = "jdbc:mysql://localhost:3306/ams1";
        final String USERNAME = "root";
        final String PASSWORD = "Berkehan123!";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sqlUpdate = "UPDATE asset SET AName = ?, AQuantity = ?, AType = ?, AReported = ?, AccountUserName = ?, AWhere = ? WHERE AUID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, updatedName);
            preparedStatement.setInt(2, updatedQuantity);
            preparedStatement.setString(3, updatedType);
            preparedStatement.setString(4, updatedReported);
            preparedStatement.setString(5, updatedUserName);
            preparedStatement.setString(6, updatedWhere);
            preparedStatement.setInt(7, selectedAsset.getAUID());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Asset updated successfully.");
            alert.showAndWait();

            // Close the form
            Stage stage = (Stage) assetNameField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
