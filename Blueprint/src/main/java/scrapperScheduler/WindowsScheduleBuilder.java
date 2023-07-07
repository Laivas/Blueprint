package scrapperScheduler;

import java.io.IOException;

import scrapperModel.ScheduledTask;
import scrapperUtil.DateUtil;

public class WindowsScheduleBuilder {
	
	private String userDir = System.getProperty("user.dir").replaceAll("\\\\", "/");
	
	private String scrapperLocation = "/Blueprint.jar";
	
	public void createScheduledTaskDaily(ScheduledTask scheduledTask) {

		String[] command = {"schtasks.exe", "/Create", "/sc", "DAILY", "/st", scheduledTask.getTime(), "/tn",
				"ScrapperTask-" + scheduledTask.getTaskName(), "/tr", userDir + scrapperLocation};
		
		System.out.println(createProcess(command));

	}
	

	public void createScheduledTaskWeekly(ScheduledTask scheduledTask) {
		
		String[] command = {"schtasks.exe", "/Create", "/sc", "WEEKLY", "/d", dayOfTheWeek(scheduledTask), "/st",
				scheduledTask.getTime(), "/tn", "ScrapperTask-" + scheduledTask.getTaskName(), "/tr", userDir + scrapperLocation};
		
		System.out.println(createProcess(command));

	}

	public void createScheduledTaskOnce(ScheduledTask scheduledTask) {
		System.out.println(scheduledTask.getDate());
		DateUtil dateUtil = new DateUtil();
		
		String[] command = {"schtasks.exe", "/Create", "/sc", "ONCE", "/sd", dateUtil.dateFormatChanger(scheduledTask.getDate()), "/st",
				scheduledTask.getTime(), "/tn", "ScrapperTask-" + scheduledTask.getTaskName(), "/tr", userDir + scrapperLocation};
		
		System.out.println(createProcess(command));

	}
		
	public void deleteScheduledTask(ScheduledTask scheduledTask) {

		String[] command = {"schtasks.exe", "/delete", "/tn", "ScrapperTask-" + scheduledTask.getTaskName(), "/f" };
		
		System.out.println(createProcess(command));

	}
	
	protected String createProcess(String[] command) {
		
		ProcessBuilder builder = new ProcessBuilder(command);
		
		String exitValue = "";
		
		try {
			Process process = builder.start();

			process.waitFor();
			
			exitValue = String.valueOf(process.exitValue());
		
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exitValue;

	}
	
	public String dayOfTheWeek(ScheduledTask scheduledTask) {
		
		DateUtil dateUtil = new DateUtil();
		
		return dateUtil.getDayOfTheWeek(scheduledTask.getDay());
		
	}

}
