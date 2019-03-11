package jilgatekeeper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.nakpilse.sql.SQLTable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import static jilgatekeeper.JILGateKeeper.mainstage;
import jilgatekeeper.User;


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
        for(Object user:userList){
            if(userTextField.getText().equals(((User)user).getUsername())){
                if(pwTextField.getText().equals(((User)user).getPassword())){
                    MainSceneController.showForm();
                    mainstage.close();
                }
              }else{
                VBox contentBox = new VBox();
                contentBox.autosize();
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Log In Error!"));
                content.setBody(contentBox);
                JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                Button button = new Button("Okay");
                button.setAlignment(Pos.BASELINE_LEFT);
                contentBox.getChildren().addAll(new Label("Incorrect Username/Password"),button);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        dialog.close();
                    }
                });
                dialog.show();
            }
        }
    }
}
