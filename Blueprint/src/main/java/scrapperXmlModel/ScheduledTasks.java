package scrapperXmlModel;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import scrapperModel.ScheduledTask;

@Setter
@Getter
@XmlRootElement(name = "ScheduledTasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduledTasks {
	
	public List<ScheduledTask> scheduledTaskList = new ArrayList<ScheduledTask>();
	
	public void addScheduledTaskToList(ScheduledTask scheduledTask) {
		
		scheduledTaskList.add(scheduledTask);
		
	}

}
