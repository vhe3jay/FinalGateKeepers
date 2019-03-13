package jilgatekeeper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.nakpilse.sql.SQLTable;
import java.awt.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import static jilgatekeeper.JILGateKeeper.loginStage;


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
        loginStage.close();
        
    }
    
    @FXML
    public void loginForm(ActionEvent evt){
              loginInfo();
    }
    
    public void loginInfo(){
        
        boolean isUserfound = false;
        boolean isPasswordCorrect = false;
        boolean usher = false;
        User tmp = null;
        for(Object user:userList){
            if(userTextField.getText().equals(((User)user).getUsername())){
                isUserfound = true;
                if(pwTextField.getText().equals(((User)user).getPassword())){
                    isPasswordCorrect = true;
                    if(((User)user).getUserlevel().equals("USHER")){
                        usher = true;
                    }
                    tmp = (User)user;
                    break;
                }
            }
        }
        
        if(isUserfound){
            if(isPasswordCorrect){
                if(tmp.getUserlevel().equals("USHER")){
                    ((MainSceneController) JILGateKeeper.LOADERS.get("MAIN").getController()).showmainForm(tmp);
                    loginStage.close();
                    //System.out.println("usher");
                }else{
                    //System.out.println("head");
                    ((MainSceneController) JILGateKeeper.LOADERS.get("MAIN").getController()).showmainForm(tmp);
                    loginStage.close();
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
    /*
    public static Component findNextFocus() {
    Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
    Container root = c.getFocusCycleRootAncestor();

    FocusTraversalPolicy policy = root.getFocusTraversalPolicy();
    Component nextFocus = policy.getComponentAfter(root, c);
    if (nextFocus == null) {
      nextFocus = policy.getDefaultComponent(root);
    }
    return nextFocus;
  }
*/
    
    
}
