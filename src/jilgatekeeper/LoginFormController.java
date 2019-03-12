package jilgatekeeper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.nakpilse.sql.SQLTable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import static jilgatekeeper.JILGateKeeper.mainstage;
import static jilgatekeeper.MainSceneController.adduserButton;


public class LoginFormController implements Initializable {
    User usermodel = new User();

    public User getUsermodel() {
        return usermodel;
    }

    public void setUsermodel(User usermodel) {
        this.usermodel = usermodel;
    }
    
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXPasswordField pwTextField;

    @FXML
    private JFXTextField userTextField;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton cancelButton;
    
    List userList = new ArrayList();
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userList = SQLTable.list(User.class);
    }    
    
    @FXML
    public void cancelButton(ActionEvent event) {
        mainstage.close();
        
    }
    
    @FXML
    public void loginForm(ActionEvent evt){
              loginInfo();
    }
    
    public void loginInfo(){
        
        boolean isUserfound = false;
        boolean isPasswordCorrect = false;
        boolean usher = false;
        for(Object user:userList){
            if(userTextField.getText().equals(((User)user).getUsername())){
                isUserfound = true;
                if(pwTextField.getText().equals(((User)user).getPassword())){
                    isPasswordCorrect = true;
                    if(((User)user).getUserlevel().equals("USHER")){
                        usher = true;
                    }
                    break;
                }
                
            }
        }
        
        if(isUserfound){
            if(isPasswordCorrect){
                if(!usher){
                    MainSceneController.showForm();
                    mainstage.close();
                    System.out.println("head");
                }else{
                    //Button bn = MainSceneController.adduserButton;
                    //adduserButton.disableProperty().setValue(true);
                    //MainSceneController.adduserButton.disableProperty().setValue(true);
                    //bn.setDisable(true);
                    System.out.println("usher");
                    MainSceneController.showForm();
                    mainstage.close();
                }
            }else{
                //Incorrect Pass
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect password!",ButtonType.CLOSE);
                alert.showAndWait();
            }
        }else{
            //No User found
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username not found!",ButtonType.CLOSE);
                alert.showAndWait();
        }
    }
}
