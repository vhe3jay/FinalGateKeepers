package jilgatekeeper;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

public class NewAttendyFormController implements Initializable {

    AttendyModel attendy = new AttendyModel();

    public AttendyModel getAttendy() {
        return attendy;
    }

    public void setAttendy(AttendyModel attendy) {
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

        lgbox.getItems().addAll(AttendyModel.lgList.values());
        loadcomponent();
        bdatePicker.setValue(LocalDate.now());
        //SETTING THE RANGE OR SPINNER
        //ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
    }

    @FXML
    private void loadcomponent() {
        attendy.nameProperty().bind(nameField.textProperty());
        attendy.dateofbirthProperty().bind(bdatePicker.valueProperty());
        attendy.lifegroupProperty().bind(lgbox.valueProperty().asString());
        attendy.contactnumberProperty().bind(contactField.textProperty());
        attendy.addressProperty().bind(addressField.textProperty());

        /*
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
    }

    @FXML
    void doSomething(ActionEvent event) {
        //attendy.setName(nameField.getText());
        //attendy.setLifegroup((AttendyModel.lgList) lgbox.getValue());
        attendy.setAge(Period.between(attendy.getDateofbirth(), LocalDate.now()).getYears());
        attendy.setTimelog(Timestamp.valueOf(LocalDateTime.now()));
        

        ((MainSceneController) JILGateKeeper.LOADERS.get("MAIN").getController()).changeSampleLabel(attendy);
        ((ListofAttendiesController) JILGateKeeper.LOADERS.get("LIST").getController()).changeSampleLabel(attendy);

        System.out.println(attendy.getName());
        System.out.println(attendy.getLifegroup());
        System.out.println(attendy.getAge());
        System.out.println(attendy.getSQLDateofbirth());
        System.out.println(attendy.getContactnumber());
        System.out.println(attendy.getAddress());
        System.out.println(Timestamp.valueOf(LocalDateTime.now()));
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
}
