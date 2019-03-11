package jilgatekeeper;

import com.nakpilse.sql.SQLColumnData;
import com.nakpilse.sql.SQLColumnFX;
import com.nakpilse.sql.SQLColumns;
import com.nakpilse.sql.SQLTableController;
import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class User extends SQLTableController<User>{

    private final IntegerProperty id = new SimpleIntegerProperty(0);
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty contactnumber = new SimpleStringProperty("");
    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");
    private final StringProperty userlevel = new SimpleStringProperty("");
    private final ObjectProperty<Timestamp> created_at = new SimpleObjectProperty(null);
    private final ObjectProperty<Timestamp> updated_at = new SimpleObjectProperty(null);
    
    private final SQLColumnFX<Integer> ID_COL = new SQLColumnFX("id",id,"Id",int.class);
    private final SQLColumnFX<String> NAME_COL = new SQLColumnFX("name",name,"Name",String.class);
    private final SQLColumnFX<String> NUMBER_COL = new SQLColumnFX("contactnumber",contactnumber,"Contactnumber",String.class);
    private final SQLColumnFX<String> USER_COL = new SQLColumnFX("username",username,"Username",String.class);
    private final SQLColumnFX<String> PW_COL = new SQLColumnFX("password",password,"Password",String.class);
    private final SQLColumnFX<String> LEVEL_COL = new SQLColumnFX("userlevel",userlevel,"Userlevel",String.class);
    private final SQLColumnFX<Timestamp> CREATED_COL = new SQLColumnFX("created_at",created_at,"Created_at",Timestamp.class);
    private final SQLColumnFX<Timestamp> UPDATED_COL = new SQLColumnFX("updated_at",updated_at,"Updated_at",Timestamp.class);
    private final SQLColumnFX<Integer> JOIN_COL = new SQLColumnFX("securitydatatable_Id",id,"",int.class);
    public static final String TableName = "securitydatatable";
    

    public String getUserlevel() {
        return userlevel.get();
    }

    public void setUserlevel(String value) {
        userlevel.set(value);
    }

    public StringProperty userlevelProperty() {
        return userlevel;
    }
    public Timestamp getUpdated_at() {
        return updated_at.get();
    }

    public void setUpdated_at(Timestamp value) {
        updated_at.set(value);
    }

    public ObjectProperty updated_atProperty() {
        return updated_at;
    }

    public Timestamp getCreated_at() {
        return created_at.get();
    }

    public void setCreated_at(Timestamp value) {
        created_at.set(value);
    }

    public ObjectProperty created_atProperty() {
        return created_at;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String value) {
        password.set(value);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String value) {
        username.set(value);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getContactnumber() {
        return contactnumber.get();
    }

    public void setContactnumber(String value) {
        contactnumber.set(value);
    }

    public StringProperty contactnumberProperty() {
        return contactnumber;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
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

    @Override
    public SQLColumns getFillableColumns() {
        SQLColumns COLS = new SQLColumns(this);
        COLS.put(1, NAME_COL);
        COLS.put(2, NUMBER_COL);
        COLS.put(3, USER_COL);
        COLS.put(4, PW_COL);
        COLS.put(5, LEVEL_COL); 
        return COLS;
    }

    @Override
    public SQLColumns getDuplicateReferenceColumns() {
        SQLColumns COLS = new SQLColumns(this);
        COLS.put(1, NAME_COL);
        COLS.put(2, USER_COL);
        return COLS;
    }

    @Override
    public SQLColumns getColumns() {
        SQLColumns COLS = new SQLColumns(this);
        COLS.put(1, ID_COL);
        COLS.put(2, NAME_COL);
        COLS.put(3, NUMBER_COL);
        COLS.put(4, USER_COL);
        COLS.put(5, PW_COL);
        COLS.put(6, LEVEL_COL); 
        COLS.put(7, CREATED_COL);
        COLS.put(8, UPDATED_COL);
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
    public enum securitylevel{
                    USHER, HEAD
    }
    
}
