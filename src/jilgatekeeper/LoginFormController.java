package jilgatekeeper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static jilgatekeeper.JILGateKeeper.mainstage;


public class LoginFormController implements Initializable {

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
    public void loginForm(ActionEvent event) {
        try{
            FXMLLoader MAIN_LOADER = new FXMLLoader(this.getClass().getResource("MainScene.fxml"));
            Scene mainsc = new Scene(MAIN_LOADER.load());
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            mainScene.setX(bounds.getMinX());
            mainScene.setY(bounds.getMinY());
            mainScene.setWidth(bounds.getWidth());
            mainScene.setHeight(bounds.getHeight());
            mainScene.setMinWidth(1130);
            mainScene.setMinHeight(700);
            //FOR MAXIMIZED WINDOW SIZE
            mainScene.setMaximized(true);
            mainScene.setTitle("JESUS IS LORD NOVELETA!");
            mainScene.setScene(mainsc);
            mainScene.show();
            mainstage.close();}
        catch(IOException ex){
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void cancelButton(ActionEvent evt){
        mainstage.close();
    }
}
