/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jilgatekeeper;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MainSceneController implements Initializable {

    @FXML
    private JFXComboBox<AttendyModels.lgList> lgComboBox = new JFXComboBox();
    @FXML
    private JFXTextField searchField;
    @FXML
    private Button clearButton;
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
    
       private ObjectProperty<Predicate<AttendyModels>> nameFilter = new SimpleObjectProperty<>();
       private ObjectProperty<Predicate<AttendyModels>> genderFilter = new SimpleObjectProperty<>();
       private FilteredList<AttendyModels> filteredItems = new FilteredList<>(FXCollections.observableList(createData));

    private static List<AttendyModels> createData = new ArrayList(
    ); 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tb.getColumns().add(column("Name", AttendyModels::nameProperty));
        tb.getColumns().add(column("Lifegroup", AttendyModels::lifegroupProperty));
        tb.getColumns().add(column("Contact Number", AttendyModels::contactnumberProperty));
        tb.getColumns().add(column("Timelogs", AttendyModels::timelogProperty));
        searchFilter();
    }

    @FXML
    public void searchFilter() {
        lgComboBox.getItems().addAll(AttendyModels.lgList.values());
        
        nameFilter.bind(Bindings.createObjectBinding(() -> 
            person -> person.getName().toLowerCase().contains(searchField.getText().toLowerCase()), 
            searchField.textProperty()));


        genderFilter.bind(Bindings.createObjectBinding(() ->
            person -> lgComboBox.getValue() == null || lgComboBox.getValue() == person.getLifegroup(), 
            lgComboBox.valueProperty()));

        //filteredItems = new FilteredList<>(FXCollections.observableList(createData));
        tb.setItems(filteredItems);
        
        clearButton.setOnAction(e -> {
            lgComboBox.setValue(null);
            searchField.clear();
        });

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(genderFilter.get()), nameFilter, genderFilter));
        
    }    
    
    private void refreshTable(){
        filteredItems = new FilteredList<>(FXCollections.observableList(createData));
        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(genderFilter.get()), nameFilter, genderFilter));
        tb.setItems((FilteredList)filteredItems);
    }
    
    private static <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        return col ;
    }
      @FXML
    public void launchCompanyForm(ActionEvent event) {
        try {
            FXMLLoader COMPANYFORM_LOADER = new FXMLLoader(this.getClass().getResource("NewAttendyForm.fxml"));
            Scene mainsc = new Scene(COMPANYFORM_LOADER.load());
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Add New Attendy!");
            primaryStage.setScene(mainsc);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      @FXML
    public void launchAttendyListForm(ActionEvent event) {
        try {
            FXMLLoader COMPANYFORM_LOADER = new FXMLLoader(this.getClass().getResource("ListofAttendies.fxml"));
            Scene mainsc = new Scene(COMPANYFORM_LOADER.load());
            Stage primaryStage = new Stage();
            primaryStage.setTitle("List of Attendies!");
            primaryStage.setScene(mainsc);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void changeSampleLabel(String text, AttendyModels attendyModels) {
        System.out.println(attendyModels.toString());
        createData.add(attendyModels);
        refreshTable();
    }


}
