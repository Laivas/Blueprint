package scrapperGuiHandlers;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import scrapperGui.DataPane;
import scrapperModel.DataPaneSelection;
import scrapperIO.XmlReaderWriter;

public class DataPaneHandler implements EventHandler<ActionEvent> {

	private DataPane dataPane;

	private DataPaneSelection dataPaneSelection;

	public DataPaneHandler(DataPane dataPane) {

		this.dataPane = dataPane;

	}

	public void loadDataPaneSelectionFromXml() {

		dataPaneSelection = new DataPaneSelection();

		XmlReaderWriter xmlReaderWriter = new XmlReaderWriter();

		if (xmlReaderWriter.fromXml(dataPaneSelection, "dataPaneSelection.xml") != null) {

			dataPaneSelection = xmlReaderWriter.fromXml(dataPaneSelection, "dataPaneSelection.xml");

			dataPane.getCsvFileNameField().setText(dataPaneSelection.getCsvFileName());

			dataPane.getWriteToCsvField().setText(dataPaneSelection.getCsvFolderDir());

			dataPane.getGenerateCsvFileNameCheckbox().setSelected(dataPaneSelection.isGenerateCsvFileName());

			dataPane.getGenerateSqlDbFileNameCheckbox().setSelected(dataPaneSelection.isGenerateSqliteDbFileName());

			dataPane.getCsvFilesRadioButton().setSelected(dataPaneSelection.isSaveCsv());

			dataPane.getSqLiteRadioButton().setSelected(dataPaneSelection.isSaveSqliteDb());

			dataPane.getSqliteDatabaseNameField().setText(dataPaneSelection.getSqliteDbFileName());

			dataPane.getSqliteDatabaseLocationField().setText(dataPaneSelection.getSqliteFolderDir());
			
			
			dataPane.getJsonFileNameField().setText(dataPaneSelection.getJsonFileName());

			dataPane.getWriteToJsonField().setText(dataPaneSelection.getJsonFolderDir());

			dataPane.getGenerateJsonFileNameCheckbox().setSelected(dataPaneSelection.isGenerateJsonFileName());
			
			dataPane.getJsonFileRadioButton().setSelected(dataPaneSelection.isSaveJson());

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
		
		if (event.getSource() == dataPane.getJsonFileRadioButton()
				|| event.getSource() == dataPane.getGenerateJsonFileNameCheckbox()) {
			
			uiSelectionSwitcher();
			
		}

		if (event.getSource() == dataPane.getWriteToCsvButton()
				|| event.getSource() == dataPane.getSqliteDatabaseLocationButton()
				|| event.getSource() == dataPane.getWriteToJsonButton()) {

			DirectoryChooser directoryChooser = new DirectoryChooser();

			directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

			File file = directoryChooser.showDialog(dataPane.getScene().getWindow());

			if (file != null) {

				if (event.getSource() == dataPane.getWriteToCsvButton()) {

					dataPane.getWriteToCsvField().setText(file.getAbsolutePath());

				}

				if (event.getSource() == dataPane.getSqliteDatabaseLocationButton()) {

					dataPane.getSqliteDatabaseLocationField().setText(file.getAbsolutePath());

				}
				
				if (event.getSource() == dataPane.getWriteToJsonButton()) {
					
					dataPane.getWriteToJsonField().setText(file.getAbsolutePath());
					
				}

			}

		}

		if (event.getSource() == dataPane.getSaveButton()) {

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
			
			
			dataPaneSelection.setSaveJson(dataPane.getJsonFileRadioButton().isSelected());
			
			dataPaneSelection.setJsonFileName(dataPane.getJsonFileNameField().getText());

			dataPaneSelection.setJsonFolderDir(dataPane.getWriteToJsonField().getText());

			dataPaneSelection.setGenerateJsonFileName(dataPane.getGenerateJsonFileNameCheckbox().isSelected());
			

			xmlReaderWriter.toXml(dataPaneSelection, "dataPaneSelection.xml");

		}

	}

	public void uiSelectionSwitcher() {

		if (dataPane.getCsvFilesRadioButton().isSelected()) {

			dataPane.getSqliteDatabaseLocationButton().setDisable(true);

			dataPane.getSqliteDatabaseNameField().setDisable(true);

			dataPane.getGenerateSqlDbFileNameCheckbox().setDisable(true);
			
			
			dataPane.getWriteToJsonButton().setDisable(true);
			
			dataPane.getJsonFileNameField().setDisable(true);
			
			dataPane.getGenerateJsonFileNameCheckbox().setDisable(true);
			

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
			
			dataPane.getCsvFileNameField().setDisable(true);
			
			dataPane.getGenerateCsvFileNameCheckbox().setDisable(true);
			
			
			dataPane.getWriteToJsonButton().setDisable(true);
			
			dataPane.getJsonFileNameField().setDisable(true);
			
			dataPane.getGenerateJsonFileNameCheckbox().setDisable(true);
			

			dataPane.getGenerateSqlDbFileNameCheckbox().setDisable(false);

			if (dataPane.getGenerateSqlDbFileNameCheckbox().isSelected()) {

				dataPane.getSqliteDatabaseNameField().setDisable(true);

			} else {

				dataPane.getSqliteDatabaseNameField().setDisable(false);

			}
			
		}
		
		if (dataPane.getJsonFileRadioButton().isSelected()) {

			dataPane.getSqliteDatabaseLocationButton().setDisable(true);

			dataPane.getSqliteDatabaseNameField().setDisable(true);

			dataPane.getGenerateSqlDbFileNameCheckbox().setDisable(true);
			
			
			dataPane.getWriteToJsonButton().setDisable(false);
			
			dataPane.getJsonFileNameField().setDisable(false);
			
			dataPane.getGenerateJsonFileNameCheckbox().setDisable(false);
			

			dataPane.getWriteToCsvButton().setDisable(true);

			dataPane.getWriteToCsvField().setDisable(true);

			dataPane.getCsvFileNameField().setDisable(true);
			
			dataPane.getCsvFileNameField().setDisable(true);
			
			dataPane.getGenerateCsvFileNameCheckbox().setDisable(true);

			if (dataPane.getGenerateJsonFileNameCheckbox().isSelected()) {

				dataPane.getJsonFileNameField().setDisable(true);

			} else {

				dataPane.getJsonFileNameField().setDisable(false);

			}

			
		}

	}

}
