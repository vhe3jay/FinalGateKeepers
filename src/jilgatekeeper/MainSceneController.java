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
    
    public static Stage newStage = new Stage();
    public static Stage listStage = new Stage();
    
    
       private ObjectProperty<Predicate<AttendyModels>> nameFilter = new SimpleObjectProperty<>();
       private ObjectProperty<Predicate<AttendyModels>> lgFilter = new SimpleObjectProperty<>();
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


        lgFilter.bind(Bindings.createObjectBinding(() ->
            person -> lgComboBox.getValue() == null || lgComboBox.getValue() == person.getLifegroup(), 
            lgComboBox.valueProperty()));

        //filteredItems = new FilteredList<>(FXCollections.observableList(createData));
        tb.setItems(filteredItems);
        
        clearButton.setOnAction(e -> {
            lgComboBox.setValue(null);
            searchField.clear();
        });

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));
        
    }    
    
    private void refreshTable(){
        filteredItems = new FilteredList<>(FXCollections.observableList(createData));
        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));
        tb.setItems((FilteredList)filteredItems);
    }
    
    private static <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setMinWidth(20);
        col.setMaxWidth(800);
        col.setPrefWidth(351.1);
        return col ;
    }
      @FXML
    public void launchnewForm(ActionEvent event) {
        try {
            FXMLLoader COMPANYFORM_LOADER = new FXMLLoader(this.getClass().getResource("NewAttendyForm.fxml"));
            Scene mainsc = new Scene(COMPANYFORM_LOADER.load());
            newStage.setTitle("Add New Attendy!");
            newStage.setScene(mainsc);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      @FXML
    public void launchAttendyListForm(ActionEvent event) {
        try {
            FXMLLoader LISTFORM_LOADER = new FXMLLoader(this.getClass().getResource("ListofAttendies.fxml"));
            Scene listsc = new Scene(LISTFORM_LOADER.load());
            listStage.setTitle("Add New Attendy!");
            listStage.setScene(listsc);
            listStage.show();
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
