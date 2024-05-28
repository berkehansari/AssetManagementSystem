package com.example.prototest2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AssetSearch {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> listView;

    @FXML
    private CheckBox tangibleCheckBox;

    @FXML
    private CheckBox intangibleCheckBox;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button infoButton;

    private ObservableList<String> dataList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadDataFromDatabase();
        setupSearchFunctionality();
        setupFilterFunctionality();
        setupButtonActions();
    }

    private void loadDataFromDatabase() {
        final String DB_URL = "jdbc:mysql://localhost:3306/ams1";
        final String USERNAME = "root";
        final String PASSWORD = "Berkehan123!";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT AName FROM asset";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dataList.add(resultSet.getString("AName"));
            }

            listView.setItems(dataList);

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupSearchFunctionality() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterList(newValue);
        });
    }

    private void setupFilterFunctionality() {
        tangibleCheckBox.setOnAction(event -> applyFilters());
        intangibleCheckBox.setOnAction(event -> applyFilters());
    }

    private void applyFilters() {
        final String DB_URL = "jdbc:mysql://localhost:3306/ams1";
        final String USERNAME = "root";
        final String PASSWORD = "Berkehan123!";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT AName FROM asset WHERE AType IN (";
            if (tangibleCheckBox.isSelected() && intangibleCheckBox.isSelected()) {
                sql += "0, 1)";
            } else if (tangibleCheckBox.isSelected()) {
                sql += "1)";
            } else if (intangibleCheckBox.isSelected()) {
                sql += "0)";
            } else {
                sql = "SELECT AName FROM asset";
            }

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            dataList.clear();
            while (resultSet.next()) {
                dataList.add(resultSet.getString("AName"));
            }

            listView.setItems(dataList);

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupButtonActions() {
        updateButton.setOnAction(event -> openUpdateAssetForm());
        deleteButton.setOnAction(event -> deleteSelectedAsset());
        infoButton.setOnAction(event -> showAlertIfNoSelection("Info"));
    }

    private void openUpdateAssetForm() {
        String selectedAssetName = listView.getSelectionModel().getSelectedItem();
        if (selectedAssetName == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select an asset first.");
            alert.showAndWait();
        } else {
            try {
                final String DB_URL = "jdbc:mysql://localhost:3306/ams1";
                final String USERNAME = "root";
                final String PASSWORD = "Berkehan123!";

                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "SELECT * FROM asset WHERE AName = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, selectedAssetName);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Asset selectedAsset = new Asset(
                            resultSet.getInt("AUID"),
                            resultSet.getString("AName"),
                            resultSet.getInt("AQuantity"),
                            resultSet.getString("AType"),
                            resultSet.getString("AReported"),
                            resultSet.getString("AccountUserName"),
                            resultSet.getString("AWhere")
                    );

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/UpdateAssetForm.fxml"));
                    Parent parent = loader.load();

                    UpdateAssetForm controller = loader.getController();
                    controller.setAsset(selectedAsset);

                    Stage stage = new Stage();
                    stage.setTitle("Update Asset");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(parent));
                    stage.showAndWait();
                }

                resultSet.close();
                preparedStatement.close();
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private void showAlertIfNoSelection(String action) {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select an asset first.");
            alert.showAndWait();
        } else {
            // Perform action for "Update" or "Info"
            System.out.println(action + " button pressed.");
        }
    }

    private void deleteSelectedAsset() {
        String selectedAsset = listView.getSelectionModel().getSelectedItem();
        if (selectedAsset == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select an asset first.");
            alert.showAndWait();
        } else {
            final String DB_URL = "jdbc:mysql://localhost:3306/ams1";
            final String USERNAME = "root";
            final String PASSWORD = "Berkehan123!";

            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                // Delete related records in hrecordtangible table
                String sqlDeleteHRecordTangible = "DELETE FROM hrecordtangible WHERE AccountUserNameHRT = (SELECT AccountUserName FROM asset WHERE AName = ?)";
                PreparedStatement preparedStatementHRecordTangible = conn.prepareStatement(sqlDeleteHRecordTangible);
                preparedStatementHRecordTangible.setString(1, selectedAsset);
                preparedStatementHRecordTangible.executeUpdate();

                // Delete related records in hrecordintangible table
                String sqlDeleteHRecordIntangible = "DELETE FROM hrecordintangible WHERE AccountUserNameHRI = (SELECT AccountUserName FROM asset WHERE AName = ?)";
                PreparedStatement preparedStatementHRecordIntangible = conn.prepareStatement(sqlDeleteHRecordIntangible);
                preparedStatementHRecordIntangible.setString(1, selectedAsset);
                preparedStatementHRecordIntangible.executeUpdate();

                // Delete related records in intangibleasset table
                String sqlDeleteIntangible = "DELETE FROM intangibleasset WHERE AUserNameI = (SELECT AccountUserName FROM asset WHERE AName = ?)";
                PreparedStatement preparedStatementIntangible = conn.prepareStatement(sqlDeleteIntangible);
                preparedStatementIntangible.setString(1, selectedAsset);
                preparedStatementIntangible.executeUpdate();

                // Delete related records in tangibleasset table
                String sqlDeleteTangible = "DELETE FROM tangibleasset WHERE AUserNameT = (SELECT AccountUserName FROM asset WHERE AName = ?)";
                PreparedStatement preparedStatementTangible = conn.prepareStatement(sqlDeleteTangible);
                preparedStatementTangible.setString(1, selectedAsset);
                preparedStatementTangible.executeUpdate();

                // Delete the selected asset
                String sqlDeleteAsset = "DELETE FROM asset WHERE AName = ?";
                PreparedStatement preparedStatementAsset = conn.prepareStatement(sqlDeleteAsset);
                preparedStatementAsset.setString(1, selectedAsset);
                preparedStatementAsset.executeUpdate();

                dataList.remove(selectedAsset);
                listView.setItems(dataList);

                preparedStatementHRecordTangible.close();
                preparedStatementHRecordIntangible.close();
                preparedStatementIntangible.close();
                preparedStatementTangible.close();
                preparedStatementAsset.close();
                conn.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Asset deleted successfully.");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void filterList(String filter) {
        if (filter == null || filter.isEmpty()) {
            listView.setItems(dataList);
        } else {
            ObservableList<String> filteredList = FXCollections.observableArrayList();
            for (String item : dataList) {
                if (item.toLowerCase().contains(filter.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            listView.setItems(filteredList);
        }
    }
}
