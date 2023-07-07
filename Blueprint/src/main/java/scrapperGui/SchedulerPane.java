package scrapperGui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;



import com.dlsc.gemsfx.CalendarPicker;
import com.dlsc.gemsfx.TimePicker;

import scrapperGuiHandlers.SchedulerPaneHandler;
import de.gsi.chart.ui.BorderedTitledPane;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;
import scrapperModel.ScheduledTask;

@Setter
@Getter
public class SchedulerPane extends BorderPane {
	
	private BorderPane borderPane;
	
	private BorderedTitledPane borderedTitledPane;
	
	private GridPane gridPane;
	
	private Label label;
	
	private TextField taskNameTextField;
	
	private TextField readFromCsvField;
	
	private TextField writeToCsvField;
	
	private Button writeToCsvButton;
	
	private Button readFromCsvButton;
	
	private Button addTaskbutton;
	
	private Button deleteTaskbutton;
	
	private RadioButton dailyRadioButton;
	
	private RadioButton weeklyRadioButton;
	
	private RadioButton onceRadioButton;
	
	private ToggleGroup toggleGroup;
	
	private HBox hBox;
	
	private VBox vBox;
	
	private ComboBox<String> daysOfTheWeekComboBox;
	
	private CalendarPicker calendarPicker;
	
	private TimePicker timePicker;
	
	private String[] daysOfTheWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	
	private TableView<ScheduledTask> tableView;
	
	private TableColumn<ScheduledTask, String> tableColumn;
	
	private TableColumn<ScheduledTask, SimpleBooleanProperty> booleanTableColumn;
	
	private SchedulerPaneHandler schedulerPaneHandler;
	
	private ObservableList<ScheduledTask> scheduledTasks;
		
	
	public SchedulerPane() {
		
		borderPane = new BorderPane();
		
		schedulerPaneHandler = new SchedulerPaneHandler(this);
		
		borderPane.setBottom(chooseFields());

		borderPane.setCenter(labelsAndFieldsPane());
		
		borderPane.setTop(radioButtonsBox());
		
		setBottom(buttonPane());
		
		setCenter(tableView());
		
		setTop(borderPane);
		
		setHandler();
		
	}
	
	public void setHandler() {
		
		
		dailyRadioButton.setOnAction(schedulerPaneHandler);
		
		weeklyRadioButton.setOnAction(schedulerPaneHandler);
		
		onceRadioButton.setOnAction(schedulerPaneHandler);
		
		daysOfTheWeekComboBox.setOnAction(schedulerPaneHandler);
		
		timePicker.setOnAction(schedulerPaneHandler);
		
		calendarPicker.setOnAction(schedulerPaneHandler);
		
		taskNameTextField.setOnAction(schedulerPaneHandler);
		
		writeToCsvButton.setOnAction(schedulerPaneHandler);
		
		readFromCsvButton.setOnAction(schedulerPaneHandler);
		
		addTaskbutton.setOnAction(schedulerPaneHandler);
		
		deleteTaskbutton.setOnAction(schedulerPaneHandler);
		
		
	}
	
	public HBox buttonPane() {
		
		hBox = new HBox();
		
		hBox.setPadding(new Insets(10, 10, 10, 10));
		
		hBox.setSpacing(10);
		
		addTaskbutton = new Button("Add Task");
		
		addTaskbutton.getStyleClass().addAll("btn","btn-primary");
		
		hBox.getChildren().add(addTaskbutton);
		
		deleteTaskbutton = new Button("Delete Task");
		
		deleteTaskbutton.getStyleClass().addAll("btn","btn-danger");
		
		hBox.getChildren().add(deleteTaskbutton);
		
		hBox.setAlignment(Pos.CENTER_RIGHT);
		
		return hBox;
	}
	
	public VBox tableView() {
		
		tableView = new TableView<ScheduledTask>();
		
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		tableView.setEditable(true);

		tableColumn = new TableColumn<ScheduledTask, String>("Task Name");
		
		tableColumn.setCellValueFactory(new PropertyValueFactory<ScheduledTask, String>("taskName"));
		
		tableView.getColumns().add(tableColumn);
		
		
		tableColumn = new TableColumn<ScheduledTask, String>("Date");
		
		tableColumn.setCellValueFactory(new PropertyValueFactory<ScheduledTask, String>("tableDate"));
		
		tableView.getColumns().add(tableColumn);
		
		
		tableColumn = new TableColumn<ScheduledTask, String>("Type");
		
		tableColumn.setCellValueFactory(new PropertyValueFactory<ScheduledTask, String>("type"));
		
		tableView.getColumns().add(tableColumn);
		
		
		tableColumn = new TableColumn<ScheduledTask, String>("Read From");
		
		tableColumn.setCellValueFactory(new PropertyValueFactory<ScheduledTask, String>("readFrom"));
		
		tableView.getColumns().add(tableColumn);
		
		
		tableColumn = new TableColumn<ScheduledTask, String>("Write To");
		
		tableColumn.setCellValueFactory(new PropertyValueFactory<ScheduledTask, String>("writeTo"));
		
		tableView.getColumns().add(tableColumn);
		
		
		
		booleanTableColumn = new TableColumn<ScheduledTask, SimpleBooleanProperty>("Select");
		
//		booleanTableColumn.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().getSelect()));
//		booleanTableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(booleanTableColumn));
//        
//		booleanTableColumn.setCellFactory(tc -> new CheckBoxTableCell<ScheduledTask,SimpleBooleanProperty>());
		
		booleanTableColumn.setCellValueFactory(new PropertyValueFactory<ScheduledTask,SimpleBooleanProperty>("selection"));

		
		booleanTableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(new Callback<Integer, 
				ObservableValue<Boolean>>() {
			
				        @Override
				        public ObservableValue<Boolean> call(Integer param) {
				        
				        	scheduledTasks = FXCollections.observableArrayList(tableView.getItems());
				        	
				            return  scheduledTasks.get(param).getSelection();
				            
				        }
				        
				    }));
		
