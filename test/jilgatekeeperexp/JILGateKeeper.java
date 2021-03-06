package jilgatekeeperexp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.scene.layout.VBox;

public class JILGateKeeper extends Application {

    public static Map<String, FXMLLoader> LOADERS = new HashMap();
    public static List<AttendyModels> createData = new ArrayList();
    public static ListofAttendiesController ListController = null;

    public static ListofAttendiesController getListController() {
        return ListController;
    }

    public static void setListController(ListofAttendiesController ListController) {
        JILGateKeeper.ListController = ListController;
    }

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
        primaryStage.setTitle("Gate Keepers!");
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

    public static void main(String[] args) {
        launch(args);
    }

}
