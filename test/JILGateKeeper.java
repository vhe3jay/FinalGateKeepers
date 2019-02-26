/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jilgatekeeper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author TestSubject
 */
public class JILGateKeeper extends Application {

    private static Scene scene_main;
    private static Scene scene_atendy;
    private static Scene scene_atendylist;

    public static final Stage Stage_1 = new Stage();
    public static final Stage Stage_2 = new Stage();
    
    public static MainSceneController MAIN_CONTROLLER = null;
    
    public static Map<String,FXMLLoader> Loaders = new HashMap();

    // NewAttendyFormController nafc = new NewAttendyFormController();
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader MAIN_LOADER = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        Loaders.put("MAINSCENE", MAIN_LOADER);
        Parent MAIN_ROOT = MAIN_LOADER.load();
        MAIN_CONTROLLER = MAIN_LOADER.getController();
        
        //Parent main_root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        Parent attendy_root = FXMLLoader.load(getClass().getResource("NewAttendyForm.fxml"));
        Parent attendy_list = FXMLLoader.load(getClass().getResource("ListofAttendies.fxml"));
        
        
        
        
        scene_main = new Scene(MAIN_ROOT);
        scene_atendy = new Scene(attendy_root);
        scene_atendylist = new Scene(attendy_list);

        stage.setScene(scene_main);
        
        //SETTING THE MAX SCREEN RESOLUTION
       Screen screen = Screen.getPrimary();
       Rectangle2D bounds = screen.getVisualBounds();
       stage.setX(bounds.getMinX());
       stage.setY(bounds.getMinY());
       stage.setWidth(bounds.getWidth());
       stage.setHeight(bounds.getHeight());
       //stage.setMinHeight(600);
       //stage.setMinWidth(800);
        //FOR MAXIMIZED WINDOW SIZE
        // MUST BE PLACE BEFORE THE SHOW COMMAND
        //stage.setMaximized(true);
        
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void showAttendyForm() {
        try {
            Stage_1.setScene(new Scene(FXMLLoader.load(JILGateKeeper.class.getResource("NewAttendyForm.fxml"))));
        } catch (IOException ex) {
            //FOR ERROR SEARCHING
            Logger.getLogger(JILGateKeeper.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage_1.show();
    }

    public static void showAttendyListForm() {
        
        try {
            //Object load = FXMLLoader.load(JILGateKeeper.class.getResource("ListofAttendies.fxml"));
            Stage_2.setScene(new Scene(FXMLLoader.load(JILGateKeeper.class.getResource("ListofAttendies.fxml"))));
        } catch (IOException ex) {
            //FOR ERROR SEARCHING
            Logger.getLogger(JILGateKeeper.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage_2.show();
    }
    

}
