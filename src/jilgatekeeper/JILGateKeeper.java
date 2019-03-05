package jilgatekeeper;

import com.nakpilse.sql.SQLConnectionFactory;
import com.nakpilse.sql.SQLConnectionPool;
import com.nakpilse.sql.SQLServer;
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
    public static Stage listStage = new Stage();
    public static final SQLConnectionPool CONNECTION_POOL = new SQLConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/jilgatekeepers", "root", "dwr2rufd7ezj", 50);

    public static ListofAttendiesController getListController() {
        return ListController;
    }

    public static void setListController(ListofAttendiesController ListController) {
        JILGateKeeper.ListController = ListController;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        SQLConnectionFactory.setConnectionPool(CONNECTION_POOL);
        SQLConnectionFactory.setServerType(SQLServer.MYSQL);
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
        primaryStage.setMaximized(true);
        
        
    }

    public static void main(String[] args) {
        launch(args);
    }

}
