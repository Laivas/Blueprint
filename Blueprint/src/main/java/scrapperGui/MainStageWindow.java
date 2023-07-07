package scrapperGui;

import org.kordamp.bootstrapfx.BootstrapFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;
import scrapperScheduler.ScrappingJob;

public class MainStageWindow extends Application {

	private Scene scene;
	
	private TabPane tabPane;
	
	private Tab tab;
	
	private MainPane mainPane;
	
	private SettingsPane settingsPane;
	
	private DataPane dataPane;
	
	private SchedulerPane schedulerPane;
	
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
	
	public void initPanes() {
		
		mainPane = new MainPane();
		
		settingsPane = new SettingsPane();
		
		dataPane = new DataPane();
		
		schedulerPane = new SchedulerPane();
		
	}
	
	
	public TabPane addTabPane() {
		
		tabPane = new TabPane();
		
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		initPanes();
		
		tab = new Tab("Main", mainPane);
		
		tabPane.getTabs().add(tab);
		
		
		tab = new Tab("Settings", settingsPane);
		
		tabPane.getTabs().add(tab);
		
		
		tab = new Tab("Data", dataPane);
		
		tabPane.getTabs().add(tab);
		
		
		tab = new Tab("Scheduler", schedulerPane);
		
		tabPane.getTabs().add(tab);
		
		
		return tabPane;
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		scene = new Scene(addTabPane(), 650, 550);
		
		loadXml();
		
		loadScheduler();
		
		scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
		
		primaryStage.setTitle("Scrapper");
		
		primaryStage.setScene(scene);
		
		primaryStage.setResizable(false);
		
		primaryStage.show();
		
	}
	
	@Override
	public void stop(){
		
		schedulerPane.getSchedulerPaneHandler().writeScheduledTasksFromTableToXml();
		
		System.exit(0);
		
	}
	
	public void loadScheduler() {
		
		schedulerPane.getSchedulerPaneHandler().setJobImplementation(new ScrappingJob());
		
		schedulerPane.getSchedulerPaneHandler().setWorkable(mainPane);
		
		System.out.println(mainPane.getWorkableClassName());
		
		schedulerPane.getSchedulerPaneHandler().quartzSchedule();

		
	}
	
	public void loadXml() {
		
		settingsPane.getSettingsHandler().loadSettingsPaneSelectionFromXml();
		
		dataPane.getDataPaneHandler().loadDataPaneSelectionFromXml();
		
		schedulerPane.getSchedulerPaneHandler().loadScheduledTasksFromXmlToTable();
		
	}


}
