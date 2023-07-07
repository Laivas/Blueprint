package scrapperGui;


import scrapperGuiHandlers.DataPaneHandler;
import de.gsi.chart.ui.BorderedTitledPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataPane extends BorderPane {
	
	private BorderPane borderPane;
	
	private GridPane gridPane;
	
	private BorderedTitledPane borderedTitledPane;
	
	private TextField sqliteDatabaseNameField;
	
//	private TextField databaseUserNameField;
//	
//	private TextField databasePasswordField;
	
	private TextField readFromCsvField;
	
	private TextField writeToCsvField;
	
	private TextField sqliteDatabaseLocationField;
	
	private TextField csvFileNameField;
	
	private CheckBox generateCsvFileNameCheckbox;
	
	private CheckBox generateSqlDbFileNameCheckbox;
	
	private Button writeToCsvButton;
	
	private Button sqliteDatabaseLocationButton;
	
	private Button saveButton;
	
//	private Button resetFieldsButton;
	
	private Label label;
	
	private ToggleGroup toggleGroup;
	
	private RadioButton csvFilesRadioButton;
	
	private RadioButton sqLiteRadioButton;
			
	private ComboBox<String> selectSaveDataComboBox;
	
	private String[] selectSaveDataValues = {"Csv File", "Database"};
	
	private DataPaneHandler dataPaneHandler;
	
	private HBox hBox;
	
	private VBox vBox;
	
	public DataPane() {
		
		borderPane = new BorderPane();
		
		dataPaneHandler = new DataPaneHandler(this);
		
		borderPane.setTop(selectDataSave());
		
		borderPane.setCenter(selectionForCsvFiles());
		
		borderPane.setBottom(selectionForSqLite());
		
		setBottom(buttonPane());
		
		setTop(borderPane);
		
		setHandler();
		
	}
	
	private void setHandler() {
		
		sqLiteRadioButton.setOnAction(dataPaneHandler);
		
		csvFilesRadioButton.setOnAction(dataPaneHandler);
		
		writeToCsvButton.setOnAction(dataPaneHandler);
		
		sqliteDatabaseLocationButton.setOnAction(dataPaneHandler);
		
		generateCsvFileNameCheckbox.setOnAction(dataPaneHandler);
		
		generateSqlDbFileNameCheckbox.setOnAction(dataPaneHandler);
		
		saveButton.setOnAction(dataPaneHandler);
		
//		resetFieldsButton.setOnAction(dataPaneHandler);
		
	}
	
	public HBox buttonPane() {
		
		hBox = new HBox();
		
		hBox.setPadding(new Insets(10, 10, 10, 10));
		
		hBox.setSpacing(10);
		
		saveButton = new Button("Save");
		
		saveButton.getStyleClass().addAll("btn","btn-success");
		
		hBox.getChildren().add(saveButton);
		
//		resetFieldsButton = new Button("Reset Fields");
//		
//		resetFieldsButton.getStyleClass().addAll("btn","btn-secondary");
//		
//		hBox.getChildren().add(resetFieldsButton);
		
		hBox.setAlignment(Pos.CENTER_RIGHT);
		
		return hBox;
	}
	
	public BorderedTitledPane selectionForCsvFiles() {
		
		gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
		
	    gridPane.setHgap(5);  
		
		
		label = new Label("Generate Name:");
		
		generateCsvFileNameCheckbox = new CheckBox();
		
//		readFromCsvField.setPrefWidth(475);
//		
//		readFromCsvField.setDisable(true);
//		
//		readFromCsvField.setStyle("-fx-opacity: 1.0;");
//		
//		readFromCsvButton = new Button("...");
		
		
		gridPane.add(label, 1, 1);
		gridPane.add(generateCsvFileNameCheckbox, 2, 1);
//		gridPane.add(readFromCsvButton, 3, 1);
	    
		label = new Label("Csv File Name:");		
		
		csvFileNameField = new TextField("");
		
		csvFileNameField.setMaxWidth(200);
	
		
		gridPane.add(label, 1, 2);
		
		gridPane.add(csvFileNameField, 2, 2);
		
		
		
		label = new Label("Csv Folder:");
		
		writeToCsvField = new TextField();
		
		writeToCsvField.setPrefWidth(475);
		
		writeToCsvField.setDisable(true);
		
		writeToCsvField.setStyle("-fx-opacity: 1.0;");
		
		writeToCsvButton = new Button("...");
		
		
		gridPane.add(label, 1, 3);
		gridPane.add(writeToCsvField, 2, 3);
		gridPane.add(writeToCsvButton, 3, 3);
		
		
		borderedTitledPane = new BorderedTitledPane("Choose Default Csv Location:", gridPane);
		
		
		return borderedTitledPane;
		
	}
	
	public BorderedTitledPane selectionForSqLite() {
		
		gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(15, 12, 15, 12));
		
		gridPane.setVgap(5); 
		
	    gridPane.setHgap(5); 
	    
	    
		label = new Label("Generate Name:");
		
		generateSqlDbFileNameCheckbox = new CheckBox();
		
		
		gridPane.add(label, 1, 1);
		gridPane.add(generateSqlDbFileNameCheckbox, 2, 1);
	    	    
	    
		
		label = new Label("Database Name:");		
		
		sqliteDatabaseNameField = new TextField("");
		
		sqliteDatabaseNameField.setMaxWidth(200);
	
		
		gridPane.add(label, 1, 2);
		
		gridPane.add(sqliteDatabaseNameField, 2, 2);
		
		
		label = new Label("Database Folder:");
		
		sqliteDatabaseLocationField = new TextField();
		
		sqliteDatabaseLocationField.setPrefWidth(475);
		
		sqliteDatabaseLocationField.setDisable(true);
		
		sqliteDatabaseLocationField.setStyle("-fx-opacity: 1.0;");
		
		sqliteDatabaseLocationButton = new Button("...");
		
		
		gridPane.add(label, 1, 3);
		gridPane.add(sqliteDatabaseLocationField, 2, 3);
		gridPane.add(sqliteDatabaseLocationButton, 3, 3);
		
				
//		label = new Label("Database Folder:");
//		
//		databaseLocationField = new TextField("");
//		
//		gridPane.add(label, 1, 2);
//		
//		gridPane.add(databaseLocationField, 2, 2);
//		
//		
//		
//		label = new Label("Password:");
//		
//		databasePassword = new TextField("");
//		
//		
//		gridPane.add(label, 1, 4);
//		
//		gridPane.add(databasePassword, 2, 4);
		
		
		borderedTitledPane = new BorderedTitledPane("Sqlite Database:" , gridPane);

		return borderedTitledPane;
	}
	
	public BorderedTitledPane selectDataSave() {
		
		hBox = new HBox();
		
		hBox.setPadding(new Insets(15, 12, 15, 12));
		
		hBox.setSpacing(10);
		
		
		toggleGroup = new ToggleGroup();
		
		csvFilesRadioButton = new RadioButton("Csv");
		
		sqLiteRadioButton = new RadioButton("SQLite");
		
		csvFilesRadioButton.setToggleGroup(toggleGroup);
		
		sqLiteRadioButton.setToggleGroup(toggleGroup);
		
		hBox.getChildren().addAll(csvFilesRadioButton, sqLiteRadioButton);
				
		borderedTitledPane = new BorderedTitledPane("Where To Save:", hBox);
		
		return borderedTitledPane;
		
	}

}
