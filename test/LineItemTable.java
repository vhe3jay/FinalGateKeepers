import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class LineItemTable extends Application {

    @Override
    public void start(Stage primaryStage) {
        TableView<LineItem> table = new TableView<>();
        table.setEditable(true);

        // columns: arguments to utility function are:
        // title, property, editable, converter (for editing cell)
        
        table.getColumns().add(
                column("Name", LineItem::nameProperty, false, null));

        table.getColumns().add(
                column("Quantity", LineItem::quantityProperty, true,
                        new IntegerStringConverter()));

        table.getColumns().add(
                column("Unit Price", LineItem::unitPriceProperty, true,
                        new DoubleStringConverter()));

        table.getColumns().add(
                column("Total Price", LineItem::totalProperty, false, null));

        // actual data list. Use an extractor so the "total" line can observe changes:
        ObservableList<LineItem> items = FXCollections.observableArrayList(item -> 
            new Observable[] { item.totalProperty() });
        
        // use transformation list defined below for actual tableview:
        table.setItems(new LineItemListWithTotal(items));
        
        // row factory just sets a CSS pseudoclass on the total row, for styling it differently:
        table.setRowFactory(tv -> {
            PseudoClass lastLinePC = PseudoClass.getPseudoClass("last-line");
            TableRow<LineItem> row = new TableRow<>();
            row.indexProperty().addListener((obs, oldIndex, newIndex) -> {
                row.pseudoClassStateChanged(lastLinePC, newIndex.intValue() == items.size());
            });
            items.addListener((Change<? extends LineItem> change) -> {
                row.pseudoClassStateChanged(lastLinePC, row.getIndex() == items.size());                
            });
            return row ;
        });

        // create a form for adding and removing elements. Note we pass it the actual data list
        // (not the transformed list the tableview is observing)
        GridPane editor = createEditor(items, table.getSelectionModel()
                .selectedIndexProperty());

        BorderPane root = new BorderPane(table, null, null, editor, null);
        
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("line-item-table.css");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // utility function for creating table columns:
    private static <S, T> TableColumn<S, T> column(String title,
            Function<S, ObservableValue<T>> property, boolean editable,
            StringConverter<T> converter) {
        
        TableColumn<S, T> col = new TableColumn<>(title);
        
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        
        col.setEditable(editable);
        
        if (editable) {
            col.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        }
        
        return col;
    }

    // TransformationList implementation. This TransformationList just has
    // one extra line at the end, displaying the total. We use a subclass of
    // LineItem for that line:
    
    public static class LineItemListWithTotal extends
            TransformationList<LineItem, LineItem> {

        private final TotalLine totalLine;

        protected LineItemListWithTotal(
                ObservableList<? extends LineItem> source) {
            super(source);
            totalLine = new TotalLine(source);
        }

        @Override
        protected void sourceChanged(Change<? extends LineItem> c) {

            // no need to modify change:
            // indexes generated by the source list will match indexes in this
            // list

            fireChange(c);
        }

        // if index is in range for source list, just return that index
        // otherwise return -1, indicating index is not represented in source
        @Override
        public int getSourceIndex(int index) {
            if (index < getSource().size()) {
                return index;
            }
            return -1;
        }

        // if index is in range for source list, return corresponding
        // item from source list.
        // if index is one after the last element in the source list,
        // return total line.
        @Override
        public LineItem get(int index) {
            if (index < getSource().size()) {
                return getSource().get(index);
            } else if (index == getSource().size()) {
                return totalLine;
            } else
                throw new ArrayIndexOutOfBoundsException(index);
        }

        // size of transformation list is one bigger than size of source list:
        @Override
        public int size() {
            return getSource().size() + 1;
        }

    }

    // Model class. Note this is carefully created to allow subclassing for the "total" line.
    // To do this, we want to allow nullable values for quantity and unit price (as they make
    // no sense in the total; hence we use ObjectProperty<Integer> instead of IntegerProperty.
    // Note we also allow the xxxProperty() methods to be overriden, but not the getXxx and 
    // setXxx. This still enforces the xxxProperty().get() == getXxx() rule (etc), while allowing
    // us to replace the total property in a subclass.
    public static class LineItem {

        private final StringProperty name = new SimpleStringProperty();
        private final ObjectProperty<Integer> quantity = new SimpleObjectProperty<>();
        private final ObjectProperty<Double> unitPrice = new SimpleObjectProperty<>();
        private final ReadOnlyObjectWrapper<Double> total = new ReadOnlyObjectWrapper<>();

        public LineItem(String name, Integer quantity, Double unitPrice) {
            setName(name);
            setQuantity(quantity);
            setUnitPrice(unitPrice);

            // Obvious binding for the total of this line item: 
            // total = quantity * unit price
            total.bind(Bindings.createObjectBinding(() -> {
                if (quantityProperty().get() == null
                        || unitPriceProperty().get() == null) {
                    return 0.0;
                }
                return quantityProperty().get() * unitPriceProperty().get();
            }, quantityProperty(), unitPriceProperty()));
        }

        public ObjectProperty<Integer> quantityProperty() {
            return this.quantity;
        }

        public final Integer getQuantity() {
            return this.quantityProperty().get();
        }

        public final void setQuantity(final Integer quantity) {
            this.quantityProperty().set(quantity);
        }

        public ObjectProperty<Double> unitPriceProperty() {
            return this.unitPrice;
        }

        public final Double getUnitPrice() {
            return this.unitPriceProperty().get();
        }

        public final void setUnitPrice(final Double unitPrice) {
            this.unitPriceProperty().set(unitPrice);
        }

        public ReadOnlyObjectProperty<Double> totalProperty() {
            return this.total.getReadOnlyProperty();
        }

        public final java.lang.Double getTotal() {
            return this.totalProperty().get();
        }

        public final StringProperty nameProperty() {
            return this.name;
        }

        public final String getName() {
            return this.nameProperty().get();
        }

        public final void setName(final String name) {
            this.nameProperty().set(name);
        }

    }

    // Special subclass to represent the total of all the line items.
    // Just sets quantity and unit price to null.
    // Overrides totalProperty() to return our own property, that is bound to
    // the data list.
    public static class TotalLine extends LineItem {

        private final ReadOnlyObjectWrapper<Double> total = new ReadOnlyObjectWrapper<>();

        public TotalLine(ObservableList<? extends LineItem> items) {
            super("Total", null, null);

            // Bind total to the sum of the totals of all the other line items:
            total.bind(Bindings.createObjectBinding(() -> items.stream()
                    .collect(Collectors.summingDouble(LineItem::getTotal)),
                    items));
        }

        @Override
        public ReadOnlyObjectProperty<Double> totalProperty() {
            return total;
        }
    }

    // Just defines the form. Note how we add and remove elements from the
    // original data list without needing to know there is a transformation list
    // attached to the table.
    private GridPane createEditor(ObservableList<LineItem> items,
            ReadOnlyIntegerProperty selectedIndex) {

        TextField nameField = new TextField();
        TextField quantityField = new TextField();
        TextField unitPriceField = new TextField();

        nameField.setOnAction(e -> addItem(items, nameField, quantityField,
                unitPriceField));
        quantityField.setOnAction(e -> addItem(items, nameField, quantityField,
                unitPriceField));
        unitPriceField.setOnAction(e -> addItem(items, nameField,
                quantityField, unitPriceField));

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addItem(items, nameField, quantityField,
                unitPriceField));

        Button deleteButton = new Button("Delete");
        deleteButton.setDisable(true);
        selectedIndex.addListener((obs, oldIndex, newIndex) -> {
            deleteButton.setDisable(newIndex.intValue() < 0
                    || newIndex.intValue() >= items.size());
        });
        items.addListener((Change<? extends LineItem> change) -> {
            deleteButton.setDisable(selectedIndex.get() < 0
                    || selectedIndex.get() >= items.size());
        });

        deleteButton.setOnAction(e -> items.remove(selectedIndex.get()));

        GridPane editor = new GridPane();
        editor.setHgap(5);
        editor.setVgap(5);

        editor.addRow(0, new Label("Name:"), nameField);
        editor.addRow(1, new Label("Quantity:"), quantityField);
        editor.addRow(2, new Label("Unit Price:"), unitPriceField);

        HBox buttons = new HBox(5, addButton, deleteButton);
        buttons.setAlignment(Pos.CENTER);
        editor.add(buttons, 0, 3, 2, 1);

        ColumnConstraints leftCol = new ColumnConstraints();
        leftCol.setHalignment(HPos.RIGHT);
        ColumnConstraints rightCol = new ColumnConstraints();
        rightCol.setHalignment(HPos.LEFT);
        rightCol.setHgrow(Priority.ALWAYS);

        editor.getColumnConstraints().addAll(leftCol, rightCol);
        editor.setAlignment(Pos.CENTER);
        editor.setPadding(new Insets(10));
        return editor;
    }

    private void addItem(ObservableList<LineItem> items, TextField nameField,
            TextField quantityField, TextField unitPriceField) {
        String name = nameField.getText();
        Integer quantity = new Integer(quantityField.getText());
        Double unitPrice = new Double(unitPriceField.getText());
        items.add(new LineItem(name, quantity, unitPrice));
        nameField.setText("");
        quantityField.setText("");
        unitPriceField.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}