package scrapperGui;

import lombok.*;
import scrapperGuiHandlers.MainPaneHandler;
import scrapperScheduler.Workable;
import de.gsi.chart.ui.BorderedTitledPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

@Getter
@Setter
public class MainPane extends BorderPane implements Workable {
	
	private MainPaneHandler mainPaneHandler;
	
	private BorderPane borderPane;
	
	private Button startButton;
	
	private Button pauseResumeButton;
	
	private Button stopButton;
	
	private HBox hBox;
	
	private GridPane gridPane;
	
	private Label label;
	
	private TextField progressField;
	
	private TextField statusField;
	
	private TextField timeElapsedField;
	
	private BorderedTitledPane borderedTitledPane;
	
	private TextField csvUrlsListField;
	
	private Button csvUrlsListButton;
	
	
	public MainPane() {
			
		borderPane = new BorderPane();
		
		mainPaneHandler = new MainPaneHandler(this);
		
		borderPane.setTop(searchFields());
		
		borderPane.setCenter(progressFields());
		
		setTop(borderPane);
		
		setBottom(buttonPane());
		
		setHandler();
		
	}
	
	public void setHandler() {
		
		csvUrlsListButton.setOnAction(mainPaneHandler);
		
		startButton.setOnAction(mainPaneHandler);
		
		pauseResumeButton.setOnAction(mainPaneHandler);
		
		stopButton.setOnAction(mainPaneHandler);
		
	}
	
	public BorderedTitledPane searchFields() {
		
		gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(15, 12, 15, 12));
		
		gridPane.setVgap(5); 
		
	    gridPane.setHgap(5); 
	    
	    
		label = new Label("Csv Urls List:");
		
		csvUrlsListField = new TextField();
		
		csvUrlsListField.setPrefWidth(475);
		
		csvUrlsListField.setDisable(true);
		
		csvUrlsListField.setStyle("-fx-opacity: 1.0;");
		
		csvUrlsListButton = new Button("...");
		
		
		gridPane.add(label, 1, 3);
		gridPane.add(csvUrlsListField, 2, 3);
		gridPane.add(csvUrlsListButton, 3, 3);
		
		
		borderedTitledPane = new BorderedTitledPane("Query:", gridPane);
		
		return borderedTitledPane;
		
	}
	
	
	public BorderedTitledPane progressFields() {
		
		gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(15, 12, 15, 12));
		
		gridPane.setVgap(5); 
		
	    gridPane.setHgap(5); 
	    
	    
		label = new Label("Time Elapsed:");
		
		timeElapsedField = new TextField();
		
		timeElapsedField.setDisable(true);
		
		timeElapsedField.setPrefWidth(250);
		
		timeElapsedField.setStyle("-fx-opacity: 1.0;");
		
		
		gridPane.add(label, 1, 1);
		gridPane.add(timeElapsedField, 2, 1);
	    
	    
	    
		label = new Label("Status:");
		
		statusField = new TextField();
		
		statusField.setDisable(true);
		
		statusField.setPrefWidth(250);
		
		statusField.setStyle("-fx-opacity: 1.0;");
		
		
		gridPane.add(label, 1, 2);
		gridPane.add(statusField, 2, 2);
		
	    
	    
		label = new Label("Progress:");
		
		progressField = new TextField();
		
		progressField.setDisable(true);
		
		progressField.setPrefWidth(250);
		
		progressField.setStyle("-fx-opacity: 1.0;");
		
		
		gridPane.add(label, 1, 3);
		gridPane.add(progressField, 2, 3);
		
		
		
		borderedTitledPane = new BorderedTitledPane("Progress:", gridPane);
		
		return borderedTitledPane;
		
	}
	
	public HBox buttonPane() {
		
		hBox = new HBox();
		
		hBox.setPadding(new Insets(10, 10, 10, 10));
		
		hBox.setSpacing(10);
		
		startButton = new Button("Start");
		
		startButton.getStyleClass().addAll("btn","btn-success");
		
		hBox.getChildren().add(startButton);
		
		pauseResumeButton = new Button("Pause");
		
		pauseResumeButton.getStyleClass().addAll("btn","btn-info");
		
		hBox.getChildren().add(pauseResumeButton);
		
		stopButton = new Button("Stop");
		
		stopButton.getStyleClass().addAll("btn","btn-danger");
		
		hBox.getChildren().add(stopButton);
		
		hBox.setAlignment(Pos.CENTER_RIGHT);
		
		return hBox;
		
	}

	@Override
	public Workable getWorkable() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getWorkableClassName() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}

}
