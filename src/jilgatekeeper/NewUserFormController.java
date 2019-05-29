package jilgatekeeper;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewUserFormController implements Initializable {
    
    User usermodel = new User();
    public static Stage myStage = null;

    public User getUsermodel() {
        return usermodel;
    }

    public void setUsermodel(User usermodel) {
        this.usermodel = usermodel;
    }

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField pwField;

    @FXML
    private PasswordField verpwField;

    @FXML
    private ComboBox levelCombo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        levelCombo.getItems().addAll(User.securitylevel.values());
        bindcomponents();
    }    
    
    public void bindcomponents(){
        usermodel.nameProperty().bind(nameField.textProperty());
        usermodel.contactnumberProperty().bind(numberField.textProperty());
        usermodel.usernameProperty().bind(userField.textProperty());
        usermodel.passwordProperty().bind(pwField.textProperty());
        usermodel.userlevelProperty().bind(levelCombo.valueProperty().asString());
    }
    
    public void adduser(ActionEvent event){
        if(pwField.equals(verpwField)){
            usermodel.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
            usermodel.save();
            myStage.close();
        }else{
            Alert alert = new Alert(AlertType.WARNING, "Password do not match",ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    
    public static void showForm(){
        Platform.runLater(()->{
                    try {
                        FXMLLoader NEWUSER_LOADER = new FXMLLoader(NewUserFormController.class.getResource("NewUserForm.fxml"));
                        Scene newusersc = new Scene(NEWUSER_LOADER.load());
                        myStage = new Stage();
                        myStage.initStyle(StageStyle.UTILITY);
                        myStage.setTitle("Create New User");
                        myStage.setScene(newusersc);
                        myStage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(NewUserFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
        });
    }
        
    
}
