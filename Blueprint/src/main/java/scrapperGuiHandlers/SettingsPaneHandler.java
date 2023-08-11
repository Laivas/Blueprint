package scrapperGuiHandlers;

import lombok.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import scrapperGui.SettingsPane;
import scrapperIO.CsvReaderWriter;
import scrapperIO.XmlReaderWriter;
import scrapperModel.SettingsPaneSelection;
import scrapperProxy.ProxyUtil;


@Setter
@Getter
public class SettingsPaneHandler implements EventHandler<ActionEvent> {
	
	private SettingsPane settingsPane;
	
	private ProxyUtil proxyUtil;
	
	private SettingsPaneSelection settingsPaneSelection;
	
	
	public SettingsPaneHandler(SettingsPane settingsPane) {
		
		this.settingsPane = settingsPane;
		
	}


	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
//		if(event.getSource() == settingsPane.getLoadProxyButton()) {
//			
//			if(!settingsPane.getProxySellerApiTextField().getText().isEmpty()) {
//				
//				proxyUtil = getProxyUtil();
//				
//				proxyUtil.fetchProxy(settingsPane.getProxySellerApiTextField().getText());
//				
//				if(proxyUtil.getAllProxies() != null) {
//					
//					settingsPane.getAvailableProxyTextField().setText(String.valueOf(proxyUtil.getAllProxies().size()));
//					
//					settingsPane.getNumberOfThreadsComboBox().getItems().addAll(valuesAsList(proxyUtil.getAllProxies().size()));
//					
//					settingsPane.getRandomProxyCheckbox().setDisable(false);
//					
//					settingsPane.getUseProxyCheckbox().setDisable(false);
//					
//				}
//				
//			}
//			
//		} 
		
		if(event.getSource() == settingsPane.getLoadProxyFromCsvButton()) {
			
			loadProxyFromCsvHandler();
			
		}
		
		if(event.getSource() == settingsPane.getLoadProxyButton()) {
			
			loadProxyHandler();
			
		}
		
		if(event.getSource() == settingsPane.getSaveSettingsButton()) {
			
			saveSettings();
			
		}
		

	}
	
	private void loadProxyFromCsvHandler() {
		
		FileChooser fileChooser = new FileChooser();

		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Csv Files", "*.csv"));

		File file = fileChooser.showOpenDialog(settingsPane.getScene().getWindow());
		
//		File file = directoryChooser.showDialog(settingsPane.getScene().getWindow());
		
		if (file != null) {
			
			settingsPane.getLoadProxyFromCsvTextField().setText(file.getAbsolutePath());
			
		}
		
	}
	
	private void loadProxyHandler() {
		
		if(settingsPane.getLoadProxyFromCsvTextField().getText() != null) {
			
			if(!settingsPane.getLoadProxyFromCsvTextField().getText().isEmpty()) {
			
			CsvReaderWriter csvReaderWriter = new CsvReaderWriter();
			
			List<String[]> proxys = csvReaderWriter.readFromCsvByLine(Paths.get(settingsPane.getLoadProxyFromCsvTextField().getText()));
			
			settingsPane.getAvailableProxyTextField().setText(String.valueOf(proxys.size()));
			
			settingsPane.getNumberOfThreadsComboBox().getItems().clear();
			
			settingsPane.getNumberOfThreadsComboBox().getItems().addAll(valuesAsList(Integer.valueOf(proxys.size())));
			
			settingsPane.getNumberOfThreadsComboBox().setDisable(false);
			
			if(proxys.size() > 0) {
				
				settingsPane.getNumberOfThreadsComboBox().setValue("1");
				
			}
			
			settingsPane.getUseProxyCheckbox().setDisable(false);
			
			}
			
		}
		
	}
	
	public void loadSettingsPaneSelectionFromXml() {
		
		XmlReaderWriter xmlReaderWriter = new XmlReaderWriter();
		
		settingsPaneSelection = new SettingsPaneSelection();
		
		if(xmlReaderWriter.fromXml(settingsPaneSelection, "settingsPaneSelection.xml") != null) {
			
			settingsPaneSelection = xmlReaderWriter.fromXml(settingsPaneSelection, "settingsPaneSelection.xml");
			
			settingsPane.getLoadProxyFromCsvTextField().setText(settingsPaneSelection.getLoadProxyFromCsv());
			
//			settingsPane.getProxySellerApiTextField().setText(settingsPaneSelection.getProxySellerApiKey());
			
			settingsPane.getAvailableProxyTextField().setText(settingsPaneSelection.getAvailableProxyCount());
			
			if(!settingsPaneSelection.getAvailableProxyCount().matches("")) {
			
			settingsPane.getNumberOfThreadsComboBox().getItems().addAll(valuesAsList(Integer.valueOf(settingsPaneSelection.getAvailableProxyCount())));
			}
			
			settingsPane.getNumberOfThreadsComboBox().setValue(settingsPaneSelection.getNumberOfThreads());
			
			if(settingsPaneSelection.getNumberOfThreads() != null) {
			
			if(!settingsPaneSelection.getNumberOfThreads().matches("")) {
			
			if(Integer.valueOf(settingsPaneSelection.getNumberOfThreads()) > 0) {
				
				settingsPane.getNumberOfThreadsComboBox().setDisable(false);
				
			}
			
			}
			
			}
			
			settingsPane.getRequestDelayComboBox().setValue(settingsPaneSelection.getRequestDelay());
			
			settingsPane.getUseProxyCheckbox().setSelected(settingsPaneSelection.isUseProxy());
			
			if(settingsPaneSelection.isUseProxy()) {
				
				settingsPane.getUseProxyCheckbox().setDisable(false);
				
			}
			
		}
		
	}
	
	public void saveSettings() {
		
		XmlReaderWriter xmlReaderWriter = new XmlReaderWriter();
		
		SettingsPaneSelection settingsPaneSelection = new SettingsPaneSelection();
		
		settingsPaneSelection.setLoadProxyFromCsv(settingsPane.getLoadProxyFromCsvTextField().getText());
		
//		settingsPaneSelection.setProxySellerApiKey(settingsPane.getProxySellerApiTextField().getText());
		
		settingsPaneSelection.setAvailableProxyCount(String.valueOf(settingsPane.getAvailableProxyTextField().getText()));
		
		settingsPaneSelection.setNumberOfThreads(settingsPane.getNumberOfThreadsComboBox().getSelectionModel().getSelectedItem());
		
		settingsPaneSelection.setRequestDelay(settingsPane.getRequestDelayComboBox().getSelectionModel().getSelectedItem());
		
		settingsPaneSelection.setUseProxy(settingsPane.getUseProxyCheckbox().isSelected());
		
		xmlReaderWriter.toXml(settingsPaneSelection, "settingsPaneSelection.xml");
		
	}
	
	private List<String> valuesAsList(int count) {
		
		List<String> listToReturn = new ArrayList<String>();
		
		for(int i = 1; i <= count; i++) {
			
			listToReturn.add(String.valueOf(i));
			
		}
		
		return listToReturn;
		
	}

}
