package jilgatekeeper;

import com.nakpilse.sql.SQLColumnData;
import com.nakpilse.sql.SQLColumnFX;
import com.nakpilse.sql.SQLColumns;
import com.nakpilse.sql.SQLTableController;
import java.sql.Timestamp;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class AttendyModels extends SQLTableController<AttendyModels>{

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
    private final ObjectProperty<lgList> lifegroup = new SimpleObjectProperty();
    private final IntegerProperty age = new SimpleIntegerProperty(0);
    private final ObjectProperty<LocalDate> dateofbirth = new SimpleObjectProperty(null);
    private final StringProperty contactnumber = new SimpleStringProperty("");
    private final StringProperty address = new SimpleStringProperty("");
    private final ObjectProperty<Timestamp> timelog = new SimpleObjectProperty(null);
    private final ObjectProperty<Timestamp> latestlog = new SimpleObjectProperty(null);
    
    private final SQLColumnFX<Integer> ID_COL = new SQLColumnFX("id",id,"Id",int.class);
    private final SQLColumnFX<String> NAME_COL = new SQLColumnFX("name",name,"Name",String.class);
    private final SQLColumnFX<String> LG_COL = new SQLColumnFX("ligegroup", lifegroup, "LifeGroup",String.class);
    private final SQLColumnFX<Integer> AGE_COL = new SQLColumnFX("age",age,"Age",Integer.class);
    private final SQLColumnFX<LocalDate> DOB_COL = new SQLColumnFX("dateofbirth", dateofbirth, "DateofBirth", LocalDate.class);
    private final SQLColumnFX<String> CONTACT_COL = new SQLColumnFX("contactnumber",contactnumber,"ContactNumber",String.class);
    private final SQLColumnFX<String> ADDRESS_COL = new SQLColumnFX("address",address,"Address",String.class);
    private final SQLColumnFX<Timestamp> TIMELOG_COL = new SQLColumnFX("timelog",timelog,"Timelog",Timestamp.class);
    private final SQLColumnFX<Timestamp> LATESTLOG_COL = new SQLColumnFX("latestlog",latestlog,"Latestlog",Timestamp.class);
    private final SQLColumnFX<Integer> JOIN_COL = new SQLColumnFX("AttendiesTable_Id",id,"",int.class);
    public static final String TableName = "attendiestable";
    
    AttendyModels() {
    }
    
    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public Timestamp getLatestLog() {
        return latestlog.get();
    }

    public void setLatestLog(Timestamp value) {
        latestlog.set(value);
    }

    public ObjectProperty latestLogProperty() {
        return latestlog;
    }

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

    
    @Override
    public String toString() {
        return "AttendyModels{" + "name=" + name + ", age=" + age + ", dateofbirth=" + dateofbirth + ", contactnumber=" + contactnumber + ", address=" + address + ", timelog=" + timelog + ", lifegroup=" + lifegroup + '}';
    }

    @Override
    public SQLColumns getFillableColumns() {
        SQLColumns COLS = new SQLColumns(this);
        COLS.put(1, NAME_COL);
        COLS.put(2, LG_COL);
        COLS.put(3, AGE_COL);
        COLS.put(4, DOB_COL);
        COLS.put(5, CONTACT_COL);
        COLS.put(6, ADDRESS_COL);
        COLS.put(7, LATESTLOG_COL);        
        return COLS;
    }

    @Override
    public SQLColumns getDuplicateReferenceColumns() {
        SQLColumns COLS = new SQLColumns(this);
        COLS.put(1, NAME_COL);
        
        return COLS;
    }

    @Override
    public SQLColumns getColumns() {
        SQLColumns COLS = new SQLColumns(this);
        COLS.put(1, ID_COL);
        COLS.put(2, NAME_COL);
        COLS.put(3, LG_COL);
        COLS.put(4, AGE_COL);
        COLS.put(5, DOB_COL);
        COLS.put(6, CONTACT_COL);
        COLS.put(7, ADDRESS_COL);
        COLS.put(8, TIMELOG_COL);
        COLS.put(9, LATESTLOG_COL);
        
        return COLS;
        
    }

    @Override
    public String getTableName() {
        return TableName;
    }

    @Override
    public SQLColumnData getPrimaryColumn() {
        return ID_COL;
    }

    @Override
    public SQLColumnData getJoinColumn() {
      return JOIN_COL;
    }
    
    
    public enum lgList {
                   FIRST_TIMERS, GUEST, CHILDREN, KKB, YAN, MEN, WOMEN, SENIORS
        }
    public enum sortby{
                    TODAY, LASTWEEK, CUSTOM, ALLTIME
    }
}