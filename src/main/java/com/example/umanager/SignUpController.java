package com.example.umanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button button_createaccount;

    @FXML
    private Button button_login;

    @FXML
    private PasswordField pf_password;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_fullname;

    @FXML
    private TextField tf_phonenumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_createaccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tf_fullname.getText().trim().isEmpty() && !pf_password.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty() && !tf_phonenumber.getText().trim().isEmpty())
                {
                    DBUtils.signUpUser(event,tf_fullname.getText(),pf_password.getText());
                } else {
                    System.out.println("Please fill all the information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all the information to Sign up!");
                    alert.show();
                }
            }
        });
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"sample.fxml","Log In!",null);
            }
        });
    }
}

