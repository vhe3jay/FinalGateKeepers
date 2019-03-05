/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jilgatekeeper;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import com.nakpilse.sql.SQLTable;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import static jilgatekeeper.JILGateKeeper.listStage;

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
    private Button deleteButton;
    @FXML
    private Label countLabel;

    public static Stage newStage = new Stage();
    private List dataList = JILGateKeeper.createData;

    private ObjectProperty<Predicate<AttendyModels>> nameFilter = new SimpleObjectProperty<>();
    private ObjectProperty<Predicate<AttendyModels>> lgFilter = new SimpleObjectProperty<>();
    //public List<AttendyModels> createData = new ArrayList();

    private FilteredList<AttendyModels> filteredItems = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JILGateKeeper.createData = SQLTable.list(AttendyModels.class);
        for(AttendyModels model:JILGateKeeper.createData){
            System.out.println(model.getDebugInfo());
        }
        filteredItems = new FilteredList<>(FXCollections.observableList(JILGateKeeper.createData));
        //SETTING THE COLUMN EDITABLE
        tb.setEditable(true);
        TableColumn nameCol = column("Name", AttendyModels::nameProperty);
        TableColumn lgCol = column("Lifegroup", AttendyModels::lifegroupProperty);
        TableColumn contactCol = column("Contact Number", AttendyModels::contactnumberProperty);
        TableColumn timeCol = column("Timelogs", AttendyModels::timelogProperty);

        tb.setEditable(true);
        tb.getColumns().add(nameCol);
        tb.getColumns().add(lgCol);
        tb.getColumns().add(contactCol);
        tb.getColumns().add(timeCol);
        tb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //lgCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactCol.setCellFactory(TextFieldTableCell.forTableColumn());

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

        searchFilter();
        addButtonToTable();
    }

    @FXML
    public void searchFilter() {
        lgComboBox.getItems().addAll(AttendyModels.lgList.values());

        nameFilter.bind(Bindings.createObjectBinding(()
                -> person -> person.getName().toLowerCase().contains(searchField.getText().toLowerCase()),
                searchField.textProperty()));

        lgFilter.bind(Bindings.createObjectBinding(()
                -> person -> lgComboBox.getValue() == null || lgComboBox.getValue().toString().equals(person.getLifegroup()),
                lgComboBox.valueProperty()));

        //filteredItems = new FilteredList<>(FXCollections.observableList(createData));
        tb.setItems(filteredItems);

        clearButton.setOnAction(e -> {
            lgComboBox.setValue(null);
            searchField.clear();
        });

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));

    }

    private void refreshTable() {
        JILGateKeeper.createData = SQLTable.list(AttendyModels.class);
        filteredItems = new FilteredList<>(FXCollections.observableList(JILGateKeeper.createData));
        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));
        tb.setItems((FilteredList) filteredItems);
        countLabel.setText(String.valueOf(dataList.size()));
    }

    private static <S, T> TableColumn<S, T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S, T> col = new TableColumn<>(title);

        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setEditable(true);
        col.setMinWidth(20);
        col.setMaxWidth(800);
        col.setPrefWidth(351.1);
        return col;
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
        listStage.show();

        /*
        try {
            FXMLLoader LISTFORM_LOADER = new FXMLLoader(this.getClass().getResource("ListofAttendies.fxml"));
            Scene listsc = new Scene(LISTFORM_LOADER.load());
            JILGateKeeper.LOADERS.put("LIST", LISTFORM_LOADER);
            //JILGateKeeper.setListController(LISTFORM_LOADER.getController());
            listStage.setTitle("List of Attendies!");
            listStage.setScene(listsc);
            listStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    void changeSampleLabel(AttendyModels attendyModels) {
        //System.out.println(attendyModels.toString());
        //JILGateKeeper.createData.add(attendyModels);
        refreshTable();
    }

    @FXML
    private void deleteButton(ActionEvent evt) {
        AttendyModels sel_item = (AttendyModels) tb.getSelectionModel().getSelectedItem();
        JILGateKeeper.createData.remove(sel_item);
        refreshTable();
    }

    private void addButtonToTable() {
        TableColumn<AttendyModels, Void> colBtn = new TableColumn("Time In");
        colBtn.setMinWidth(20);
        colBtn.setMaxWidth(200);

        Callback<TableColumn<AttendyModels, Void>, TableCell<AttendyModels, Void>> cellFactory = new Callback<TableColumn<AttendyModels, Void>, TableCell<AttendyModels, Void>>() {
            @Override
            public TableCell<AttendyModels, Void> call(final TableColumn<AttendyModels, Void> param) {
                final TableCell<AttendyModels, Void> cell = new TableCell<AttendyModels, Void>() {

                    private final Button btn = new Button("Log In");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            AttendyModels data = getTableView().getItems().get(getIndex());                            
                            data.setLatestLog(Timestamp.valueOf(LocalDateTime.now()));
                            data.update();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);

        tb.getColumns().add(colBtn);
    }

}
