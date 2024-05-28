package com.example.prototest2;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AssetAU {

    @FXML
    private TextField assetNameField;

    @FXML
    private TextField assetQuantityField;

    @FXML
    private TextField assetTypeField;

    @FXML
    private TextField assetReportedField;

    @FXML
    private TextField assetWhereField;

    @FXML
    private TextField userNameField;

    @FXML
    private Button addButton;



    @FXML
    public void initialize() {
        addButton.setOnAction(event -> addAssetToDatabase());
    }



    public void addAssetToDatabase() {
        final String DB_URL = "jdbc:mysql://localhost:3306/ams1";
        final String USERNAME = "root";
        final String PASSWORD = "Berkehan123!";

        String assetName = assetNameField.getText();
        String assetQuantityStr = assetQuantityField.getText();
        String assetType = assetTypeField.getText();
        String assetReported = assetReportedField.getText();
        String assetWhere = assetWhereField.getText();
        String accountUserName = userNameField.getText(); // Giriş yapmış kullanıcı adı

        if (assetName.isEmpty() || assetQuantityStr.isEmpty() || assetType.isEmpty() || assetReported.isEmpty() || assetWhere.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Please enter all fields");
            return;
        }

        int assetQuantity;
        try {
            assetQuantity = Integer.parseInt(assetQuantityStr);
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Form Error!", "Please enter a valid number for quantity");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO asset (AName, AQuantity, AType, AReported, AWhere, AccountUserName) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, assetName);
            preparedStatement.setInt(2, assetQuantity);
            preparedStatement.setString(3, assetType);
            preparedStatement.setString(4, assetReported);
            preparedStatement.setString(5, assetWhere);
            preparedStatement.setString(6, accountUserName); // Giriş yapmış kullanıcı adını buraya ekleyin

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(AlertType.INFORMATION, "Success", "Asset added successfully!");
                assetNameField.clear();
                assetQuantityField.clear();
                assetTypeField.clear();
                assetReportedField.clear();
                assetWhereField.clear();
            } else {
                showAlert(AlertType.ERROR, "Failure", "Failed to add asset");
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "An error occurred while connecting to the database");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
