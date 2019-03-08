package jilgatekeeper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.nakpilse.sql.SQLTable;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static jilgatekeeper.JILGateKeeper.mainstage;


public class LoginFormController implements Initializable {
    UserModel usermodel = new UserModel();

    public UserModel getUsermodel() {
        return usermodel;
    }

    public void setUsermodel(UserModel usermodel) {
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
    
    
    public static Stage mainScene = new Stage();
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void cancelButton(ActionEvent event) {
        mainstage.close();
    }
    
    @FXML
    public void loginForm(ActionEvent evt){
    if(userTextField.getText() != null){
        if(userTextField.getText().equals(usermodel.getName())){
            if(pwTextField.getText().equals(usermodel.getPassword())){
                MainSceneController.showForm();
                mainstage.close();
            }else{
                    VBox contentBox = new VBox();
                    contentBox.getChildren().add(new Label("Incorrect Username or Password"));
                    contentBox.autosize();
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Log In Error!"));
                    content.setBody(contentBox);
                    JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                    JFXButton button = new JFXButton("Okay");
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            dialog.close();
                        }
                    });
            }
        }else{
                    VBox contentBox = new VBox();
                    contentBox.getChildren().add(new Label("Incorrect Username or Password"));
                    contentBox.autosize();
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Log In Error!"));
                    content.setBody(contentBox);
                    JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                    JFXButton button = new JFXButton("Okay");
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            dialog.close();
                        }
                    });
        }
        }else{
        VBox contentBox = new VBox();
                    contentBox.getChildren().add(new Label("Incorrect Username or Password"));
                    contentBox.autosize();
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Log In Error!"));
                    content.setBody(contentBox);
                    JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                    JFXButton button = new JFXButton("Okay");
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            dialog.close();
                        }
                    });
    }
    mainstage.close();
    }
    
}
