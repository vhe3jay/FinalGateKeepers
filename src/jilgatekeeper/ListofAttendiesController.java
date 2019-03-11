package jilgatekeeper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jilgatekeeper.Attendy.sortby;

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
    public TableView<Attendy> tb;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXCheckBox cBox;
    
    private ObjectProperty<Predicate<Attendy>> nameFilter = new SimpleObjectProperty<>();
    private ObjectProperty<Predicate<Attendy>> lgFilter = new SimpleObjectProperty<>();
    private FilteredList<Attendy> filteredItems = null;
    
    private List<Attendy> AttendyList = new ArrayList();
    Attendy atndy = new Attendy();

    /*
    ObservableList<String> lifegrouplist = FXCollections.observableArrayList("First Timers", "Guests", "Children","KKB","YAN","MEN", "WOMEN","Seniors");
    ObservableList<String> sort_list = FXCollections.observableArrayList("Today", "Last week", "Custom");
    
    private static final ObservableList<AttendyModels> completedata = FXCollections.observableArrayList(
            new Attendy (1,"asdasd", Attendy.lgList.YAN, 18, LocalDate.now(), "nbn", "address",Timestamp.valueOf(LocalDateTime.now()))
    );
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filteredItems = new FilteredList<>(FXCollections.observableList(JILGateKeeper.createData));
        
        TableColumn nameCol = column("Name", Attendy::nameProperty);
        TableColumn lgCol = column("Lifegroup", Attendy::lifegroupProperty);
        TableColumn ageCol = column("Age", Attendy::ageProperty);
        TableColumn birthCol = column("Birthdate", Attendy::dateofbirthProperty);
        TableColumn contactCol = column("Contact No.", Attendy::contactnumberProperty);
        TableColumn addressCol = column("Address", Attendy::addressProperty);
        TableColumn timelogCol = column("First Time Attended", Attendy::timelogProperty);
        TableColumn latestCol = column("Latest Time Attended", Attendy::latestLogProperty);

        tb.getColumns().add(nameCol);
        tb.getColumns().add(lgCol);
        tb.getColumns().add(ageCol);
        tb.getColumns().add(birthCol);
        tb.getColumns().add(contactCol);
        tb.getColumns().add(addressCol);
        tb.getColumns().add(timelogCol);
        tb.getColumns().add(latestCol);
        tb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        latestCol.setSortType(SortType.DESCENDING);
        latestCol.setSortable(true);
        timelogCol.setMinWidth(90);
        ageCol.setMinWidth(25);
        ageCol.setStyle("-fx-alignment: CENTER;");
        birthCol.setStyle("-fx-alignment: CENTER;");
        
        sort_attendy.getItems().addAll(Attendy.sortby.values());
        lgcombo.getItems().addAll(Attendy.lgList.values());

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());

        latestCol.setMinWidth(110);
        addressCol.setMinWidth(130);
        ageCol.setMaxWidth(90);

        nameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Attendy, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Attendy, String> t) {
                Attendy sel_attendy = (Attendy) t.getTableView().getItems().get(t.getTablePosition().getRow());
                sel_attendy.setName(t.getNewValue());
                sel_attendy.update();
                loadAttendies();
            }
        }
        );
        contactCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Attendy, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Attendy, String> t) {
                Attendy contact = (Attendy) t.getTableView().getItems().get(t.getTablePosition().getRow());
                contact.setContactnumber(t.getNewValue());
                contact.update();
                loadAttendies();
            }
        }
        );
        addressCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Attendy, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Attendy, String> t) {
                Attendy address = (Attendy) t.getTableView().getItems().get(t.getTablePosition().getRow());
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
            sort_attendy.setValue(sortby.ALL);
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

    public void changeSampleLabel(Attendy attendyModels) {
        //createData.add(attendyModels);
        ((MainSceneController) JILGateKeeper.LOADERS.get("MAIN").getController()).refreshTable();
    }
    public void refresh(){
        //((MainSceneController) JILGateKeeper.LOADERS.get("MAIN").getController()).refreshTable();
        
        filteredItems = new FilteredList<>(FXCollections.observableList(AttendyList));
        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));
        SortedList<Attendy> sortedlist = new SortedList<>(filteredItems);
        tb.setItems(sortedlist);
        sortedlist.comparatorProperty().bind(tb.comparatorProperty());
    }
    
    public void loadAttendies(){
        AttendyList = SQLTable.list(Attendy.class);
        refresh();
    }
    java.sql.Timestamp from = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
    java.sql.Timestamp to = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
    
    @FXML
    public void querybyDate(ActionEvent event) {
        sortby sel_period = (sortby)sort_attendy.getValue();
        switch (sel_period) {
             case TODAY:
                from = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                to = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                AttendyList = SQLTable.list(Attendy.class,"latestlog",from,to);
                refresh();
                break;
            case LASTWEEK:
                from = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.of(0, 0, 0)));
                to = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                AttendyList = SQLTable.list(Attendy.class,"latestlog",from,to);
                refresh();
                break;
            case CUSTOM:
                VBox contentBox = new VBox();
                DatePicker customFrom = new DatePicker();
                DatePicker customTo = new DatePicker();
                contentBox.getChildren().addAll(new Label("From"),customFrom,new Label("To"),customTo);
                contentBox.autosize();
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Custom"));
                content.setBody(contentBox);
                JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                JFXButton button = new JFXButton("Okay");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        from = java.sql.Timestamp.valueOf(LocalDateTime.of(customFrom.getValue(), LocalTime.of(0, 0, 0)));
                        to = java.sql.Timestamp.valueOf(LocalDateTime.of(customTo.getValue(), LocalTime.of(23, 59, 59)));
                        AttendyList = SQLTable.list(Attendy.class,"latestlog",from,to);
                        refresh();
                        dialog.close();
                    }
                });
                content.setActions(button);
                dialog.show();                
                break;
            default:
                AttendyList = SQLTable.list(Attendy.class);
                refresh();
                break;
        }
        
    }
    
    public void forFirstTimers(){
        if(cBox.isSelected()){
            List<Attendy> firsttimers = new ArrayList();
            for(Attendy attendy:AttendyList){
                if((attendy.getTimelog().after(from) || attendy.getTimelog().equals(from)) && (attendy.getTimelog().before(to) || attendy.getTimelog().equals(to))){
                    firsttimers.add(attendy);
                }
            }
            filteredItems = new FilteredList<>(FXCollections.observableList(firsttimers));
            filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));
            SortedList<Attendy> sortedlist = new SortedList<>(filteredItems);
            tb.setItems(sortedlist);
            sortedlist.comparatorProperty().bind(tb.comparatorProperty());
        }else{
            refresh();
        }
    }

}
