package scrapperIO;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;

public class CsvReaderWriter {

	
	
	public void writeToCsv(List<String[]> lines, Path path) {

		try {

			CSVWriter writer = new CSVWriter(new FileWriter(path.toString(), true));

			writer.writeAll(lines);
			
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeArrayLineToCsv(String[] line, Path path) {

		try {

			CSVWriter writer = new CSVWriter(new FileWriter(path.toString(), true));

			writer.writeNext(line);

			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public <T> void writeObjectsListToCsv(Class<T> type, List<T> objectList, Path path, String[] columns) {

		try {
			Writer fileWriter = new FileWriter(path.toString(), true);

			StatefulBeanToCsvBuilder<T> beanToCsvBuilder = new StatefulBeanToCsvBuilder<T>(fileWriter);

			StatefulBeanToCsv<T> beanToCsvWriter = beanToCsvBuilder.build();

			beanToCsvWriter.write(objectList);

			fileWriter.close();

		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<String[]> readFromCsvByLine(Path path) {

		List<String[]> lines = new ArrayList<String[]>();

		try {

			Reader reader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1);

			CSVReader csvReader = new CSVReader(reader);

			String[] line;

			while ((line = csvReader.readNext()) != null) {

				lines.add(line);

			}

			reader.close();

			csvReader.close();

		} catch (IOException | CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lines;

	}

}
