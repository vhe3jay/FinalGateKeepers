package jilgatekeeper;

import static O.QN.selection;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.nakpilse.sql.SQLTable;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import static jilgatekeeper.JILGateKeeper.listStage;
import static jilgatekeeper.JILGateKeeper.loginStage;
import static jilgatekeeper.JILGateKeeper.mainStage;

public class MainSceneController implements Initializable {
    public static User user = new User();

    public static  User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MainSceneController.user = user;
    }
    
    @FXML
    private JFXComboBox<Attendy.lgList> lgComboBox;
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
    @FXML
    private Label dateLabel;
    @FXML
    static ImageView jilImage;
    @FXML
    private StackPane stackPane;
    @FXML
    public Button adduserButton;

    public static Stage newStage = new Stage();
    Attendy atndy = new Attendy();

    private ObjectProperty<Predicate<Attendy>> nameFilter = new SimpleObjectProperty<>();
    private ObjectProperty<Predicate<Attendy>> lgFilter = new SimpleObjectProperty<>();
    private FilteredList<Attendy> filteredItems = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//            jilImage.setFitHeight(100);
//            jilImage.fitWidthProperty().bind(LoginFormController.mainStage.getScene().widthProperty());
        filteredItems = new FilteredList<>(FXCollections.observableList(JILGateKeeper.createData));
        editcolum();
        searchFilter();
        addButtonToTable();
        refreshTable();
    }
    
    //SETTING THE COLUMN EDITABLE
    public void editcolum(){
        tb.setEditable(true);
        TableColumn nameCol = column("Name", Attendy::nameProperty);
        TableColumn lgCol = column("Lifegroup", Attendy::lifegroupProperty);
        TableColumn contactCol = column("Contact Number", Attendy::contactnumberProperty);
        TableColumn latestCol = column("Latest Log", Attendy::latestLogProperty);

        tb.setEditable(true);
        tb.getColumns().add(nameCol);
        tb.getColumns().add(lgCol);
        tb.getColumns().add(contactCol);
        tb.getColumns().add(latestCol);
        tb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        latestCol.setSortType(TableColumn.SortType.DESCENDING);
        
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lgCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Attendy, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Attendy, String> t) {
                Attendy sel_attendy = (Attendy) t.getTableView().getItems().get(t.getTablePosition().getRow());
                sel_attendy.setName(t.getNewValue());
                sel_attendy.update();
                refreshTable();
            }
        }
        );
        lgCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Attendy, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Attendy, String> t) {
                Attendy lg = (Attendy) t.getTableView().getItems().get(t.getTablePosition().getRow());
                lg.setLifegroup(t.getNewValue());
                lg.update();
                refreshTable();
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
                refreshTable();
            }
        }
        );
    }

    @FXML
    public void searchFilter() {
        lgComboBox.getItems().addAll(Attendy.lgList.values());

        nameFilter.bind(Bindings.createObjectBinding(()
                -> person -> person.getName().toLowerCase().contains(searchField.getText().toLowerCase()),
                searchField.textProperty()));

        lgFilter.bind(Bindings.createObjectBinding(()
                -> person -> lgComboBox.getValue() == null || lgComboBox.getValue().toString().equals(person.getLifegroup()),
                lgComboBox.valueProperty()));
        tb.setItems(filteredItems);

        clearButton.setOnAction(e -> {
            lgComboBox.setValue(null);
            searchField.clear();
        });

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));

    }

    public void refreshTable() {
        JILGateKeeper.createData = SQLTable.list(Attendy.class);
        filteredItems = new FilteredList<>(FXCollections.observableList(JILGateKeeper.createData));
        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().and(lgFilter.get()), nameFilter, lgFilter));
        SortedList<Attendy> sortedlist = new SortedList<>(filteredItems);
        tb.setItems(sortedlist);
        sortedlist.comparatorProperty().bind(tb.comparatorProperty());
        countLabel.setText(String.valueOf(JILGateKeeper.createData.size()));
        dateLabel.setText(String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, YYYY"))));
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
            Scene newsc = new Scene(COMPANYFORM_LOADER.load());
            newStage.setTitle("Add New Attendy!");
            newStage.setScene(newsc);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void launchAttendyListForm(ActionEvent event) {
        ((ListofAttendiesController) JILGateKeeper.LOADERS.get("LIST").getController()).loadAttendies();
        
        listStage.show();
    }

    void changeSampleLabel(Attendy attendyModels) {
        refreshTable();
    }

    @FXML
    private void deleteButton(ActionEvent evt) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this " + selection + "?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        Attendy sel_item = (Attendy) tb.getSelectionModel().getSelectedItem();
                        JILGateKeeper.createData.remove(sel_item);
                        sel_item.delete();
                        refreshTable();
                    }
                }              

    private void addButtonToTable() {
        TableColumn<Attendy, Void> colBtn = new TableColumn("Time In");
        colBtn.setMinWidth(20);
        colBtn.setMaxWidth(200);

        Callback<TableColumn<Attendy, Void>, TableCell<Attendy, Void>> cellFactory = new Callback<TableColumn<Attendy, Void>, TableCell<Attendy, Void>>() {
            @Override
            public TableCell<Attendy, Void> call(final TableColumn<Attendy, Void> param) {
                final TableCell<Attendy, Void> cell = new TableCell<Attendy, Void>() {

                    private final Button btn = new Button("Log In");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Attendy data = getTableView().getItems().get(getIndex());     
                            java.sql.Timestamp d = Timestamp.valueOf(LocalDateTime.now());
                            data.setLatestLog(d);
                            data.update();
                            //System.out.println(data.getDebugInfo());
                            timelogsheet timelog = new timelogsheet();
                            timelog.setAttendy_id(data.getId());
                            timelog.setTimelog(d);
                            timelog.save();
                            btn.disableProperty().setValue(true);
                        //btn.setStyle("-fx-background-color: Red");
                        
                            refreshTable();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Attendy data = getTableView().getItems().get(getIndex());
                            boolean hasLog = false;
                            if(data.getLatestLog() != null){
                                if(data.getLatestLog().toLocalDateTime().toLocalDate().equals(LocalDate.now())){
                                    hasLog = true;
                                }
                            }                            
                            setGraphic(((hasLog)? null:btn));
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);

        tb.getColumns().add(colBtn);
    }
    
    @FXML
    void launchNewUserForm(ActionEvent event){
        NewUserFormController.showForm();
    }
    
    public void showmainForm(User user){
        Screen screen = Screen.getPrimary();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Rectangle2D bounds = screen.getVisualBounds();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        mainStage.initStyle(StageStyle.UTILITY);
        mainStage.setWidth(width);
        mainStage.setHeight(height);
        mainStage.setX(bounds.getMinX());
        mainStage.setY(bounds.getMinY());
        mainStage.setWidth(bounds.getWidth());
        mainStage.setHeight(bounds.getHeight());
        mainStage.setMinWidth(1150);
        mainStage.setMinHeight(600);
        mainStage.setMaximized(true);
        mainStage.setTitle("JESUS IS LORD NOVELETA");
        setUser(user);
        disablebuttonforusher();
        mainStage.show();
    }
    public void disablebuttonforusher(){
        String userlevel = user.getUserlevel();
        if(userlevel.equals("USHER")){
            adduserButton.disableProperty().setValue(true);
            System.out.println("usher");
            loginStage.close();
        }else{
            loginStage.close();
            System.out.println("head");
        }
    }
    
}
