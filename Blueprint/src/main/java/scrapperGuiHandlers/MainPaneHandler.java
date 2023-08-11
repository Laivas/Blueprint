package scrapperGuiHandlers;

import lombok.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.time.DurationFormatUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import scrapper.Query;
import scrapper.QueryImpl;
import scrapper.Scrapper;
import scrapper.ScrapperImpl;
import scrapperDatabase.SqliteDatabaseConnection;
import scrapperGui.MainPane;
import scrapperIO.CsvReaderWriter;
import scrapperIO.XmlReaderWriter;
import scrapperModel.DataPaneSelection;
import scrapperModel.SettingsPaneSelection;
import scrapperUtil.FileNameGenerator;
import scrapperIO.ObjectToJsonWriter;

@Getter
@Setter
public class MainPaneHandler implements EventHandler<ActionEvent> {

	private MainPane mainPane;
	
	private Scrapper scrapper;
	
//	private Thread thread;
	
//	private XmlReaderWriter xmlReaderWriter;
	
	public MainPaneHandler(MainPane mainPane) {
		
		this.mainPane = mainPane;
		
	}
 	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == mainPane.getCsvUrlsListButton()) {
			
			csvUrlListButtonHandler();
			
		}
		
		if(event.getSource() == mainPane.getStartButton()) {
			
			startHandler();
			
		}
		
		if(event.getSource() == mainPane.getStopButton()) {
			
			stopHandler();			
			
		}
		
		if(event.getSource() == mainPane.getPauseResumeButton()) {
			
			pauseResumeHandler();
			
		}
		
	}
	
	public void csvUrlListButtonHandler() {
		
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		fileChooser.getExtensionFilters().add((new ExtensionFilter("Csv files", "*.csv")));
		
		File file = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
		
		if(file != null) {
			
			mainPane.getCsvUrlsListField().setText(file.getAbsolutePath());
			
		}
		
	}
	
	public void startHandler() {
		
		if (scrapper != null) {

			if (scrapper.isRunning()) {

				scrapper.setRunning(false);

			}

		}

		if (scrapper == null) {

			scrapper = new ScrapperImpl();

		}

		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (scrapper != null) {

					if (scrapper.isRunning() == false) {

						getSelectedSaveDataPath();
						
						applySettings();
						
						Query query = buildQuery(mainPane);
						
						updateProgress(scrapper);
						
						scrapper.setQuery(query);

						scrapper.start();
						
						query = null;
						
						scrapper = null;
						
													
					}
					
				}
				
			}
			
		});

		thread.start();
				
	}
	
	public void stopHandler() {
		
		if (scrapper != null) {

			if (scrapper.isRunning()) {

				scrapper.stop();

			}

		}
		
	}
	
	public void pauseResumeHandler() {
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (scrapper != null) {

					if (scrapper.isRunning()) {

						if (mainPane.getPauseResumeButton().getText().equals("Pause")) {

							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									mainPane.getPauseResumeButton().setText("Resume");

								}

							});

							scrapper.pause();

						}

						if (mainPane.getPauseResumeButton().getText().equals("Resume")) {

							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									mainPane.getPauseResumeButton().setText("Pause");

								}

							});

							scrapper.resume();

						}

					}

				}

			}

		});

		thread.start();
		
	}
	
	public void applySettings() {
		
		XmlReaderWriter xmlReaderWriter = new XmlReaderWriter();

		SettingsPaneSelection settingsPaneSelection = xmlReaderWriter.fromXml(new SettingsPaneSelection(),
				"settingsPaneSelection.xml");

		if (settingsPaneSelection != null) {

			scrapper.setDelayInMs(Integer.valueOf(settingsPaneSelection.getRequestDelay()));

			if (settingsPaneSelection.isUseProxy()) {

				scrapper.setNumberOfThreads(
						Integer.valueOf(settingsPaneSelection.getNumberOfThreads()));
				
				CsvReaderWriter CsvReaderWriter = new CsvReaderWriter();
				
				scrapper.setProxies(CsvReaderWriter.readFromCsvByLine(Paths.get(settingsPaneSelection.getLoadProxyFromCsv())));

			}

		}

		if (settingsPaneSelection == null) {

			scrapper.setDelayInMs(1000);

		}
		
	}
	
	public Query buildQuery(MainPane mainPane) {
		
		List<String[]> links = null;
		
		Query query = new QueryImpl();
		
		if(mainPane.getCsvUrlsListField().getText() != null) {
			
			CsvReaderWriter csvReaderWriter = new CsvReaderWriter();
			
			links = csvReaderWriter.readFromCsvByLine(Paths.get(mainPane.getCsvUrlsListField().getText()));
			
			query.setQuerys(links);
			
		}
		
		return query;
		
	}
	
	public void getSelectedSaveDataPath() {

		XmlReaderWriter xmlReaderWriter = new XmlReaderWriter();

		DataPaneSelection dataPaneSelection = new DataPaneSelection();

		dataPaneSelection = xmlReaderWriter.fromXml(dataPaneSelection, "dataPaneSelection.xml");

		if (dataPaneSelection.isSaveCsv()) {

			if (dataPaneSelection.isGenerateCsvFileName()) {

				FileNameGenerator fileNameGenerator = new FileNameGenerator();

				scrapper.setWriteToPath(Paths.get(dataPaneSelection.getCsvFolderDir() + File.separator
						+ fileNameGenerator.generateDateFileName() + ".csv"));

			}

			if (dataPaneSelection.isGenerateCsvFileName() == false && dataPaneSelection.getCsvFileName() != null
					&& !dataPaneSelection.getCsvFileName().isEmpty()) {

				scrapper.setWriteToPath(Paths.get(dataPaneSelection.getCsvFolderDir() + File.separator
						+ dataPaneSelection.getCsvFileName() + ".csv"));

			}
		}

		if (dataPaneSelection.isSaveSqliteDb()) {

			if (dataPaneSelection.isGenerateSqliteDbFileName()) {

				FileNameGenerator fileNameGenerator = new FileNameGenerator();

				SqliteDatabaseConnection sqliteDatabaseConnection = new SqliteDatabaseConnection(
						dataPaneSelection.getSqliteFolderDir() + File.separator,
						fileNameGenerator.generateDateFileName() + ".db");

				scrapper.setSqliteDatabaseConnection(sqliteDatabaseConnection);

			}

			if (dataPaneSelection.isGenerateSqliteDbFileName() == false
					&& dataPaneSelection.getSqliteDbFileName() != null
					&& !dataPaneSelection.getSqliteDbFileName().isEmpty()) {

				SqliteDatabaseConnection sqliteDatabaseConnection = new SqliteDatabaseConnection(
						dataPaneSelection.getSqliteFolderDir() + File.separator,
						dataPaneSelection.getSqliteDbFileName() + ".db");

				scrapper.setSqliteDatabaseConnection(sqliteDatabaseConnection);

			}

		}
		
		if (dataPaneSelection.isSaveJson()) {

			if (dataPaneSelection.isGenerateJsonFileName()) {

				FileNameGenerator fileNameGenerator = new FileNameGenerator();
				
				scrapper.setObjectToJsonWriter(new ObjectToJsonWriter());

				scrapper.setWriteToPath(Paths.get(dataPaneSelection.getJsonFolderDir() + File.separator
						+ fileNameGenerator.generateDateFileName() + ".json"));

			}

			if (dataPaneSelection.isGenerateJsonFileName() == false && dataPaneSelection.getJsonFileName() != null
					&& !dataPaneSelection.getJsonFileName().isEmpty()) {

				scrapper.setObjectToJsonWriter(new ObjectToJsonWriter());
				
				scrapper.setWriteToPath(Paths.get(dataPaneSelection.getJsonFolderDir() + File.separator
						+ dataPaneSelection.getJsonFileName() + ".json"));

			}
		}
		
		if (dataPaneSelection.isSaveSqliteDb() == false && dataPaneSelection.isSaveCsv() == false
				&& dataPaneSelection.isSaveJson() == false ) {

			FileNameGenerator fileNameGenerator = new FileNameGenerator();

			scrapper.setWriteToPath(Paths.get(System.getProperty("user.home") + File.separator
					+ fileNameGenerator.generateDateFileName() + ".csv"));

		}

	}
	
	private String elapsedTimeFrom(long millis) {
		
		return DurationFormatUtils.formatDuration(millis, "HH:mm:ss").replaceAll("-", "");
		
	}
	
	private String getStatus(Scrapper scrapper) {
		
		if(scrapper.isRunning() == false) {
			
			return "terminated";
			
		}
		
		if(scrapper.isRunning() && scrapper.isPause()) {
			
			return "paused";
			
		}
		
		if(scrapper.isRunning() || scrapper.isPause() == false) {
			
			return "running";
			
		}
		
		return null;
		
	}
	
	public void updateProgress(Scrapper scrapper) {

		Timer timer = new Timer();
		
		long start = System.currentTimeMillis();

		timer.schedule(new TimerTask() {
			
//			int count = 0;

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (scrapper.isRunning() == true) {
					
//					count = scrapper.getCount();
					
					mainPane.getStatusField().setText(getStatus(scrapper));
					
					mainPane.getTimeElapsedField().setText(elapsedTimeFrom((start - System.currentTimeMillis())));

					mainPane.getProgressField().setText("Pages checked: " + String.valueOf(scrapper.getCount()));
					
				}

				if (scrapper.isRunning() == false) {
					
//					count = scrapper.getCount();
					
					mainPane.getStatusField().setText(getStatus(scrapper));
					
					mainPane.getTimeElapsedField().setText(elapsedTimeFrom((start - System.currentTimeMillis())));

					mainPane.getProgressField().setText("Pages checked: " + String.valueOf(scrapper.getCount()));

					timer.cancel();

				}

			}

		}, 0, 500);

	}

}
