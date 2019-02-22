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
import javafx.beans.Observable;
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
            //searchFilter();
            tb.setItems(data);
        }catch(Exception er){
            //FRO CHECKING THE ERROR
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, er);
        }
    }    
    
    public static void addAttendyToTable(AttendyModels attendy){
        data.add(attendy);
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
    public void searchFilter(){
	if(searchField.textProperty().get().isEmpty()){
		tb.setItems(data);
		return;
	}
	ObservableList<AttendyModels> tableItems = FXCollections.observableArrayList();
	ObservableList<TableColumn<AttendyModels, ?>> cols = tb.getColumns();
	for(int i=0; i<data.size(); i++){
		for(int j = 0; j<data.size();j++){
			TableColumn col = cols.get(j);
			String cellValue = col.getCellData(data.get(i)).toString();
			cellValue = cellValue.toLowerCase();
			if(cellValue.contains(searchField.textProperty().get().toLowerCase())){
				tableItems.add(data.get(i));
				break;
			}
		}
	}
	tb.setItems(tableItems);
}
 
}


