package forms;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LayoutDemoVBox extends Application {

   public static void main(String[] args) {
      // ...
   }

   public void start(Stage stage) throws Exception {

      Scene scene = new Scene(createVBoxLayout(), 250, 200);
      // ...
   }

   public VBox createVBoxLayout() {
      VBox vbox = new VBox();

      vbox.setSpacing(10);
      vbox.setPadding(new Insets(5));
      vbox.setAlignment(Pos.CENTER_LEFT);
      
       Label userLabel=new Label("User Name ");
      Label passLabel=new Label("Password ");
      TextField userTextField=new TextField();
      PasswordField passwordField=new PasswordField();
      Button loginButton=new Button("Login");

      // ...

      vbox.getChildren().addAll(userLabel,userTextField,
         passLabel,passwordField,loginButton);


      return vbox;
   }
}