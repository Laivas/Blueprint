package scrapper;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;

import lombok.Getter;
import lombok.Setter;
import scrapperHttpConnection.ConnectionSettings;
import scrapperHttpConnection.HttpClientRequestBuilder;
import scrapperIO.CsvReaderWriter;
import scrapperUtil.InputStreamDecoder;
import scrapperUtil.PagesListFinderInCsv;
import scrapperUtil.UriPreparator;
import scrapperModel.DataFromPage;
import scrapperParsers.HtmlParser;

@Getter
@Setter
public class ScrapperImpl extends Scrapper {

	private int totalPages;
	
	public String tempFolder = System.getProperty("user.home").replaceAll("\\\\", "/") + "/ScrappingApp/temp/";

	@Override
	public void start() {
		// TODO Auto-generated method stub
		setRunning(true);

		properties();

		super.csvReaderWriter = new CsvReaderWriter();

		super.htmlParser = new HtmlParser();

		super.httpClientRequestBuilder = new HttpClientRequestBuilder();

		super.uriPreparator = new UriPreparator();

		super.inputStreamDecoder = new InputStreamDecoder();

		runnerNew(buildHttpClients(), buildLinks());

		setRunning(false);

		closeDatabaseConnection();

	}

	public void runnerNew(List<HttpClient> httpClients, List<String> requests) {

		if (getProxies() != null && getNumberOfThreads() > 0 && isRunning()) {

			multiThreadedScrapingNew(getNumberOfThreads(), requests, httpClients);

		}

		if (getProxies() == null && isRunning()) {

			singleThreadedScrapingNew(requests, httpClients);

		}

	}

	public void properties() {

		System.setProperty("jdk.httpclient.allowRestrictedHeaders", "Connection");
		System.setProperty("jdk.http.auth.proxying.disabledSchemes", "");
		System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");

	}

	@Override
	public List<String> buildLinks() {

		List<String[]> querys = super.getQuery().getQuerys();

		PagesListFinderInCsv pagesListFinderInCsv = new PagesListFinderInCsv();

		List<String> links = pagesListFinderInCsv.findLinksInListOfArrays(querys);

		return Collections.synchronizedList(links);

	}

//	@Override
//	public List<HttpRequest> buildHttpRequests() {
//		// TODO Auto-generated method stub
//
//		HttpClientRequestBuilder httpClientRequestBuilder = new HttpClientRequestBuilder();
//
//		PagesListFinderInCsv pagesListFinderInCsv = new PagesListFinderInCsv();
//
//		UriPreparator uriPreparator = new UriPreparator();
//
//		List<HttpRequest> requests = new ArrayList<HttpRequest>();
//
//		List<String[]> querys = super.getQuery().getQuerys();
//
//		List<URI> uris = new ArrayList<URI>();
//
//		List<String> links = pagesListFinderInCsv.findLinksInListOfArrays(querys);
//
//		for (String link : links) {
//
//			String uriString = uriPreparator.prepareUri(link);
//
//			if (uriString != null) {
//
//				uris.add(uriPreparator.buildURI(uriString));
//
//			}
//
//		}
//
//		List<URI> preparedUris = uriPreparator.removeDuplicates(uris);
//
//		for (URI uri : preparedUris) {
//
//			requests.add(httpClientRequestBuilder.buildRequest(uri));
//
//		}
//
//		return requests;
//	}

	@Override
	public List<HttpClient> buildHttpClients() {
		// TODO Auto-generated method stub

		List<HttpClient> httpClients = new ArrayList<HttpClient>(getNumberOfThreads());

		HttpClientRequestBuilder httpClientRequestBuilder = new HttpClientRequestBuilder();

		if (getProxies() != null) {

			for (String[] line : getProxies()) {

				if (httpClients.size() < getNumberOfThreads()) {

					ConnectionSettings connectionSettings = new ConnectionSettings();

					connectionSettings.setNumberOfThreads(getNumberOfThreads());

					connectionSettings.setProxyHost(line[0]);

					connectionSettings.setProxyPort(Integer.valueOf(line[1]));

					connectionSettings.setProxyUsername(line[2]);

					connectionSettings.setProxyPassword(line[3]);

					httpClients.add(httpClientRequestBuilder.createClientWithProxy(connectionSettings));

				}

			}

		}

		if (getProxies() == null) {

			HttpClientRequestBuilder customHttpClient = new HttpClientRequestBuilder();

			httpClients.add(customHttpClient.createClient());

		}

		return Collections.synchronizedList(httpClients);

	}
	
	public File[] tempFolderFiles(String pathToTempFolder) {

		File[] files = new File(pathToTempFolder).listFiles(file -> file.getName().contains(".html"));
		
		return files;
		
	}

	@Override
	public void parseHtmlInFileSystem() {

		File[] files = tempFolderFiles(tempFolder);

		for (File file : files) {
			
			DataFromPage dataFromPage = null;

			if (file.exists()) {

				try {
					dataFromPage = htmlParser.landingPageHtmlParser(Jsoup.parse(file).html(), file.getName());
					
					writeData(dataFromPage);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				count++;

				System.out.println(file.getName() + " writen.");

				file.delete();

				System.out.println("deleted.");

			}

		}

	}

	public void writeData(DataFromPage dataFromPage) {

		if (getWriteToPath() != null) {

			super.csvReaderWriter.writeArrayLineToCsv(dataFromPage.fieldsValuesToArray(), getWriteToPath());

		}

		if (super.getSqliteDatabaseConnection() != null) {

			super.getSqliteDatabaseConnection().insertIntoDatabase(dataFromPage);

		}

		dataFromPage = null;
		
	}

	@Override
	public void scrapPage(String html, String link) {
		// TODO Auto-generated method stub

		DataFromPage dataFromPage = htmlParser.landingPageHtmlParser(html, link);

		System.out.println(dataFromPage.getMail());

		csvReaderWriter.writeArrayLineToCsv(dataFromPage.fieldsValuesToArray(), getWriteToPath());

		count++;

		System.out.println(link + " writen.");

	}

}
