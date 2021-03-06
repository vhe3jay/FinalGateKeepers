package jilgatekeeperexp;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class NewAttendyFormController implements Initializable {

    private AttendyModels attendy = new AttendyModels();
    
    public AttendyModels getAttendy() {
        return attendy;
    }
    
    public void setAttendy(AttendyModels attendy) {
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
    @FXML
    private Spinner<Integer> ageSpinner;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadcomponent();

        lgbox.getItems().addAll(AttendyModels.lgList.values());
        //SETTING THE RANGE OR SPINNER
        ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        bdatePicker.setValue(LocalDate.now());
    }

    @FXML
    private void loadcomponent() {
        attendy.nameProperty().bind(nameField.textProperty());
        attendy.ageProperty().bind(ageSpinner.valueProperty());
        attendy.dateofbirthProperty().bind(bdatePicker.valueProperty());
        attendy.lifegroupProperty().bind(lgbox.valueProperty());
        attendy.contactnumberProperty().bind(contactField.textProperty());
        attendy.addressProperty().bind(addressField.textProperty());
        attendy.ageProperty().bind(ageSpinner.valueProperty());

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
    }

    @FXML
    void doSomething(ActionEvent event) {
        ((MainSceneController) JILGateKeeper.LOADERS.get("MAIN").getController()).changeSampleLabel(nameField.getText(), new AttendyModels(nameField.getText(), lgbox.getValue(), contactField.getText(), Timestamp.valueOf(LocalDateTime.now())));
        //((ListofAttendiesController) JILGateKeeper.LOADERS.get("LIST").getController()).textsample();
        JILGateKeeper.getListController().changeSampleLabel(new AttendyModels(nameField.getText(), lgbox.getValue(), ageSpinner.getValue(), bdatePicker.getValue(), contactField.getText(), addressField.getText(), Timestamp.valueOf(LocalDateTime.now())));
       /*
        System.out.println(attendy.getName());
        System.out.println(attendy.getLifegroup());
        System.out.println(attendy.getAge());
        System.out.println(attendy.getSQLDateofbirth());
        System.out.println(attendy.getContactnumber());
        System.out.println(attendy.getAddress());
        System.out.println(Timestamp.valueOf(LocalDateTime.now()));
*/      MainSceneController.newStage.close();
    }

}
