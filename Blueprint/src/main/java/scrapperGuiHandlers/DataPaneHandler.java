package scrapperGuiHandlers;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import scrapperGui.DataPane;
import scrapperIO.XmlReaderWriter;
import scrapperModel.DataPaneSelection;

public class DataPaneHandler implements EventHandler<ActionEvent> {

	private DataPane dataPane;

	private DataPaneSelection dataPaneSelection;

	public DataPaneHandler(DataPane dataPane) {

		this.dataPane = dataPane;

	}

	public void loadDataPaneSelectionFromXml() {

		dataPaneSelection = new DataPaneSelection();

		XmlReaderWriter xmlReaderWriter = new XmlReaderWriter();
		
		if(xmlReaderWriter.fromXml(dataPaneSelection, "dataPaneSelection.xml") != null) {

		dataPaneSelection = xmlReaderWriter.fromXml(dataPaneSelection, "dataPaneSelection.xml");

		dataPane.getCsvFileNameField().setText(dataPaneSelection.getCsvFileName());

		dataPane.getWriteToCsvField().setText(dataPaneSelection.getCsvFolderDir());

		dataPane.getGenerateCsvFileNameCheckbox().setSelected(dataPaneSelection.isGenerateCsvFileName());

		dataPane.getGenerateSqlDbFileNameCheckbox().setSelected(dataPaneSelection.isGenerateSqliteDbFileName());

		dataPane.getCsvFilesRadioButton().setSelected(dataPaneSelection.isSaveCsv());

		dataPane.getSqLiteRadioButton().setSelected(dataPaneSelection.isSaveSqliteDb());

		dataPane.getSqliteDatabaseNameField().setText(dataPaneSelection.getSqliteDbFileName());

		dataPane.getSqliteDatabaseLocationField().setText(dataPaneSelection.getSqliteFolderDir());

		uiSelectionSwitcher();
		
		}

	}


	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub

		if (event.getSource() == dataPane.getCsvFilesRadioButton()
				|| event.getSource() == dataPane.getGenerateCsvFileNameCheckbox()) {

			uiSelectionSwitcher();

		}

		if (event.getSource() == dataPane.getSqLiteRadioButton()
				|| event.getSource() == dataPane.getGenerateSqlDbFileNameCheckbox()) {

			uiSelectionSwitcher();

		}

		if (event.getSource() == dataPane.getWriteToCsvButton()
				|| event.getSource() == dataPane.getSqliteDatabaseLocationButton()) {

			directoryChooserHandler(event);

		}

		if (event.getSource() == dataPane.getSaveButton()) {

			saveButtonHandler();

		}

	}
	
	private void directoryChooserHandler(ActionEvent event) {
		
		DirectoryChooser directoryChooser = new DirectoryChooser();

//		directoryChooser.getExtensionFilters().add(new ExtensionFilter("Csv Files", "*.csv"));

		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

		File file = directoryChooser.showDialog(dataPane.getScene().getWindow());

		if (file != null) {

			if (event.getSource() == dataPane.getWriteToCsvButton()) {

				dataPane.getWriteToCsvField().setText(file.getAbsolutePath());

			}

			if (event.getSource() == dataPane.getSqliteDatabaseLocationButton()) {

				dataPane.getSqliteDatabaseLocationField().setText(file.getAbsolutePath());

			}

		}
		
	}
	
	private void saveButtonHandler() {
		
		XmlReaderWriter xmlReaderWriter = new XmlReaderWriter();

		dataPaneSelection = new DataPaneSelection();

		dataPaneSelection.setCsvFileName(dataPane.getCsvFileNameField().getText());

		dataPaneSelection.setCsvFolderDir(dataPane.getWriteToCsvField().getText());

		dataPaneSelection.setGenerateCsvFileName(dataPane.getGenerateCsvFileNameCheckbox().isSelected());

		dataPaneSelection.setGenerateSqliteDbFileName(dataPane.getGenerateSqlDbFileNameCheckbox().isSelected());

		dataPaneSelection.setSaveCsv(dataPane.getCsvFilesRadioButton().isSelected());

		dataPaneSelection.setSaveSqliteDb(dataPane.getSqLiteRadioButton().isSelected());

		dataPaneSelection.setSqliteDbFileName(dataPane.getSqliteDatabaseNameField().getText());

		dataPaneSelection.setSqliteFolderDir(dataPane.getSqliteDatabaseLocationField().getText());

		xmlReaderWriter.toXml(dataPaneSelection, "dataPaneSelection.xml");	
		
	}
	
	public void uiSelectionSwitcher() {

		if (dataPane.getCsvFilesRadioButton().isSelected()) {

			dataPane.getSqliteDatabaseLocationButton().setDisable(true);

			dataPane.getSqliteDatabaseNameField().setDisable(true);

			dataPane.getGenerateSqlDbFileNameCheckbox().setDisable(true);

			dataPane.getWriteToCsvButton().setDisable(false);

			dataPane.getGenerateCsvFileNameCheckbox().setDisable(false);

			if (dataPane.getGenerateCsvFileNameCheckbox().isSelected()) {

				dataPane.getCsvFileNameField().setDisable(true);

			} else {

				dataPane.getCsvFileNameField().setDisable(false);

			}

		}

		if (dataPane.getSqLiteRadioButton().isSelected()) {

			dataPane.getSqliteDatabaseLocationButton().setDisable(false);

			dataPane.getWriteToCsvButton().setDisable(true);

			dataPane.getWriteToCsvField().setDisable(true);

			dataPane.getCsvFileNameField().setDisable(true);

			dataPane.getGenerateCsvFileNameCheckbox().setDisable(true);

			dataPane.getGenerateSqlDbFileNameCheckbox().setDisable(false);

			if (dataPane.getGenerateSqlDbFileNameCheckbox().isSelected()) {

				dataPane.getSqliteDatabaseNameField().setDisable(true);

			} else {

				dataPane.getSqliteDatabaseNameField().setDisable(false);

			}

		}

	}

}
