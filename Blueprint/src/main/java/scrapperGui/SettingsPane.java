package scrapperGui;

import lombok.*;
import scrapperGuiHandlers.SettingsPaneHandler;
import de.gsi.chart.ui.BorderedTitledPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Setter
@Getter
public class SettingsPane extends BorderPane {
	
	
	private BorderPane borderPane;
	
	private GridPane gridPane;
	
	private BorderedTitledPane borderedTitledPane;

	private Button loadProxyButton;
	
	private Button loadProxyFromCsvButton;
	
	private Button saveSettingsButton;
	
	private Label label;
	
	private TextField proxySellerApiTextField;
	
	private TextField availableProxyTextField;
	
	private TextField loadProxyFromCsvTextField;
	
	private CheckBox useProxyCheckbox;
	
	private ComboBox<String> requestDelayComboBox;
	
	private ComboBox<String> numberOfThreadsComboBox;
	
	private String[] requestDelayValues = { "150", "250", "500", "1000", "1500", "2000", "3000", "4000", "5000", "7000", "10000" };
	
	private HBox hBox;
	
	private VBox vBox;
	
	private SettingsPaneHandler settingsHandler;
	
	
	public SettingsPane() {
		
		borderPane = new BorderPane();
		
		settingsHandler = new SettingsPaneHandler(this);
		
//		borderPane.setTop(radioButtonsBox());
		
		borderPane.setCenter(proxySettings());
		
		borderPane.setBottom(threadingSettings());
		
		setCenter(otherSettings());
		
		setBottom(buttonPane());
		
		setTop(borderPane);
		
		setHandler();
		
	}
	
	public void setHandler() {
		
		saveSettingsButton.setOnAction(settingsHandler);
		
		requestDelayComboBox.setOnAction(settingsHandler);
		
		loadProxyFromCsvButton.setOnAction(settingsHandler);
		
		loadProxyButton.setOnAction(settingsHandler);
			
	}
	
	public BorderedTitledPane otherSettings() {
		
		gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
		
	    gridPane.setHgap(5);  
	    
	    
		label = new Label("Request Delay:");
		
		requestDelayComboBox = new ComboBox<String>();
		
		requestDelayComboBox.getItems().addAll(requestDelayValues);
		
		requestDelayComboBox.setVisibleRowCount(5);
		
		requestDelayComboBox.setValue("1000");
		
		gridPane.add(label, 1, 1);
		
		gridPane.add(requestDelayComboBox, 2, 1);
		
		
		
		label = new Label("Use Proxy:");
		
		useProxyCheckbox = new CheckBox();
		
		useProxyCheckbox.setDisable(true);
		
		gridPane.add(label, 1, 2);
		
		gridPane.add(useProxyCheckbox, 2, 2);
		
		
		
		borderedTitledPane = new BorderedTitledPane("Settings:", gridPane);
		
		return borderedTitledPane;
		
	}
	
	
	
	public BorderedTitledPane threadingSettings() {
		
		gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
		
	    gridPane.setHgap(5);  
	    
	    
		label = new Label("Number of Threads: (Available With Proxy)");
		
		numberOfThreadsComboBox = new ComboBox<String>();
			
		numberOfThreadsComboBox.setDisable(true);
		
		numberOfThreadsComboBox.setStyle("-fx-opacity: 1.0;");
		
		gridPane.add(label, 1, 1);
		
		gridPane.add(numberOfThreadsComboBox, 2, 1);
	    
	    
		
		
		borderedTitledPane = new BorderedTitledPane("Threading Settings:", gridPane);
		
		return borderedTitledPane;
		
	}
	
	public BorderedTitledPane proxySettings() {
		
		gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
		
	    gridPane.setHgap(5);  
	    
	    
	    
		label = new Label("Available Proxy:");
		
		availableProxyTextField = new TextField();
		
		availableProxyTextField.setMaxWidth(100);
		
		availableProxyTextField.setDisable(true);
		
		availableProxyTextField.setStyle("-fx-opacity: 1.0;");
		
		gridPane.add(label, 1, 1);
		
		gridPane.add(availableProxyTextField, 2, 1);
		
		
		
		
//		label = new Label("proxy-seller.com API Key:");
//		
//		proxySellerApiTextField = new TextField();
//		
//		proxySellerApiTextField.setMaxWidth(300);
//		
//		gridPane.add(label, 1, 2);
//		
//		gridPane.add(proxySellerApiTextField, 2, 2);
		
		
		
		label = new Label("Load Proxy From Csv:");
		
		loadProxyFromCsvTextField = new TextField();
		
		loadProxyFromCsvTextField.setPrefWidth(300);
		
		loadProxyFromCsvTextField.setDisable(true);
		
		loadProxyFromCsvTextField.setStyle("-fx-opacity: 1.0;");
		
		loadProxyFromCsvButton = new Button("...");
		
		gridPane.add(label, 1, 3);
		
		gridPane.add(loadProxyFromCsvTextField, 2, 3);
		
		gridPane.add(loadProxyFromCsvButton, 3, 3);
		
		
		borderedTitledPane = new BorderedTitledPane("Proxy Settings:", gridPane);
		
		return borderedTitledPane;
		
	}
	
	
	public HBox buttonPane() {
		
		hBox = new HBox();
		
		hBox.setPadding(new Insets(10, 10, 10, 10));
		
		hBox.setSpacing(10);
		
		loadProxyButton = new Button("Load Proxy");
		
		loadProxyButton.getStyleClass().addAll("btn","btn-primary");
		
		hBox.getChildren().add(loadProxyButton);
		
		saveSettingsButton = new Button("Save Settings");
		
		saveSettingsButton.getStyleClass().addAll("btn","btn-success");
		
		hBox.getChildren().add(saveSettingsButton);
		
		hBox.setAlignment(Pos.CENTER_RIGHT);
		
		return hBox;
	}

}
