package jilgatekeeper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.nakpilse.sql.SQLTable;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static jilgatekeeper.JILGateKeeper.loginStage;
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
    
    public static Stage mainStage = new Stage();
    
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
        boolean head = false;
        for(Object user:userList){
            if(userTextField.getText().equals(((User)user).getUsername())){
                isUserfound = true;
                if(pwTextField.getText().equals(((User)user).getPassword())){
                    isPasswordCorrect = true;
                    if(((User)user).getUserlevel().equals("USHER")){
                        head = true;
                    }
                    break;
                }
            }
        }
        
        if(isUserfound){
            if(isPasswordCorrect){
                if(!head){
                    adduserButton.setDisable(true);
                    showmainForm();
                    loginStage.close();
                    System.out.println("head");
                }else{
                    //Button bn = MainSceneController.adduserButton;
                    //adduserButton.disableProperty().setValue(true);
                    //MainSceneController.adduserButton.disableProperty().setValue(true);
                    //bn.setDisable(true);
                    System.out.println("usher");
                    showmainForm();
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
    
    public void showmainForm(){
        try {
        FXMLLoader MAIN_LOADER = new FXMLLoader(MainSceneController.class.getResource("MainScene.fxml"));
        Scene mainsc = new Scene(MAIN_LOADER.load());
        Screen screen = Screen.getPrimary();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Rectangle2D bounds = screen.getVisualBounds();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        mainStage.setWidth(width);
        mainStage.setHeight(height);
        mainStage.setX(bounds.getMinX());
        mainStage.setY(bounds.getMinY());
        mainStage.setWidth(bounds.getWidth());
        mainStage.setHeight(bounds.getHeight());
        mainStage.setMinWidth(1130);
        mainStage.setMinHeight(600);
        //FOR MAXIMIZED WINDOW SIZE
        mainStage.setMaximized(true);
        mainStage.setTitle("jESUS IS LORD NOVELETA");
        mainStage.setScene(mainsc);
        mainStage.show();
        } catch (IOException ex) {
            Logger.getLogger(NewUserFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
