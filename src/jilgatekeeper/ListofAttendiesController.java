package jilgatekeeper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.nakpilse.sql.SQLTable;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jilgatekeeper.AttendyModels.sortby;

public class ListofAttendiesController implements Initializable {

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
        @FXML
    private StackPane stackPane;
    
    private ObjectProperty<Predicate<AttendyModels>> nameFilter = new SimpleObjectProperty<>();
    private ObjectProperty<Predicate<AttendyModels>> lgFilter = new SimpleObjectProperty<>();
    private FilteredList<AttendyModels> filteredItems = null;
    private List<AttendyModels> sortedDataList = new ArrayList();

    /*
    ObservableList<String> lifegrouplist = FXCollections.observableArrayList("First Timers", "Guests", "Children","KKB","YAN","MEN", "WOMEN","Seniors");
    ObservableList<String> sort_list = FXCollections.observableArrayList("Today", "Last week", "Custom");
    
    private static final ObservableList<AttendyModels> completedata = FXCollections.observableArrayList(
            new AttendyModels (1,"asdasd", AttendyModels.lgList.YAN, 18, LocalDate.now(), "nbn", "address",Timestamp.valueOf(LocalDateTime.now()))
    );
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filteredItems = new FilteredList<>(FXCollections.observableList(JILGateKeeper.createData));
        
        TableColumn nameCol = column("Name", AttendyModels::nameProperty);
        TableColumn lgCol = column("Lifegroup", AttendyModels::lifegroupProperty);
        TableColumn ageCol = column("Age", AttendyModels::ageProperty);
        TableColumn birthCol = column("Birthdate", AttendyModels::dateofbirthProperty);
        TableColumn contactCol = column("Contact No.", AttendyModels::contactnumberProperty);
        TableColumn addressCol = column("Address", AttendyModels::addressProperty);
        TableColumn latestCol = column("Time", AttendyModels::latestLogProperty);

        tb.getColumns().add(nameCol);
        tb.getColumns().add(lgCol);
        tb.getColumns().add(ageCol);
        tb.getColumns().add(birthCol);
        tb.getColumns().add(contactCol);
        tb.getColumns().add(addressCol);
        tb.getColumns().add(latestCol);
        tb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        latestCol.setSortable(true);
        
        sort_attendy.getItems().addAll(AttendyModels.sortby.values());
        lgcombo.getItems().addAll(AttendyModels.lgList.values());

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());

        latestCol.setMinWidth(110);
        addressCol.setMinWidth(130);
        ageCol.setMaxWidth(90);

        nameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<AttendyModels, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AttendyModels, String> t) {
                AttendyModels sel_attendy = (AttendyModels) t.getTableView().getItems().get(t.getTablePosition().getRow());
                sel_attendy.setName(t.getNewValue());
                sel_attendy.update();
                loadAttendies();
            }
        }
        );
        contactCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<AttendyModels, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AttendyModels, String> t) {
                AttendyModels contact = (AttendyModels) t.getTableView().getItems().get(t.getTablePosition().getRow());
                contact.setContactnumber(t.getNewValue());
                contact.update();
                loadAttendies();
            }
        }
        );
        addressCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<AttendyModels, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AttendyModels, String> t) {
                AttendyModels address = (AttendyModels) t.getTableView().getItems().get(t.getTablePosition().getRow());
                address.setAddress(t.getNewValue());
                address.update();
                loadAttendies();
            }
        }
        );
        searchFilter();
    }

    @FXML
    public void searchFilter() {
        nameFilter.bind(Bindings.createObjectBinding(()
                -> person -> person.getName().toLowerCase().contains(searchField.getText().toLowerCase()),
                searchField.textProperty()));

        lgFilter.bind(Bindings.createObjectBinding(()
                -> person -> lgcombo.getValue() == null || lgcombo.getValue().toString().equals(person.getLifegroup()),
                lgcombo.valueProperty()));
        tb.setItems(filteredItems);
        
        clearButton.setOnAction(e -> {
            lgcombo.setValue(null);
            searchField.clear();
            sort_attendy.setValue(sortby.ALL);
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

    public void changeSampleLabel(AttendyModels attendyModels) {
        //createData.add(attendyModels);
        ((MainSceneController) JILGateKeeper.LOADERS.get("MAIN").getController()).refreshTable();
    }
    public void refresh(){
        //((MainSceneController) JILGateKeeper.LOADERS.get("MAIN").getController()).refreshTable();
        
        filteredItems = new FilteredList<>(FXCollections.observableList(sortedDataList));
        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));
        tb.setItems((FilteredList) filteredItems);
    }
    
    public void loadAttendies(){
        sortedDataList = SQLTable.list(AttendyModels.class);
        refresh();
    }
    
    @FXML
    public void querybyDate(ActionEvent event) {
        sortby sel_period = (sortby)sort_attendy.getValue();
        java.sql.Timestamp from = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
        java.sql.Timestamp to = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
        switch (sel_period) {
             case TODAY:
                sortedDataList = SQLTable.list(AttendyModels.class,"latestlog",from,to);
                refresh();
                break;
            case LASTWEEK:
                from = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.of(0, 0, 0)));
                to = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                sortedDataList = SQLTable.list(AttendyModels.class,"latestlog",from,to);
                refresh();
                break;
            case CUSTOM:
                VBox contentBox = new VBox();
                DatePicker customFrom = new DatePicker();
                DatePicker customTo = new DatePicker();
                contentBox.getChildren().addAll(new Label("From"),customFrom,new Label("To"),customTo);
                contentBox.autosize();
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Information"));
                content.setBody(contentBox);
                JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                JFXButton button = new JFXButton("Okay");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        java.sql.Timestamp from1 = java.sql.Timestamp.valueOf(LocalDateTime.of(customFrom.getValue(), LocalTime.of(0, 0, 0)));
                        java.sql.Timestamp to1 = java.sql.Timestamp.valueOf(LocalDateTime.of(customTo.getValue(), LocalTime.of(23, 59, 59)));
                        sortedDataList = SQLTable.list(AttendyModels.class,"latestlog",from1,to1);
                        refresh();
                        dialog.close();
                    }
                });
                content.setActions(button);
                dialog.show();                
                break;
            default:
                sortedDataList = SQLTable.list(AttendyModels.class);
                refresh();
                break;
        }
        
    }

}
