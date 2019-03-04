package jilgatekeeper;

import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class timelogsheet {

    private final ObjectProperty<Timestamp> timelog = new SimpleObjectProperty(null);
    private final IntegerProperty attendy_id = new SimpleIntegerProperty(0);
    private final IntegerProperty id = new SimpleIntegerProperty(0);

    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }
    

    public int getAttendy_id() {
        return attendy_id.get();
    }

    public void setAttendy_id(int value) {
        attendy_id.set(value);
    }

    public IntegerProperty attendy_idProperty() {
        return attendy_id;
    }
    

    public Timestamp getTimelog() {
        return timelog.get();
    }

    public void setTimelog(Timestamp value) {
        timelog.set(value);
    }

    public ObjectProperty timelogProperty() {
        return timelog;
    }

}
