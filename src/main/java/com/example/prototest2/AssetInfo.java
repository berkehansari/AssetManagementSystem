package com.example.prototest2;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AssetInfo {

    @FXML
    private Label nameLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label reportedLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label expenseLabel;

    @FXML
    private Label currentValueLabel;

    @FXML
    private Label purchaseValueLabel;

    @FXML
    private Label interestLabel;

    @FXML
    private LineChart<String, Number> historyChart;

    public void setAsset(Asset asset) {
        nameLabel.setText("Name: " + asset.getAName());
        quantityLabel.setText("Quantity: " + asset.getAQuantity());
        typeLabel.setText("Type: " + asset.getAType());
        reportedLabel.setText("Reported: " + asset.getAReported());
        usernameLabel.setText("Account Username: " + asset.getAccountUserName());
        locationLabel.setText("Location: " + asset.getAWhere());

        if ("1".equals(asset.getAType())) { // Tangible
            expenseLabel.setText("Expense: " + asset.getExpense());
            currentValueLabel.setText("Current Value: " + asset.getCurrentValue());
            purchaseValueLabel.setText("Purchase Value: " + asset.getPurchaseValue());
            interestLabel.setVisible(false);
        } else if ("0".equals(asset.getAType())) { // Intangible
            expenseLabel.setVisible(false);
            currentValueLabel.setText("Current Value: " + asset.getCurrentValue());
            purchaseValueLabel.setText("Purchase Value: " + asset.getPurchaseValue());
            interestLabel.setText("Interest: " + asset.getInterest());
        }
    }

    public void initialize() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();


        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(30);
        yAxis.setTickUnit(1);


        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return String.format("%d", object.intValue());
            }
        });

        historyChart.setCreateSymbols(false);
        historyChart.setAnimated(false);
    }


    public void loadHistoricalRecords(String assetName, String type) {
        final String DB_URL = "jdbc:mysql://localhost:3306/ams1";
        final String USERNAME = "root";
        final String PASSWORD = "Berkehan123!";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql;
            if ("1".equals(type)) {
                sql = "SELECT RecordDate, RecordCode FROM hrecordtangible WHERE ANameHRT = ?";
            } else {
                sql = "SELECT RecordDate, RecordCode FROM hrecordintangible WHERE ANameHRI = ?";
            }

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, assetName);
            ResultSet resultSet = preparedStatement.executeQuery();

            XYChart.Series<String, Number> series = new XYChart.Series<>(); // Specify String as the X axis type
            while (resultSet.next()) {
                String date = resultSet.getDate("RecordDate").toString(); // Convert Date to String
                series.getData().add(new XYChart.Data<>(date, resultSet.getDouble("RecordCode")));
            }
            historyChart.getData().add(series);


            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
