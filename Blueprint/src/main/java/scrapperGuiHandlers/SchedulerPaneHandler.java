package scrapperGuiHandlers;


import java.io.File;
import java.util.Iterator;

import org.quartz.Job;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import lombok.Getter;
import lombok.Setter;
import scrapperGui.SchedulerPane;
import scrapperIO.XmlReaderWriter;
import scrapperModel.ScheduledTask;
import scrapperScheduler.QuartzScheduler;
import scrapperScheduler.WindowsScheduleBuilder;
import scrapperScheduler.Workable;
import scrapperXmlModel.ScheduledTasks;

@Setter
@Getter
public class SchedulerPaneHandler implements EventHandler<ActionEvent> {

	private SchedulerPane schedulerPane;

	private ScheduledTask scheduledTask;
	
	private WindowsScheduleBuilder windowsScheduleBuilder;
	
	private QuartzScheduler quartzScheduler;
	
	private Class<? extends Job> jobImpl;
	
	private Job jobImplementation;
	
	private Workable workable;

	public SchedulerPaneHandler(SchedulerPane schedulerPane) {

		this.schedulerPane = schedulerPane;

	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub

		if (event.getSource() == schedulerPane.getAddTaskbutton()) {

			scheduledTask = new ScheduledTask();
			
			windowsScheduleBuilder = new WindowsScheduleBuilder();

			if (schedulerPane.getDailyRadioButton().isSelected() && validateScheduledTaskFields()) {

				String type = schedulerPane.getDailyRadioButton().getText();

				if (!schedulerPane.getTimePicker().getTime().toString().isBlank()) {
//					if (!schedulerPane.getTimePicker().getValue().toString().isBlank()) {
					
					String date = schedulerPane.getTimePicker().getTime().toString();
//					String date = schedulerPane.getTimePicker().getValue().toString();
					
					String taskName = schedulerPane.getTaskNameTextField().getText();
					
					if(!entryExists(taskName)) {

					setScheduledTaskFields(date, type);
					
					windowsScheduleBuilder.createScheduledTaskDaily(scheduledTask);
					
					schedulerPane.getTableView().getItems().add(scheduledTask);
					
					}

				}

			}

			if (schedulerPane.getWeeklyRadioButton().isSelected() && validateScheduledTaskFields()) {

				String type = schedulerPane.getWeeklyRadioButton().getText();

				if (!schedulerPane.getDaysOfTheWeekComboBox().getSelectionModel().getSelectedItem().isBlank()
						&& !schedulerPane.getTimePicker().getTime().toString().isBlank()) {
//					if (!schedulerPane.getDaysOfTheWeekComboBox().getSelectionModel().getSelectedItem().isBlank()
//							&& !schedulerPane.getTimePicker().getValue().toString().isBlank()) {

					String date = schedulerPane.getDaysOfTheWeekComboBox().getSelectionModel().getSelectedItem() + " "
							+ schedulerPane.getTimePicker().getTime().toString();
//					String date = schedulerPane.getDaysOfTheWeekComboBox().getSelectionModel().getSelectedItem() + " "
//							+ schedulerPane.getTimePicker().getValue().toString();

					String taskName = schedulerPane.getTaskNameTextField().getText();
					
					if(!entryExists(taskName)) {
					
					setScheduledTaskFields(date, type);
					
					windowsScheduleBuilder.createScheduledTaskWeekly(scheduledTask);
					
					schedulerPane.getTableView().getItems().add(scheduledTask);
					
					}

				}

			}

			if (schedulerPane.getOnceRadioButton().isSelected() && validateScheduledTaskFields()) {

				String type = schedulerPane.getOnceRadioButton().getText();

				if (!schedulerPane.getCalendarPicker().getValue().toString().isBlank()
						&& !schedulerPane.getTimePicker().getTime().toString().isBlank()) {
//					if (!schedulerPane.getCalendarPicker().getValue().toString().isBlank()
//							&& !schedulerPane.getTimePicker().getValue().toString().isBlank()) {

					String date = schedulerPane.getCalendarPicker().getValue().toString() + " "
							+ schedulerPane.getTimePicker().getTime().toString();
//					String date = schedulerPane.getCalendarPicker().getValue().toString() + " "
//							+ schedulerPane.getTimePicker().getValue().toString();

					String taskName = schedulerPane.getTaskNameTextField().getText();
					
					if(!entryExists(taskName)) {

					setScheduledTaskFields(date, type);
					
					windowsScheduleBuilder.createScheduledTaskOnce(scheduledTask);
					
					schedulerPane.getTableView().getItems().add(scheduledTask);

					}
					
				}

//				schedulerPane.getCalendarPicker().setDisable(false);

			}
			

		}

		if (event.getSource() == schedulerPane.getDailyRadioButton()) {

			schedulerPane.getDaysOfTheWeekComboBox().setDisable(true);

			schedulerPane.getCalendarPicker().setDisable(true);

			schedulerPane.getTimePicker().setDisable(false);

		}

		if (event.getSource() == schedulerPane.getWeeklyRadioButton()) {

			schedulerPane.getDaysOfTheWeekComboBox().setDisable(false);

			schedulerPane.getTimePicker().setDisable(false);

			schedulerPane.getCalendarPicker().setDisable(true);

		}

		if (event.getSource() == schedulerPane.getOnceRadioButton()) {
			
			schedulerPane.getTimePicker().setDisable(false);

			schedulerPane.getDaysOfTheWeekComboBox().setDisable(true);

			schedulerPane.getCalendarPicker().setDisable(false);

		}

		if (event.getSource() == schedulerPane.getReadFromCsvButton()
				|| event.getSource() == schedulerPane.getWriteToCsvButton()) {

			FileChooser fileChooser = new FileChooser();

			fileChooser.getExtensionFilters().add(new ExtensionFilter("Csv Files", "*.csv"));

			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

			File file = fileChooser.showOpenDialog(schedulerPane.getScene().getWindow());

			if (file != null) {

				if (event.getSource() == schedulerPane.getReadFromCsvButton()) {

					schedulerPane.getReadFromCsvField().setText(file.getAbsolutePath());

				}

				if (event.getSource() == schedulerPane.getWriteToCsvButton()) {

					schedulerPane.getWriteToCsvField().setText(file.getAbsolutePath());

				}

			}

		}
		
		if(event.getSource() == schedulerPane.getDeleteTaskbutton()) {

			if(windowsScheduleBuilder == null) {
				
				windowsScheduleBuilder = new WindowsScheduleBuilder();
				
			}
			
			for (Iterator<ScheduledTask> iterator = schedulerPane.getTableView().getItems().iterator(); iterator.hasNext();) {
				ScheduledTask scheduledTask = iterator.next();
			    if (scheduledTask.getSelection().get()) {
			    	
			        iterator.remove();
			        
			        windowsScheduleBuilder.deleteScheduledTask(scheduledTask);
			        
			    }
			}
			
		}
		

	}
	
