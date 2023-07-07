package scrapperUtil;

import java.text.SimpleDateFormat;
import java.util.Date;



public class FileNameGenerator {
	
	public String generateDateFileName() {
		
		Date date = new Date(System.currentTimeMillis());
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM-dd-yyyy hh mm ss a");
		
		return sdf.format(date).toString();
		
	}
	
	public static void main(String[] args) {
		
		FileNameGenerator fng = new FileNameGenerator();
		
		System.out.println(fng.generateDateFileName());
		
	}

}
