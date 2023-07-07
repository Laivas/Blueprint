package scrapperScheduler;

import java.util.Calendar;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import lombok.Getter;
import lombok.Setter;
import scrapperModel.ScheduledTask;
import scrapperUtil.DateUtil;

@Setter
@Getter
public class QuartzScheduler {
	
	private Date date;
	
	private DateUtil dateUtil;

	private SchedulerFactory schedulerFactory;

	private Calendar calendar;

	private Scheduler scheduler;

	private JobDetail jobDetail;

	private SimpleTrigger trigger;
	
	private ScheduledTask scheduledTask;
	
	private Class<? extends Job> jobImpl;
	
	private Job jobImplementation;
	
	private Workable workable;
	
	public void scheduler(ScheduledTask scheduledTask) {
		
		String time = scheduledTask.getTime();
		
		String day = scheduledTask.getDay();
		
		String date = scheduledTask.getDate();
		
		if(scheduledTask.getType().equals("Daily")) {
			
			scheduleDaily(scheduledTask.getTaskName(), scheduledTask.getType(), jobImplementation, time);
			
		}
		
		if(scheduledTask.getType().equals("Weekly")) {
			
			scheduleWeekly(scheduledTask.getTaskName(), scheduledTask.getType(), jobImplementation, day, time);
			
		}
		
		if(scheduledTask.getType().equals("Once")) {
			
			scheduleOnce(scheduledTask.getTaskName(), scheduledTask.getType(), jobImplementation, date, time);
			
		}
		
	}
	

	public void scheduleDaily(String name, String group, Job jobImpl, String time) {

		if(dateUtil == null) {
			
			dateUtil = new DateUtil();
			
		}

		int hour = Integer.valueOf(time.split(":")[0].strip());
		
		int min = Integer.valueOf(dateUtil.addMinute(time.split(":")[1].strip()));
		
		try {
			
			schedulerFactory = new StdSchedulerFactory();

			scheduler = schedulerFactory.getScheduler();

			jobDetail = JobBuilder.newJob(jobImpl.getClass()).withIdentity(name, group).build();
			
			jobDetail.getJobDataMap().put(workable.getWorkableClassName(), workable.getWorkable());

			CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(name, group)
					.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(hour, min))
					.forJob(jobDetail).build();

			scheduler.scheduleJob(jobDetail, trigger);

			scheduler.start();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void scheduleWeekly(String name, String group, Job jobImpl, String weekDay, String time) {
		
		if(dateUtil == null) {
			
			dateUtil = new DateUtil();
			
		}

		int hour = Integer.valueOf(time.split(":")[0].strip());
		
		int min = Integer.valueOf(dateUtil.addMinute(time.split(":")[1].strip()));
		
		try {
			
			schedulerFactory = new StdSchedulerFactory();

			scheduler = schedulerFactory.getScheduler();

			jobDetail = JobBuilder.newJob(jobImpl.getClass()).withIdentity(name, group).build();
			
			jobDetail.getJobDataMap().put(workable.getWorkableClassName(), workable.getWorkable());
			
			CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(name, group)
					.withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(dayOftheWeek(weekDay), hour, min))
					.forJob(jobDetail).build();

			scheduler.scheduleJob(jobDetail, trigger);

			scheduler.start();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void scheduleOnce(String name, String group, Job jobImpl, String date, String time) {
		
		try {

			schedulerFactory = new StdSchedulerFactory();

			scheduler = schedulerFactory.getScheduler();

			jobDetail = JobBuilder.newJob(jobImpl.getClass()).withIdentity(name, group).build();

			jobDetail.getJobDataMap().put(workable.getWorkableClassName(), workable.getWorkable());
			
			trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity(name, group).startAt(dateUtility(date, time))
					.forJob(jobDetail).build();

			scheduler.scheduleJob(jobDetail, trigger);

			scheduler.start();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	private Date dateUtility(String date, String time) {
		
		dateUtil = new DateUtil();
		
		int hour = Integer.valueOf(time.split(":")[0].strip());
		
		int min = Integer.valueOf(dateUtil.addMinute(time.split(":")[1].strip()));
		
		Date startAtDate = dateUtil.stringToDate(date + " " + hour + ":" + String.valueOf(min));
		System.out.println(startAtDate.toString());
		return startAtDate;
		
	}
	
	private int dayOftheWeek(String day) {

		if (day.equalsIgnoreCase("SUNDAY")) {

			return 1;

		}
		if (day.equalsIgnoreCase("MONDAY")) {

			return 2;

		}
		if (day.equalsIgnoreCase("TUESDAY")) {

			return 3;

		}
		if (day.equalsIgnoreCase("WEDNESDAY")) {

			return 4;

		}
		if (day.equalsIgnoreCase("THURSDAY")) {

			return 5;

		}
		if (day.equalsIgnoreCase("FRIDAY")) {

			return 6;

		}
		if (day.equalsIgnoreCase("SATURDAY")) {

			return 7;

		}

		return 1;

	}

}
