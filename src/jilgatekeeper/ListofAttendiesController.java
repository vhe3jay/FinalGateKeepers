/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jilgatekeeper;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author TestSubject
 */


public class ListofAttendiesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXComboBox sort_attendy;
    @FXML
    private JFXComboBox lifegroup;
    @FXML
    private TableView<AttendyModels> tb;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn lgCol;
    @FXML
    private TableColumn ageCol;
    @FXML
    private TableColumn bdayCol;
    @FXML
    private TableColumn contactCol;
    @FXML
    private TableColumn addressCol;
    @FXML
    private TableColumn timeCol;
    
    ObservableList<String> lifegrouplist = FXCollections.observableArrayList("First Timers", "Guests", "Children","KKB","YAN","MEN", "WOMEN","Seniors");
    ObservableList<String> sort_list = FXCollections.observableArrayList("Today", "Last week", "Custom");
    
    private static final ObservableList<AttendyModels> completedata = FXCollections.observableArrayList(
            new AttendyModels (1,"asdasd", AttendyModels.lgList.YAN, 18, LocalDate.now(), "nbn", "address",Timestamp.valueOf(LocalDateTime.now()))
            
    );
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        lgCol.setCellValueFactory(new PropertyValueFactory("lifegroup"));
        ageCol.setCellValueFactory(new PropertyValueFactory("age"));
        bdayCol.setCellValueFactory(new PropertyValueFactory("dateofbirth"));
        contactCol.setCellValueFactory(new PropertyValueFactory("contactnumber"));
        addressCol.setCellValueFactory(new PropertyValueFactory("address"));
        timeCol.setCellValueFactory(new PropertyValueFactory("timelog"));
        //tb.setItems(data);
        tb.setEditable(true);
        tb.setItems(completedata);
        sortlist();
        lglist();
        //log();
    }
    public static void addAttendyToTable(AttendyModels attendy){
        completedata.add(attendy);
    }
    
    
     private void sortlist(){
        sort_attendy.setItems(sort_list);
    }
     
     private void lglist(){
        lifegroup.getItems().addAll(AttendyModels.lgList.values());

}
}