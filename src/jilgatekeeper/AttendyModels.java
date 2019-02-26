package jilgatekeeper;

import java.sql.Timestamp;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class AttendyModels {

    public AttendyModels() {
        
    }
    
    
    public AttendyModels(String Aname, Object Lgroup, String Cnumber, Timestamp Tlog) {
        this.setName(Aname);
        this.setLifegroup((lgList) Lgroup);
        this.setContactnumber(Cnumber);
        this.setTimelog(Tlog);
    }
    
    public AttendyModels(Integer id, String name, Object lifegroup, Integer age, LocalDate dateofbirth, String contactnumber, String address, Timestamp timelog) {
        this.setId(id);
        this.setName(name);
        this.setLifegroup((lgList) lifegroup);
        this.setAge(age);
        this.setDateofbirth(dateofbirth);
        this.setContactnumber(contactnumber);
        this.setAddress(address);
        this.setTimelog(timelog);
    }

    private final IntegerProperty id = new SimpleIntegerProperty(0);
    private final StringProperty name = new SimpleStringProperty("");
    private final IntegerProperty age = new SimpleIntegerProperty(0);
    private final ObjectProperty<LocalDate> dateofbirth = new SimpleObjectProperty(null);
    private final StringProperty contactnumber = new SimpleStringProperty("");
    private final StringProperty address = new SimpleStringProperty("");
    private final ObjectProperty<Timestamp> timelog = new SimpleObjectProperty(null);
    private final ObjectProperty<lgList> lifegroup = new SimpleObjectProperty<>();

    public lgList getLifegroup() {
        return lifegroup.get();
    }

    public void setLifegroup(lgList value) {
        lifegroup.set(value);
    }

    public ObjectProperty lifegroupProperty() {
        return lifegroup;
    }
    

    public Timestamp getTimelog() {
        return timelog.get();
    }

    public final void setTimelog(Timestamp value) {
        timelog.set(value);
    }

    public ObjectProperty timelogProperty() {
        return timelog;
    }
    
    public String getAddress() {
        return address.get();
    }

    public final void setAddress(String value) {
        address.set(value);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getContactnumber() {
        return contactnumber.get();
    }

    public final void setContactnumber(String value) {
        contactnumber.set(value);
    }

    public StringProperty contactnumberProperty() {
        return contactnumber;
    }

    public LocalDate getDateofbirth() {
        return dateofbirth.get();
    }
    
    public java.sql.Date getSQLDateofbirth() {
        return java.sql.Date.valueOf(dateofbirth.get());
    }

    public final void setDateofbirth(LocalDate value) {
        dateofbirth.set(value);
    }

    public ObjectProperty dateofbirthProperty() {
        return dateofbirth;
    }

    public int getAge() {
        return age.get();
    }

    public final void setAge(int value) {
        age.set(value);
    }

    public IntegerProperty ageProperty() {
        return age;
    }
    
    public String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }
    
    public int getId() {
        return id.get();
    }

    public final void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }
    
    

    public enum lgList {
                   FIRST_TIMERS, GUEST, CHILDREN, KKB, YAN, MEN, WOMEN, SENIORS
        }
}