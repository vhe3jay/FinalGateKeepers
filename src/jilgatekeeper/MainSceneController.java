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
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import static jilgatekeeper.JILGateKeeper.listStage;
import static jilgatekeeper.JILGateKeeper.loginStage;
import static jilgatekeeper.JILGateKeeper.mainStage;

public class MainSceneController implements Initializable {
    public static User user = new User();
    Attendy atndy = new Attendy();

    public Attendy getAtndy() {
        return atndy;
    }

    public void setAtndy(Attendy atndy) {
        this.atndy = atndy;
    }

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
    private Label countLabel;
    @FXML
    private Label dateLabel;
    @FXML
    static ImageView jilImage;
    @FXML
    public Button adduserButton;

    public static Stage newStage = new Stage();

    private ObjectProperty<Predicate<Attendy>> nameFilter = new SimpleObjectProperty<>();
    private ObjectProperty<Predicate<Attendy>> lgFilter = new SimpleObjectProperty<>();
    private FilteredList<Attendy> filteredItems = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        dateLabel.setText(String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, YYYY"))));
        dailyattendycounter();
        //countLabel.setText(String.valueOf(JILGateKeeper.createData.size()));
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
        Platform.runLater(()->{
            NewAttendyFormController.showForm();
        });
    }

    @FXML
    public void launchAttendyListForm(ActionEvent event) {
            ((ListofAttendiesController) JILGateKeeper.LOADERS.get("LIST").getController()).loadAttendies();
            listStage.show();
        
    }
    
    public void showmainForm(User user){
        Screen screen = Screen.getPrimary();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Rectangle2D bounds = screen.getVisualBounds();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        mainStage.setWidth(width);
        mainStage.setHeight(height);
        mainStage.initStyle(StageStyle.UTILITY);
        mainStage.setX(bounds.getMinX());
        mainStage.setY(bounds.getMinY());
        mainStage.setMinWidth(1180);
        mainStage.setMinHeight(600);
        mainStage.setMaximized(true);
        mainStage.setTitle("JESUS IS LORD NOVELETA");
        setUser(user);
        disablebuttonforusher();
        mainStage.show();
    }
    
    @FXML
    void launchNewUserForm(ActionEvent event){
        NewUserFormController.showForm();
    }

    void changeSampleLabel(Attendy attendyModels) {
        refreshTable();
    }

    @FXML
    private void deleteButton(ActionEvent evt) {
        Platform.runLater(()->{
            Attendy sel_item = (Attendy) tb.getSelectionModel().getSelectedItem();
        if (sel_item != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this selection?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                JILGateKeeper.createData.remove(sel_item);
                int id = sel_item.delete();
                refreshTable();
                showPopupMessage("Deleted successfully");
            }
        }
        });
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
    
    public void dailyattendycounter(){
        LocalDate dNow = LocalDate.now();
        List records = JILGateKeeper.createData;
        int current_attendies = 0;
        
        for(int i = 0; i < records.size(); i++){
            if(((Attendy)records.get(i)).getLatestLog() != null && ((Attendy)records.get(i)).getLatestLog().toLocalDateTime().toLocalDate().equals(dNow)){
                current_attendies++;
            }
            
        }
        countLabel.setText(String.valueOf(current_attendies));
    }
    
    public static Popup createPopup(final String message) {
    final Popup popup = new Popup();
    popup.setAutoFix(true);
    popup.setAutoHide(true);
    popup.setHideOnEscape(true);
    Label label = new Label(message);
    label.setOnMouseReleased(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            popup.hide();
        }
    });
    label.getStylesheets().add("jilgatekeeper/style.css");
    label.getStyleClass().add("popup");
    popup.getContent().add(label);
    return popup;
}
    
    public static void showPopupMessage(final String message) {
    final Popup popup = createPopup(message);
    popup.setOnShown(new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent e) {
            popup.setX(JILGateKeeper.mainStage.getX() + JILGateKeeper.mainStage.getWidth()/2 - popup.getWidth()/2);
            popup.setY(JILGateKeeper.mainStage.getY() + JILGateKeeper.mainStage.getHeight()/2 - popup.getHeight()/2);
        }
    });        
    popup.show(JILGateKeeper.mainStage);
}
}
