package jilgatekeeper;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class NewUserFormController implements Initializable {
    
    UserModel usermodel = new UserModel();

    public UserModel getUsermodel() {
        return usermodel;
    }

    public void setUsermodel(UserModel usermodel) {
        this.usermodel = usermodel;
    }
    
    @FXML
    private AnchorPane parentPane;

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
    private Button regButton;

    @FXML
    private ComboBox levelCombo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        levelCombo.getItems().addAll(UserModel.securitylevel.values());
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
        usermodel.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        usermodel.save();
    }
    public static Stage myStage = null;
    public static void showForm(){
        try {
            FXMLLoader NEWUSER_LOADER = new FXMLLoader(NewUserFormController.class.getResource("NewUserForm.fxml"));
            Scene newusersc = new Scene(NEWUSER_LOADER.load());
            myStage = new Stage();
            myStage.setTitle("Create New User");
            myStage.setScene(newusersc);
            myStage.show();
        } catch (IOException ex) {
            Logger.getLogger(NewUserFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