	public boolean entryExists(String taskName) {
		
		for(ScheduledTask scheduledTask : schedulerPane.getTableView().getItems()) {
			
			if(scheduledTask.getTaskName().equals(taskName)) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public void loadScheduledTasksFromXmlToTable() {
		
		ScheduledTasks scheduledTasks = new ScheduledTasks();
		
		XmlReaderWriter xmlReaderWriter = new XmlReaderWriter();
		
		scheduledTasks = xmlReaderWriter.fromXml(scheduledTasks, "scheduledTasks.xml");

		if(scheduledTasks != null) {
		
		schedulerPane.getTableView().getItems().addAll(scheduledTasks.getScheduledTaskList());

		}
		
	}
	
	public void writeScheduledTasksFromTableToXml() {
		
		ScheduledTasks scheduledTasks = new ScheduledTasks();
		
		XmlReaderWriter xmlReaderWriter = new XmlReaderWriter();
		
		for(ScheduledTask scheduledTask : schedulerPane.getTableView().getItems()) {
			
			scheduledTasks.addScheduledTaskToList(scheduledTask);
			
		}
		
		xmlReaderWriter = new XmlReaderWriter();
	
		xmlReaderWriter.toXml(scheduledTasks, "scheduledTasks.xml");
		
	}

	public boolean validateScheduledTaskFields() {

		if (!schedulerPane.getTaskNameTextField().getText().isBlank()
				&& !schedulerPane.getWriteToCsvField().getText().isBlank()
				&& !schedulerPane.getReadFromCsvField().getText().isBlank()) {

			return true;

		}

		return false;

	}
	
	public void quartzSchedule() {
		
		quartzScheduler = new QuartzScheduler();
		
		for(ScheduledTask scheduledTask : schedulerPane.getTableView().getItems()) {
			
			quartzScheduler.setWorkable(workable);
			
			quartzScheduler.setJobImplementation(jobImplementation);
			
			quartzScheduler.scheduler(scheduledTask);
			
		}
		
	} 

	public void setScheduledTaskFields(String date, String type) {

		if (scheduledTask != null) {

			scheduledTask.setTableDate(date);
			
			if(schedulerPane.getCalendarPicker().getValue() != null) {
			
			scheduledTask.setDate(schedulerPane.getCalendarPicker().getValue().toString());
			
			}
			
			if(schedulerPane.getDaysOfTheWeekComboBox().getSelectionModel().getSelectedItem() != null) {

			scheduledTask.setDay(schedulerPane.getDaysOfTheWeekComboBox().getSelectionModel().getSelectedItem());
			
			}
			
			if(schedulerPane.getTimePicker().getTime() != null) {
			
			scheduledTask.setTime(schedulerPane.getTimePicker().getTime().toString());
			
			}
//			if(schedulerPane.getTimePicker().getValue() != null) {
//				
//				scheduledTask.setTime(schedulerPane.getTimePicker().getValue().toString());
//				
//			}

			scheduledTask.setTaskName(schedulerPane.getTaskNameTextField().getText());

			scheduledTask.setReadFrom(schedulerPane.getReadFromCsvField().getText());

			scheduledTask.setWriteTo(schedulerPane.getWriteToCsvField().getText());

			scheduledTask.setType(type);
			
			scheduledTask.setSelection(new SimpleBooleanProperty(false));
			
		}

	}


}
