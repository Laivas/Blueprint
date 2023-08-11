package scrapper;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import scrapperIO.CsvReaderWriter;

public class Temp {
	
	public static void main(String[] args) {
		
		CsvReaderWriter rw = new CsvReaderWriter();
		
		List<String[]> lines = rw.readFromCsvByLine(Paths.get("C:/Users/LM/Desktop/August-02-2023 05 41 42 pm.csv"));
		
		String [] linesArr = new String [lines.size()]; 
		
		for(String line [] : lines) {
			
			System.out.println(line[7]);
//			
//			String [] linesss = {"http://" + line[0]};
//			
//			rw.writeArrayLineToCsv(linesss, Paths.get("C:/Users/LM/Desktop/August-02.csv"));
		}

	}

}
