package forms;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LayoutDemoHBox extends Application {

   public static void main(String[] args) {
      Application.launch(args);
   }

   @Override
   public void start(Stage stage) throws Exception {

      Scene scene = new Scene(createHBoxLayout(), 650, 100);
      stage.setTitle("Layout Demo");
      stage.setScene(scene);
      stage.show();
   }

   public HBox createHBoxLayout() {
      HBox hbox = new HBox();

      hbox.setSpacing(10);
      hbox.setPadding(new Insets(5));
      hbox.setAlignment(Pos.CENTER_LEFT);

      Label userLabel=new Label("User Name ");
      Label passLabel=new Label("Password ");
      TextField userTextField=new TextField();
      PasswordField passwordField=new PasswordField();
      Button loginButton=new Button("Login");

      hbox.getChildren().addAll(userLabel,userTextField,
         passLabel,passwordField,loginButton);

      return hbox;
   }
   
}