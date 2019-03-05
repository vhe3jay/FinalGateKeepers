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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.util.StringConverter;

public class NewAttendyFormController implements Initializable {

    AttendyModels attendy = new AttendyModels();
    

    /**
     * Get the value of attendy
     *
     * @return the value of attendy
     */
    public AttendyModels getAttendy() {
        return attendy;
    }

    /**
     * Set the value of attendy
     *
     * @param attendy new value of attendy
     */
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
    //@FXML
    //private Spinner<Integer> ageSpinner;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lgbox.getItems().addAll(AttendyModels.lgList.values());
        //SETTING THE RANGE OR SPINNER
        loadcomponent();
        //ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        bdatePicker.setValue(LocalDate.now());
    }

    @FXML
    private void loadcomponent() {
        attendy.nameProperty().bind(nameField.textProperty());
        attendy.dateofbirthProperty().bind(bdatePicker.valueProperty());
        attendy.lifegroupProperty().bind(lgbox.valueProperty());
        attendy.contactnumberProperty().bind(contactField.textProperty());
        attendy.addressProperty().bind(addressField.textProperty());
        //attendy.ageProperty().bind(ageSpinner.valueProperty());

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
        //age = Period.between(attendy.getDateofbirth(), LocalDate.now()).getYears();
        //attendy.setName(nameField.getText());
        //attendy.setLifegroup((AttendyModels.lgList) lgbox.getValue());
        attendy.setAge(Period.between(attendy.getDateofbirth(), LocalDate.now()).getYears());
        //attendy.setDateofbirth(bdatePicker.getValue());
        //attendy.setContactnumber(contactField.getText());
        //attendy.setAddress(addressField.getText());
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
    }
    
    @FXML
    void parseDate(InputMethodEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try{
            LocalDate d = (LocalDate) formatter.parse(bdatePicker.getEditor().getText());
            bdatePicker.setValue(d);
        }catch(Exception er){
            
        }
        
    }


}
