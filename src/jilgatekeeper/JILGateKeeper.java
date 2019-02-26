/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jilgatekeeper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.scene.layout.VBox;

/**
 *
 * @author Duskmourne
 */
public class JILGateKeeper extends Application {

    public static Map<String, FXMLLoader> LOADERS = new HashMap();

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader MAIN_LOADER = new FXMLLoader(this.getClass().getResource("MainScene.fxml"));
        FXMLLoader LIST_LOADER = new FXMLLoader(this.getClass().getResource("ListofAttendies.fxml"));
        FXMLLoader NEW_LOADER = new FXMLLoader(this.getClass().getResource("NewAttendyForm.fxml"));
        LOADERS.put("MAIN", MAIN_LOADER);
        LOADERS.put("LIST", LIST_LOADER);
        LOADERS.put("NEW", NEW_LOADER);
        VBox MAIN_ROOT = MAIN_LOADER.load();
        Scene mainsc = new Scene(MAIN_ROOT);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(mainsc);
        primaryStage.show();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        //stage.setMinHeight(600);
        //stage.setMinWidth(800);
        //FOR MAXIMIZED WINDOW SIZE
        // MUST BE PLACE BEFORE THE SHOW COMMAND
        //stage.setMaximized(true);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void showAttendyForm() {
        JILGateKeeper.LOADERS.get("NEW").getController();
    }

    public static void showAttendyListForm() {

        LOADERS.getClass().getResource("ListofAttendies.fxml");
    }

}
