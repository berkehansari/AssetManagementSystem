package com.example.prototest2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

import java.sql.*;


public class LoginScreen {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField idearea;

    @FXML
    private PasswordField passarea;

    @FXML
    public void onSignInButtonClick() throws IOException {
        //System.out.println("Test Passed!");
        String AccountID = String.valueOf(idearea.getText());
        String AccountPassword = String.valueOf(passarea.getText());

        user = getAuthenticatedUser(AccountID, AccountPassword);

        if(user != null){
            System.out.println("Login Success");

            //ATID.
            System.out.println("ATID: "+user.AccountTypeID);


            dispose();
        }else{
            System.out.println("Invalid Pass or ID.");
        }

    }

    private void dispose() {
    }

    public User user;
    private User getAuthenticatedUser(String AccountID, String AccountPassword){
        User user = null;


        final String DB_URL = "jdbc:mysql://localhost:3306/ams1";
        final String USERNAME = "root";
        final String PASSWORD = "Berkehan123!";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM accounts WHERE AccountID=? AND AccountPassword=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, AccountID);
            preparedStatement.setString(2, AccountPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User();
                user.AccountID = resultSet.getString("AccountID");
                user.AccountPassword = resultSet.getString("AccountPassword");
                user.AccountTypeID = resultSet.getString("AccountTypeID");
            }

            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }


        return user;
    }
}
