package scrapperUtil;

import java.io.InputStream;
import java.io.Writer;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.http.HttpHeaders;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class InputStreamDecoder {

	private static String tempFolder = System.getProperty("user.home").replaceAll("\\\\", "/") + "/ScrappingApp/temp/";
	
	public InputStreamDecoder() {
		
	}
	
	public void createTempDirectoryIfNotExists() {
		
		if(!new File(tempFolder).exists()) {
			
			new File(tempFolder).mkdir();
			
		}
		
	}
	
	
	public void inputStreamWriterToFileSystem(InputStream inputStreamResponse, String encoding, String link) {
		
//		InputStream inputStream = decodeInputStream(inputStreamResponse, encoding);
//		InputStream inputStream = decodeInputStream(inputStreamResponse, encoding);
		
		createTempDirectoryIfNotExists();

		try {

//			Reader reader = new InputStreamReader(inputStream);
//
//			BufferedReader bufferedReader = new BufferedReader(reader);
			
//			reader = new InputStreamReader(decodeInputStream(inputStreamResponse, encoding));
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(decodeInputStream(inputStreamResponse, encoding)));

			String line = "";
			
			String fileName = link.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
			
			String fName = checkFileNameForLength(fileName);
			
//			Writer writer = new FileWriter(tempFolder + fileName + ".html", true);
			Writer writer = new FileWriter(tempFolder + fName + ".html", true);

			while ((line = bufferedReader.readLine()) != null) {

//				writer.append(line);
				
				writer.write(line);

			}
			
			writer.flush();
			
			inputStreamResponse.close();
			
			writer.close();
			
			bufferedReader.close();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public String checkFileNameForLength(String fileName) {
		
		String fName = fileName;
		
		if(fileName.length() > 128) {
			
			fName = fileName.substring(0, 128);
			
		}
		
		return fName;
		
	}

	public String inputStreamHttpResponseToString(InputStream response, String encoding) {
		
		String content = "";

		InputStream inputStream = decodeInputStream(response, encoding);

		try {

			Reader reader = new InputStreamReader(inputStream);

			BufferedReader bufferedReader = new BufferedReader(reader);

			String line = "";

			while ((line = bufferedReader.readLine()) != null) {

				content += line;

			}
			
			inputStream.close();
			
			reader.close();
			
			bufferedReader.close();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return content;

	}
	
	public List<String> inputStreamHttpResponseToStringNew(InputStream response, String encoding) {
		
		List<String> lines = new ArrayList<String>();
		
		InputStream inputStream = decodeInputStream(response, encoding);
		
		try {
			
			Reader reader = new InputStreamReader(inputStream);
			
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String line = "";
			
			while ((line = bufferedReader.readLine()) != null) {
				
				lines.add(line);
				
			}
			
			inputStream.close();
			
			reader.close();
			
			bufferedReader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lines;
		
	}

	private InputStream decodeInputStream(InputStream response, String encoding) {

		try {

			if (encoding.equals("")) {

				return response;

			}

			if (encoding.equalsIgnoreCase("gzip")) {

				return new GZIPInputStream(response);

			}

			if (encoding.equalsIgnoreCase("deflate")) {

				return new InflaterInputStream(response);

			}

			else
				return null;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public String determineContentEncoding(HttpHeaders responseHeaders) {

		String encoding = "";

		encoding = responseHeaders.firstValue("Content-Encoding").orElse("");

		if (encoding.equals("")) {

			encoding = responseHeaders.firstValue("content-encoding").orElse("");

		}

		return encoding;

	}

}
