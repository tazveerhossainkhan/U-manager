package com.example.umanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;
import java.io.IOException;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile,String title, String username) {
        Parent root = null;

        if(username!=null) {

           try {
               FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
               root = loader.load();
               LoggedInController loggedInController = loader.getController();
               loggedInController.setUserInformations(username);

           }catch (IOException e) {
               e.printStackTrace();
           }
        }else{
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,700,500));
        stage.show();
    }
    public static void signUpUser(ActionEvent event, String username, String password)
    {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/U-Manager-User","root","Root@123");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1,username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst())
            {
                System.out.println("User already exist! ");
                Alert alert = new Alert(Alert.AlertType.ERROR, "User already exist");
                alert.setContentText("You can not use this username");
                alert.show();
            }
            else
            {
                psInsert = connection.prepareStatement("INSERT INTO users (username,password) VALUES(?,?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.executeUpdate();
                changeScene(event,"logged-in.fxml","Welcome! ",username);

            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            if(resultSet != null)
            {
                try
                {
                    resultSet.close();
                } catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists != null)
            {
                try
                {
                    psCheckUserExists.close();
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if(psInsert != null)
            {
                try
                {
                    psInsert.close();
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if(connection != null)
            {
                try
                {
                    connection.close();
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInUser(ActionEvent event, String userName, String password)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/U-Manager-User","root","Root@123");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst())
            {
                System.out.println("User not found in the database.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect! ");
                alert.show();
            } else
            {
                while(resultSet.next())
                {
                    String retrievedPassword = resultSet.getString("password");

                    if(retrievedPassword.equals(password))
                    {
                        changeScene(event,"logged-in.fxml","Welcome!","username");        ///may get error

                    } else
                    {
                        System.out.println("Password did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided Credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally {
            if(resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null)
            {
                try {
                    preparedStatement.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null)
            {
                try{
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
/// U-Manager-User (Username : "root" --------- Password: "Root@123";