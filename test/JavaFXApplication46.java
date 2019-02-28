import java.time.*;
import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;

public class JavaFXApplication46 extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        Map<String, String> map = new HashMap();
        map.put("2017-05-28", "Peter");
        map.put("2017-05-30", "Paul");
        map.put("2017-05-31", "Mary");
        VBox vbox = new VBox(20);
        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);

        DatePicker endDatePicker = new DatePicker();

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker)
            {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty)
                    {
                        super.updateItem(item, empty);

                        for (Map.Entry<String, String> entry : map.entrySet()) {

                            if (item.toString().equals(entry.getKey())) {
                                setTooltip(new Tooltip(map.get(item.toString())));
                            }
                            //System.out.println(entry.getKey() + "/" + entry.getValue());
                            //
                        }

                    }
                };
            }
        };
        endDatePicker.setDayCellFactory(dayCellFactory);
        endDatePicker.setValue(LocalDate.now());
        vbox.getChildren().add(new Label("End Date:"));
        vbox.getChildren().add(endDatePicker);
        stage.show();
    }
}