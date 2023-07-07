package scrapperUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private final String[] dayOfTheWeek = { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };

	public String timeConverter(String time) {

		SimpleDateFormat date12Format = new SimpleDateFormat("hh:mma");

		SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

		try {
			if (date12Format.parse(time) != null) {

				return date24Format.format(date12Format.parse(time));

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}

	public String getDayOfTheWeek(int index) {

		return dayOfTheWeek[index];

	}

	public String getDayOfTheWeek(String fullDayName) {

		for (String day : Arrays.asList(dayOfTheWeek)) {

			if (fullDayName.toUpperCase().contains(day)) {

				return day;

			}

		}
		return null;
	}
	
	public String addMinuteTo(String hourAndMin) {

		String newTime = "";

		try {

			SimpleDateFormat df = new SimpleDateFormat("hh:mm");

			Date d = df.parse(hourAndMin);

			Calendar cal = Calendar.getInstance();

			cal.setTime(d);

			cal.add(Calendar.MINUTE, 1);

			newTime = df.format(cal.getTime());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newTime;

	}
	

	public String addMinute(String min) {

		String newTime = "";

		try {

			SimpleDateFormat df = new SimpleDateFormat("mm");

			Date d = df.parse(min);

			Calendar cal = Calendar.getInstance();

			cal.setTime(d);

			cal.add(Calendar.MINUTE, 1);

			newTime = df.format(cal.getTime());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newTime;

	}
	
	public Calendar dateToCalendar(Date date) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		System.out.println(calendar.getTime().toString());
		return calendar;

	}
	
	public Date stringToDate(String StringDate) {

		Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			System.out.println(simpleDateFormat.parse(StringDate).toString());
			date = simpleDateFormat.parse(StringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;

	}
	
	public String dateFormatChanger(String date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		SimpleDateFormat sdfParse = new SimpleDateFormat("MM/dd/yyyy");
		
		String dateParsed = null;
		
		try {
			dateParsed = sdfParse.format(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dateParsed.toString();
		
	}

}
