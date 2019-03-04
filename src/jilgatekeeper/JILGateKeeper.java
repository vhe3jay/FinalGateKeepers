package jilgatekeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class JILGateKeeper extends Application {

    public static Map<String, FXMLLoader> LOADERS = new HashMap();
    public static List<AttendyModels> createData = new ArrayList();
    public static ListofAttendiesController ListController = null;
    public static Stage listStage = new Stage();

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
        
        Scene listsc = new Scene(LIST_LOADER.load());
        listStage.setTitle("List of Attendies!");
        listStage.setScene(listsc);
        listStage.setMinWidth(900);
        listStage.setMinHeight(600);

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
        primaryStage.setMinWidth(1130);
        primaryStage.setMinHeight(700);
        //FOR MAXIMIZED WINDOW SIZE
        // MUST BE PLACE BEFORE THE SHOW COMMAND
        primaryStage.setMaximized(true);

    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static void customResize(TableView<?> view) {
        /*
        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/view.getColumns().size()));
            });
        }
*/
    }

}
