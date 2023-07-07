package scrapperScheduler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import scrapper.Query;
import scrapper.QueryImpl;
import scrapper.Scrapper;
import scrapper.ScrapperImpl;
import scrapperGui.MainPane;
import scrapperIO.CsvReaderWriter;
import scrapperIO.XmlReaderWriter;
import scrapperModel.ScheduledTask;
import scrapperModel.SettingsPaneSelection;
import scrapperXmlModel.ScheduledTasks;

public class ScrappingJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub

		System.out.println("Scrapping Job Implementation here");

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				XmlReaderWriter xmlReaderWriter = new XmlReaderWriter();

				ScheduledTasks tasks = new ScheduledTasks();
				
				MainPane mainPane = (MainPane) context.getMergedJobDataMap().get("scrapperGui.MainPane");
				
				Scrapper scrapper = new ScrapperImpl();

				for (ScheduledTask task : xmlReaderWriter.fromXml(tasks, "scheduledTasks.xml").getScheduledTaskList()) {

					if (task.getTaskName().equals(context.getTrigger().getJobKey().getName())
							&& task.getType().equals(context.getTrigger().getJobKey().getGroup())) {
						
						scrapper.setWriteToPath((Paths.get(task.getWriteTo())));
						
						Query query = new QueryImpl();
						
						query.setQuerys(readEntriesFromCsv(Paths.get(task.getReadFrom())));
						
						xmlReaderWriter = new XmlReaderWriter();

						SettingsPaneSelection settingsPaneSelection = xmlReaderWriter
								.fromXml(new SettingsPaneSelection(), "settingsPaneSelection.xml");

						if (settingsPaneSelection != null) {

							scrapper.setDelayInMs(Integer.valueOf(settingsPaneSelection.getRequestDelay()));

							if (settingsPaneSelection.isUseProxy()) {

								scrapper.setNumberOfThreads(
										Integer.valueOf(settingsPaneSelection.getNumberOfThreads()));

								CsvReaderWriter CsvReaderWriter = new CsvReaderWriter();

								scrapper.setProxies(CsvReaderWriter
										.readFromCsvByLine(Paths.get(settingsPaneSelection.getLoadProxyFromCsv())));

							}

						}

						if (settingsPaneSelection == null) {

							scrapper.setDelayInMs(1000);

						}
						
						mainPane.getMainPaneHandler().setScrapper(scrapper);
						
						mainPane.getMainPaneHandler().updateProgress(scrapper);
						
						scrapper.setQuery(query);

						scrapper.start();
						
						scrapper.stop();
						
					}

				}

			}

		});
		
		thread.start();

	}

	private List<String[]> readEntriesFromCsv(Path path) {

		CsvReaderWriter csvUtil = new CsvReaderWriter();

		List<String[]> entries = csvUtil.readFromCsvByLine(path);

		return entries;

	}

}
