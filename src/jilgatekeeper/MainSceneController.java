/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jilgatekeeper;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author TestSubject
 */

public class MainSceneController implements Initializable {

    @FXML
    private JFXComboBox lifegroupcombo ;
    @FXML
    private JFXTextField searchField ;
    @FXML
    private Button searchButton;
    @FXML
    private TableView tb;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn lgCol;
    @FXML
    private TableColumn numberCol;
    @FXML
    private TableColumn timeCol;
    @FXML
    private Button presButton;
    @FXML
    private Button addButton;
    
    @FXML
    ObservableList<String> list = FXCollections.observableArrayList("First Timers", "Guests", "Children","KKB","YAN","MEN", "WOMEN","Seniors");
    
    //DATA BEING INPUT HERE
    private static final ObservableList<AttendyModels> data = FXCollections.observableArrayList(
            new AttendyModels ("bobby","YAN","09567255912",Timestamp.valueOf(LocalDateTime.now())),
            new AttendyModels ("mhar","YAN","09366948787",Timestamp.valueOf(LocalDateTime.now())),
            new AttendyModels (1,"asdasd", "YAN", 18, LocalDate.now(), "nbn", "address",Timestamp.valueOf(LocalDateTime.now()))
    );
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            nameCol.setCellValueFactory(new PropertyValueFactory("name"));
            lgCol.setCellValueFactory(new PropertyValueFactory("lifegroup"));
            numberCol.setCellValueFactory(new PropertyValueFactory("contactnumber"));
            timeCol.setCellValueFactory(new PropertyValueFactory("timelog"));
            // TODO
            loadcomponent();
            tb.setItems(data);
        }catch(Exception er){
            //FRO CHECKING THE ERROR
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, er);
        }
        
    }    
    
    public static void addAttendyToTable(AttendyModels attendy){
        data.add(attendy);
    }
    
    public void adddata(){
        /*
      
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                data.add(new AttendyModels(
                        //ADDING DATA 
                        nameCol.getText(),
                        (String) lgbox.getValue(),
                        numberCol.getText(),
                        timeCol.toString()
       }
        });
        */
        
   }
    
    private void loadcomponent(){
        //lifegroup.setValue("First Timers");
        lifegroupcombo.setItems(list);
    }
    
       
    @FXML
    public void showAttendyForm(ActionEvent evt){
        JILGateKeeper.showAttendyForm();
    }
    
    @FXML
    public void showAttendyListForm(ActionEvent evt){
        JILGateKeeper.showAttendyListForm();
    }
    
    @FXML
    public void lgfilter(ActionEvent evt){
          FilteredList<String> filteredItems = new FilteredList(list, p -> true);

        // Add a listener to the textProperty of the combobox editor. The
        // listener will simply filter the list every time the input is changed
        // as long as the user hasn't selected an item in the list.
        lifegroupcombo.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = lifegroupcombo.getEditor();
            final String selected = (String) lifegroupcombo.getSelectionModel().getSelectedItem();

            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        return item.toUpperCase().startsWith(newValue.toUpperCase());
                    });
                }
            });
        });
     }
 
}


