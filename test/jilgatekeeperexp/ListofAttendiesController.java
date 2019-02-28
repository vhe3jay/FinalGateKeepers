/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jilgatekeeperexp;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    
    
    private ObjectProperty<Predicate<AttendyModels>> nameFilter = new SimpleObjectProperty<>();
    private ObjectProperty<Predicate<AttendyModels>> lgFilter = new SimpleObjectProperty<>();
       
    private List<AttendyModels> createData = new ArrayList();
    private FilteredList<AttendyModels> filteredItems = new FilteredList<>(FXCollections.observableList(createData));
    
    /*
    ObservableList<String> lifegrouplist = FXCollections.observableArrayList("First Timers", "Guests", "Children","KKB","YAN","MEN", "WOMEN","Seniors");
    ObservableList<String> sort_list = FXCollections.observableArrayList("Today", "Last week", "Custom");
    
    private static final ObservableList<AttendyModels> completedata = FXCollections.observableArrayList(
            new AttendyModels (1,"asdasd", AttendyModels.lgList.YAN, 18, LocalDate.now(), "nbn", "address",Timestamp.valueOf(LocalDateTime.now()))
    );
    */
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        tb.getColumns().add(column("Name", AttendyModels::nameProperty));
        tb.getColumns().add(column("Lifegroup", AttendyModels::lifegroupProperty));
        tb.getColumns().add(column("Age", AttendyModels::ageProperty));
        tb.getColumns().add(column("Birthdate", AttendyModels::dateofbirthProperty));
        tb.getColumns().add(column("Contact No.", AttendyModels::contactnumberProperty));
        tb.getColumns().add(column("Address", AttendyModels::addressProperty));
        tb.getColumns().add(column("Time", AttendyModels::timelogProperty));
        sort_attendy.getItems().addAll(AttendyModels.sortby.values());
        lgcombo.getItems().addAll(AttendyModels.lgList.values());
        
    }
    
    private static <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setMinWidth(20);
        col.setMaxWidth(800);
        col.setPrefWidth(205);
        col.resizableProperty();
        return col ;
    }
    
    private void refreshTable(){
        //filteredItems = new FilteredList<>(FXCollections.observableList(createData));
        //filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));
        tb.setItems(FXCollections.observableList(createData));
    }
        
    public void changeSampleLabel(AttendyModels attendyModels) {
        //System.out.println(attendyModels.toString());
        //createData.add(attendyModels);
        refreshTable();
    }
    
    public void textsample(){
        System.out.println("print this to test");
    }
    
}