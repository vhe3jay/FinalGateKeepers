package jilgatekeeper;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


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
    }    
    
    public void bindcomponents(){
        usermodel.nameProperty().bind(nameField.textProperty());
        usermodel.contactnumberProperty().bind(numberField.textProperty());
        usermodel.usernameProperty().bind(userField.textProperty());
        usermodel.passwordProperty().bind(pwField.textProperty());
        usermodel.userlevelProperty().bind(levelCombo.valueProperty().asString());
        
    }
    
}
