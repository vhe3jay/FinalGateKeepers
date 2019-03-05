/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jilgatekeeper;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

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
    private Button clearButton;
    @FXML
    private JFXTextField searchField;
    @FXML
    public TableView<AttendyModels> tb;

    private ObjectProperty<Predicate<AttendyModels>> nameFilter = new SimpleObjectProperty<>();
    private ObjectProperty<Predicate<AttendyModels>> lgFilter = new SimpleObjectProperty<>();
    private FilteredList<AttendyModels> filteredItems = new FilteredList<>(FXCollections.observableList(JILGateKeeper.createData));

    /*
    ObservableList<String> lifegrouplist = FXCollections.observableArrayList("First Timers", "Guests", "Children","KKB","YAN","MEN", "WOMEN","Seniors");
    ObservableList<String> sort_list = FXCollections.observableArrayList("Today", "Last week", "Custom");
    
    private static final ObservableList<AttendyModels> completedata = FXCollections.observableArrayList(
            new AttendyModels (1,"asdasd", AttendyModels.lgList.YAN, 18, LocalDate.now(), "nbn", "address",Timestamp.valueOf(LocalDateTime.now()))
    );
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn nameCol = column("Name", AttendyModels::nameProperty);
        TableColumn lgCol = column("Lifegroup", AttendyModels::lifegroupProperty);
        TableColumn ageCol = column("Age", AttendyModels::ageProperty);
        TableColumn birthCol = column("Birthdate", AttendyModels::dateofbirthProperty);
        TableColumn contactCol = column("Contact No.", AttendyModels::contactnumberProperty);
        TableColumn addressCol = column("Address", AttendyModels::addressProperty);
        TableColumn timeCol = column("Time", AttendyModels::timelogProperty);

        tb.getColumns().add(nameCol);
        tb.getColumns().add(lgCol);
        tb.getColumns().add(ageCol);
        tb.getColumns().add(birthCol);
        tb.getColumns().add(contactCol);
        tb.getColumns().add(addressCol);
        tb.getColumns().add(timeCol);

        tb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        sort_attendy.getItems().addAll(AttendyModels.sortby.values());
        lgcombo.getItems().addAll(AttendyModels.lgList.values());

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());

        timeCol.setMinWidth(110);
        addressCol.setMinWidth(130);
        ageCol.setMaxWidth(90);

        nameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<AttendyModels, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AttendyModels, String> t) {
                AttendyModels sel_attendy = (AttendyModels) t.getTableView().getItems().get(t.getTablePosition().getRow());
                sel_attendy.setName(t.getNewValue());
                refreshTable();
            }
        }
        );
        contactCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<AttendyModels, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AttendyModels, String> t) {
                AttendyModels contact = (AttendyModels) t.getTableView().getItems().get(t.getTablePosition().getRow());
                contact.setContactnumber(t.getNewValue());
                refreshTable();
            }
        }
        );
        addressCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<AttendyModels, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AttendyModels, String> t) {
                AttendyModels address = (AttendyModels) t.getTableView().getItems().get(t.getTablePosition().getRow());
                address.setAddress(t.getNewValue());
                refreshTable();
            }
        }
        );
        searchFilter();

    }

    @FXML
    public void searchFilter() {
        lgcombo.getItems().addAll(AttendyModels.lgList.values());

        nameFilter.bind(Bindings.createObjectBinding(()
                -> person -> person.getName().toLowerCase().contains(searchField.getText().toLowerCase()),
                searchField.textProperty()));

        lgFilter.bind(Bindings.createObjectBinding(()
                -> person -> lgcombo.getValue() == null || lgcombo.getValue() == person.getLifegroup(),
                lgcombo.valueProperty()));

        //filteredItems = new FilteredList<>(FXCollections.observableList(createData));
        tb.setItems(filteredItems);

        clearButton.setOnAction(e -> {
            lgcombo.setValue(null);
            searchField.clear();
        });
        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));
    }

    private static <S, T> TableColumn<S, T> column(String title, Function<S, ObservableValue<T>> property//,boolean editable,
    //StringConverter<T> converter
    ) {
        TableColumn<S, T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setMinWidth(20);
        col.setMaxWidth(800);
        col.setPrefWidth(205);
        col.resizableProperty();
        return col;
    }

    private void refreshTable() {
        tb.setItems(FXCollections.observableList(JILGateKeeper.createData));
    }

    public void changeSampleLabel(AttendyModels attendyModels) {
        //System.out.println(attendyModels.toString());
        //createData.add(attendyModels);
        refreshTable();
    }

}
