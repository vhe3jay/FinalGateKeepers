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

public class timelogsheet extends SQLTableController<timelogsheet>{

    private final ObjectProperty<Timestamp> timelog = new SimpleObjectProperty(null);
    private final IntegerProperty attendy_id = new SimpleIntegerProperty(0);
    private final IntegerProperty id = new SimpleIntegerProperty(0);
    
    private final SQLColumnFX<Integer> ID_COL = new SQLColumnFX("id",id,"Id",int.class);
    private final SQLColumnFX<Integer> ATTENDYID_COL = new SQLColumnFX("attendy_id",attendy_id,"Attendy_id",int.class);
    private final SQLColumnFX<Timestamp> TIMELOG_COL = new SQLColumnFX("timelog",timelog,"Timelog",Timestamp.class);
    private final SQLColumnFX<Integer> JOIN_COL = new SQLColumnFX("timelogsheet_id",id,"Id",int.class);
    public static final String TableName = "timelogsheet";

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

    @Override
    public SQLColumns getFillableColumns() {
        SQLColumns COLS = new SQLColumns(this);
        COLS.put(1, TIMELOG_COL);
        COLS.put(2, ATTENDYID_COL);
        return COLS;
    }

    @Override
    public SQLColumns getDuplicateReferenceColumns() {
        return null;
    }

    @Override
    public SQLColumns getColumns() {
        SQLColumns COLS = new SQLColumns(this);
        COLS.put(1, ID_COL);
        COLS.put(2, ATTENDYID_COL);
        COLS.put(3, TIMELOG_COL);
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

}