		booleanTableColumn.setEditable(true);
		
		
		tableView.getColumns().add(booleanTableColumn);
		
	    tableView.setPrefSize(625, 150);

	    
		vBox = new VBox();
		

		vBox.setPadding(new Insets(10, 10, 10, 10));
        
		vBox.getChildren().addAll(tableView);
		
		
		
		
		return vBox;
		
	}
	
	public BorderedTitledPane chooseFields() {
		
		gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
		
	    gridPane.setHgap(5);  
		
		
		label = new Label("Read From Csv:");
		
		readFromCsvField = new TextField();
		
		readFromCsvField.setPrefWidth(475);
		
		readFromCsvField.setDisable(true);
		
		readFromCsvField.setStyle("-fx-opacity: 1.0;");
		
		readFromCsvButton = new Button("...");
		
		
		gridPane.add(label, 1, 1);
		gridPane.add(readFromCsvField, 2, 1);
		gridPane.add(readFromCsvButton, 3, 1);
		
		
		
		label = new Label("Write To Csv:");
		
		writeToCsvField = new TextField();
		
		writeToCsvField.setPrefWidth(475);
		
		writeToCsvField.setDisable(true);
		
		writeToCsvField.setStyle("-fx-opacity: 1.0;");
		
		writeToCsvButton = new Button("...");
		
		
		gridPane.add(label, 1, 2);
		gridPane.add(writeToCsvField, 2, 2);
		gridPane.add(writeToCsvButton, 3, 2);
		
		
		borderedTitledPane = new BorderedTitledPane("Choose Csv:", gridPane);
		
		
		return borderedTitledPane;
		
	}
	
	public BorderedTitledPane labelsAndFieldsPane() {
		
		gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(15, 12, 15, 12));
		
		gridPane.setVgap(5); 
		
	    gridPane.setHgap(5); 
	    
	    
	    
		
		label = new Label("Choose Time:");		
		
		timePicker = new TimePicker();

		timePicker.setTime(LocalDateTime.ofInstant(new Date().toInstant(),
			    ZoneId.systemDefault()).toLocalTime());
		
		gridPane.add(label, 1, 1);
		
		gridPane.add(timePicker, 2, 1);
	    
	    
		
		daysOfTheWeekComboBox = new ComboBox<String>();
		
		daysOfTheWeekComboBox.getItems().addAll(daysOfTheWeek);
				
		label = new Label("Choose Day:");
		
		gridPane.add(label, 1, 2);
		
		gridPane.add(daysOfTheWeekComboBox, 2, 2);
		
		
		
		label = new Label("Choose Date:");
		
		calendarPicker = new CalendarPicker();
		
		gridPane.add(label, 1, 3);
		
		gridPane.add(calendarPicker, 2, 3);
		
		
		
		label = new Label("Task Name:");
		
		taskNameTextField = new TextField();
		
		
		gridPane.add(label, 1, 4);
		
		gridPane.add(taskNameTextField, 2, 4);
		
		
		
		
		borderedTitledPane = new BorderedTitledPane("Choose Date:" , gridPane);
		
		return borderedTitledPane;
		
	}
	
	public BorderedTitledPane radioButtonsBox() {
		
		hBox = new HBox();
		
		hBox.setPadding(new Insets(15, 12, 15, 12));
		
		hBox.setSpacing(10);
	
		toggleGroup = new ToggleGroup();
		
		dailyRadioButton = new RadioButton("Daily");
		
		weeklyRadioButton = new RadioButton("Weekly");
		
		onceRadioButton = new RadioButton("Once");
		
		
		dailyRadioButton.setToggleGroup(toggleGroup);
		
		weeklyRadioButton.setToggleGroup(toggleGroup);
		
		onceRadioButton.setToggleGroup(toggleGroup);

		hBox.getChildren().addAll(dailyRadioButton, weeklyRadioButton , onceRadioButton);
		
		borderedTitledPane = new BorderedTitledPane("Task Frequency:", hBox);
		
		return borderedTitledPane;
		
	}

}
