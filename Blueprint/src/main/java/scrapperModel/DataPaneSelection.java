package scrapperModel;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@XmlRootElement(name = "DataPaneSelection")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataPaneSelection {
	
	private boolean saveCsv;
	
	private boolean saveSqliteDb;
	
	private boolean generateCsvFileName;
	
	private boolean generateSqliteDbFileName;
	
	private String csvFileName;
	
	private String csvFolderDir;
	
	private String sqliteDbFileName;
	
	private String sqliteFolderDir;

}
