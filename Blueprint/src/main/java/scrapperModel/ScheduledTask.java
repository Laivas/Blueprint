package scrapperModel;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@XmlRootElement(name = "ScheduledTask")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduledTask {
	
	public String taskName;

	public String tableDate;
	
	public String date;
	
	public String time;
	
	public String day;
	
	public String type;
	
	public String readFrom;
	
	public String writeTo;
	
	public SimpleBooleanProperty selection;

	
	
}
