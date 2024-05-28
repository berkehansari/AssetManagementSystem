package com.example.prototest2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AssetSearch {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> dataList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadDataFromDatabase();
        setupSearchFunctionality();
    }


    private void loadDataFromDatabase() {
        final String DB_URL = "jdbc:mysql://localhost:3306/ams1";
        final String USERNAME = "root";
        final String PASSWORD = "Berkehan123!";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT AName FROM asset"; // Veritabanı tablonuzun adını ve kolon adını güncelleyin
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dataList.add(resultSet.getString("AName")); // Veritabanı kolonunuzun adını güncelleyin
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
