package scrapperModel;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@XmlRootElement(name = "SettingsPaneSelection")
@XmlAccessorType(XmlAccessType.FIELD)
public class SettingsPaneSelection {

	private String loadProxyFromCsv;
	
	private String proxySellerApiKey;
	
	private String availableProxyCount;
	
	private String numberOfThreads;
	
	private String requestDelay;
	
	private boolean useProxy;
	
}
