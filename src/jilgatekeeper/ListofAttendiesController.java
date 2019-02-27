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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private JFXComboBox lgcombo;
    @FXML
    public TableView<AttendyModels> tb;
    
    /*
    ObservableList<String> lifegrouplist = FXCollections.observableArrayList("First Timers", "Guests", "Children","KKB","YAN","MEN", "WOMEN","Seniors");
    ObservableList<String> sort_list = FXCollections.observableArrayList("Today", "Last week", "Custom");
    
    private static final ObservableList<AttendyModels> completedata = FXCollections.observableArrayList(
            new AttendyModels (1,"asdasd", AttendyModels.lgList.YAN, 18, LocalDate.now(), "nbn", "address",Timestamp.valueOf(LocalDateTime.now()))
    );
    */
    private static List<AttendyModels> createData = new ArrayList(
    );
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tb.getColumns().add(column("Name", AttendyModels::nameProperty));
        tb.getColumns().add(column("Lifegroup", AttendyModels::lifegroupProperty));
        tb.getColumns().add(column("Age", AttendyModels::ageProperty));
        tb.getColumns().add(column("Birthdate", AttendyModels::dateofbirthProperty));
        tb.getColumns().add(column("Contact No.", AttendyModels::contactnumberProperty));
        tb.getColumns().add(column("Address", AttendyModels::addressProperty));
        tb.getColumns().add(column("Time", AttendyModels::timelogProperty));
        
        /*
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        lgCol.setCellValueFactory(new PropertyValueFactory("lifegroup"));
        ageCol.setCellValueFactory(new PropertyValueFactory("age"));
        bdayCol.setCellValueFactory(new PropertyValueFactory("dateofbirth"));
        contactCol.setCellValueFactory(new PropertyValueFactory("contactnumber"));
        addressCol.setCellValueFactory(new PropertyValueFactory("address"));
        timeCol.setCellValueFactory(new PropertyValueFactory("timelog"));
        */
        //tb.setItems(data);
        //tb.setEditable(true);
        //tb.setItems(completedata);
        sortlist();
        lglist();
        //log();
    }
    
    private static <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setMinWidth(20);
        col.setMaxWidth(800);
        col.setPrefWidth(205);
        return col ;
    }
    
    
     private void sortlist(){
        sort_attendy.getItems().addAll(AttendyModels.sortby.values());
    }
     
     private void lglist(){
        lgcombo.getItems().addAll(AttendyModels.lgList.values());

}
}