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
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class JILGateKeeper extends Application {

    public static Map<String, FXMLLoader> LOADERS = new HashMap();
    public static List<Attendy> createData = new ArrayList();
    public static ListofAttendiesController ListController = null;
    public static final SQLConnectionPool CONNECTION_POOL = new SQLConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/jilgatekeepers", "root", "dwr2rufd7ezj", 50);
    public static Stage listStage = new Stage();
    Scene listsc;
    public static Stage loginStage = null;
    public static Scene loginsc;
    public static ListofAttendiesController getListController() {
        return ListController;
    }

    public static void setListController(ListofAttendiesController ListController) {
        JILGateKeeper.ListController = ListController;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        loginStage = primaryStage;
        //FOR SQL CONNECTION 
        //NOTE: MUST CONNECT FIRST BEFORE LOAD OF FORMS
        SQLConnectionFactory.setConnectionPool(CONNECTION_POOL);
        SQLConnectionFactory.setServerType(SQLServer.MYSQL);
        
        //LOADING OF FORMS
        FXMLLoader MAIN_LOADER = new FXMLLoader(this.getClass().getResource("MainScene.fxml"));
        FXMLLoader LIST_LOADER = new FXMLLoader(this.getClass().getResource("ListofAttendies.fxml"));
        FXMLLoader NEW_LOADER = new FXMLLoader(this.getClass().getResource("NewAttendyForm.fxml"));
        FXMLLoader LOGIN_LOADER = new FXMLLoader(this.getClass().getResource("LoginForm.fxml"));
        FXMLLoader NEWUSER_LOADER = new FXMLLoader(this.getClass().getResource("NewUserForm.fxml"));
        
        LOADERS.put("MAIN", MAIN_LOADER);
        LOADERS.put("LIST", LIST_LOADER);
        LOADERS.put("NEW", NEW_LOADER);
        LOADERS.put("LOGIN", LOGIN_LOADER);
        LOADERS.put("NEWUSER", NEWUSER_LOADER);
        
        listsc = new Scene(LIST_LOADER.load());
        listStage.setTitle("List of Attendies!");
        listStage.setScene(listsc);
        listStage.setMinWidth(900);
        listStage.setMinHeight(600);

        //VBox MAIN_ROOT = LOGIN_LOADER.load();
         loginsc = new Scene(LOGIN_LOADER.load());
        primaryStage.setTitle("Jesus Is Lord Noveleta");    
        primaryStage.setScene(loginsc);
        primaryStage.show();
        
        
    }

    public static void main(String[] args) {
        launch(args);
    }

}
