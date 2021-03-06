package jilgatekeeper;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.StageStyle;
import static jilgatekeeper.MainSceneController.newStage;

public class NewAttendyFormController implements Initializable {

    Attendy attendy = new Attendy();

    public Attendy getAttendy() {
        return attendy;
    }

    public void setAttendy(Attendy attendy) {
        this.attendy = attendy;
    }

    @FXML
    private JFXComboBox lgbox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField addressField;
    @FXML
    private JFXDatePicker bdatePicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lgbox.getItems().addAll(Attendy.lgList.values());
        loadcomponent();
        bdatePicker.setValue(LocalDate.now());
    }

    @FXML
    private void loadcomponent() {
        attendy.nameProperty().bind(nameField.textProperty());
        attendy.dateofbirthProperty().bind(bdatePicker.valueProperty());
        attendy.lifegroupProperty().bind(lgbox.valueProperty().asString());
        attendy.contactnumberProperty().bind(contactField.textProperty());
        attendy.addressProperty().bind(addressField.textProperty());
    }
        /*
        //SETTING THE RANGE OR SPINNER
        ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        //CONVERTER OF FOR THE VALUE ON SPINNER
        ageSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            SpinnerValueFactory<Integer> ageValFac = ageSpinner.getValueFactory();
            String text = ageSpinner.getEditor().getText();
            if (ageValFac != null) {
                StringConverter<Integer> converter = ageValFac.getConverter();
                if (text != null && !text.isEmpty()) {
                    int val = converter.fromString(text);
                    ageValFac.setValue(val);
                }
            }
        });
         */
    @FXML
    void doSomething(ActionEvent event) {
        attendy.setAge(Period.between(attendy.getDateofbirth(), LocalDate.now()).getYears());
        attendy.setTimelog(Timestamp.valueOf(LocalDateTime.now()));

        ((MainSceneController) JILGateKeeper.LOADERS.get("MAIN").getController()).changeSampleLabel(attendy);
        ((ListofAttendiesController) JILGateKeeper.LOADERS.get("LIST").getController()).changeSampleLabel(attendy);
        attendy.save();
        MainSceneController.newStage.close();
        ((MainSceneController) JILGateKeeper.LOADERS.get("MAIN").getController()).refreshTable();
    }

    @FXML
    void parseDate(InputMethodEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate d = (LocalDate) formatter.parse(bdatePicker.getEditor().getText());
            bdatePicker.setValue(d);
        } catch (Exception er) {
        }
    }
    
    @FXML
    public static void showForm() {
        Platform.runLater(()->{
        try {
            FXMLLoader COMPANY_LOADER = new FXMLLoader(NewAttendyFormController.class.getResource("NewAttendyForm.fxml"));
            Scene newsc = new Scene(COMPANY_LOADER.load());
            newStage.initStyle(StageStyle.UTILITY);
            newStage.setTitle("Add New Attendy!");
            newStage.setScene(newsc);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        });
    }
}
