
package jilgatekeeper;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;


    
public class NewAttendyFormController implements Initializable {

    private AttendyModels attendy = new AttendyModels();

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
    private JFXComboBox lgbox ;
    @FXML
    private Button addButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField addressField;
    @FXML
    private JFXDatePicker bdatePicker ;
    @FXML
    private Spinner<Integer> ageSpinner;
    
    /*
    @FXML
    void newdata(ActionEvent event) {
        System.out.println(attendy.getName());
        System.out.println(attendy.getLifegroup());
        System.out.println(attendy.getAge());
        //System.out.println(attendy.getSQLDateofbirth());
        System.out.println(attendy.getContactnumber());
        System.out.println(attendy.getAddress());
        System.out.println(Timestamp.valueOf(LocalDateTime.now()));
        //COMMAND FOR DISPLAYING THE TIMESTAMP ON MAINSCENE TABLE
        attendy.setTimelog(Timestamp.valueOf(LocalDateTime.now()));
        //ADD COMMAND FOR THE DATA ON MAIN SCENE
        ListofAttendiesController.addAttendyToTable(attendy);
        //DISPOSING OF THE ADD NEW ATTENDY FORM
    }
*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadcomponent();
        
        lgbox.getItems().addAll(AttendyModels.lgList.values());
        //SETTING THE RANGE OR SPINNER
        ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,1));
    }   
    
    @FXML
    private void loadcomponent(){
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
            if(ageValFac != null){
                StringConverter<Integer> converter = ageValFac.getConverter();
                if(text != null && !text.isEmpty()){
                    int val = converter.fromString(text);
                    ageValFac.setValue(val);
                }
            }
        });
    }
    @FXML
    void doSomething(ActionEvent event) {
        ((MainSceneController)JILGateKeeper.LOADERS.get("MAIN").getController()).changeSampleLabel(nameField.getText(), new AttendyModels(nameField.getText(),lgbox.getValue(),contactField.getText(),Timestamp.valueOf(LocalDateTime.now())));
        System.out.println(attendy.getName());
        System.out.println(attendy.getLifegroup());
        System.out.println(attendy.getAge());
        System.out.println(attendy.getSQLDateofbirth());
        System.out.println(attendy.getContactnumber());
        System.out.println(attendy.getAddress());
        System.out.println(Timestamp.valueOf(LocalDateTime.now()));
    }
    
}