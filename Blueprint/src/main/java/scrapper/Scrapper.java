package scrapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;
import scrapperDatabase.SqliteDatabaseConnection;
import scrapperHttpConnection.HttpClientRequestBuilder;
import scrapperIO.CsvReaderWriter;
import scrapperParsers.HtmlParser;
import scrapperProxy.ProxyUtil;
import scrapperUtil.InputStreamDecoder;
import scrapperUtil.PausableExecutorService;
import scrapperUtil.UriPreparator;


@Setter
@Getter
public abstract class Scrapper {

	private boolean pause;

	private boolean running;

	private int delayInMs;

	private int numberOfThreads;

	private ProxyUtil proxyUtil;

	private Path writeToPath;

	private SqliteDatabaseConnection sqliteDatabaseConnection;

	private List<String[]> proxies;

	private PausableExecutorService executorService;

	protected InputStreamDecoder inputStreamDecoder;

	protected HttpClientRequestBuilder httpClientRequestBuilder;

	protected UriPreparator uriPreparator;

	private HtmlParser htmlParser;

	private CsvReaderWriter csvReaderWriter;

	public int count;

	private Query query;
	
	
	
	/**
	 * Abstract method to be implemented, to start the program.
	 */
	public abstract void start();

	/**
	 * Method to stop the program.
	 */
	public void stop() {

		setPause(false);

		setRunning(false);

	};

	/**
	 * Abstract method to be implemented, this method takes two params
	 * html source, and link of that html page.
	 * 
	 * @param html
	 * @param link
	 */
	public abstract void scrapPage(String html, String link);

	/**
	 * Abstract method to be implemented to build httpClients.
	 * 
	 * @return
	 */
	public abstract List<HttpClient> buildHttpClients();

	/**
	 * Abstract method to be implemented to build valid links to pages.
	 * 
	 * @return
	 */
	public abstract List<String> buildLinks();

	/**
	 * 
	 * Abstract method to be implemented, which should take html file that is
	 * saved in file system, parses html, saves extracted contents to selected destination
	 * and deletes that html file in fs.
	 * 
	 */
	public abstract void parseHtmlInFileSystem();

	/**
	 * 
	 * Method to pause program.
	 * 
	 */
	public void pause() {

		setPause(true);

		pauseResume();

	}
	
	/**
	 * 
	 * Method to resume program.
	 * 
	 */
	public void resume() {

		setPause(false);

		pauseResume();

	}
	
	/**
	 * 
	 * Private method to pause resume program which uses executor service.
	 * 
	 */
	private void pauseResume() {

		if (executorService != null) {

			if (isPause() == true) {

				executorService.pause();

			}

			if (isPause() == false) {

				executorService.resume();

			}

		}

	}

	/**
	 * No args constructor.
	 */
	public Scrapper() {

	}

	
	/**
	 * Close sqlite database connection.
	 */
	protected void closeDatabaseConnection() {

		if (sqliteDatabaseConnection != null) {

			sqliteDatabaseConnection.sessionClose();

		}

	}
	
	/**
	 * Deprecated since method is sync.
	 */
	public void processRequestSync(List<String> httpRequests, List<HttpClient> httpClients) {

		if (!httpRequests.isEmpty() && !httpClients.isEmpty()) {

			String request = httpRequests.remove(0);

			HttpClient client = httpClients.remove(0);

			HttpRequest httpRequest = httpClientRequestBuilder.buildRequest(uriPreparator.buildURI(request));

			try {

				if (httpRequest != null) {

					HttpResponse<InputStream> response;
					response = client.send(httpRequest, BodyHandlers.ofInputStream());

					inputStreamWriterToFileSystem(response.body(),
							inputStreamDecoder.determineContentEncoding(response.headers()),
							httpRequest.uri().toString());

					httpClients.add(client);

				}
				
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	
	
	/**
	 * Deprecated.
	 * 
	 * @param httpRequests
	 * @param httpClients
	 */
	public void processRequest(List<HttpRequest> httpRequests, List<HttpClient> httpClients) {

		if (!httpRequests.isEmpty() && !httpClients.isEmpty()) {

			HttpRequest request = httpRequests.remove(0);

			HttpClient client = httpClients.remove(0);

			client.sendAsync(request, BodyHandlers.ofInputStream()).thenAcceptAsync(response -> {

				scrapPage(inputStreamWriterToString(response.body(), inputStreamDecoder.determineContentEncoding(response.headers())),
						request.uri().toString());

			});

			httpClients.add(client);

		}

	}

	
	
	/**
	 * 
	 * Takes httprequests and clients sends them async, when client sends request, client goes back to queue.
	 * HttpRequest then removed from synchronized list.
	 * Received Response of html page then is saved to file system.
	 * 
	 * @param httpRequests
	 * @param httpClients
	 */
	public void processRequestNew(List<String> httpRequests, List<HttpClient> httpClients) {

		if (!httpRequests.isEmpty() && !httpClients.isEmpty()) {

			String request = httpRequests.remove(0);

			HttpClient client = httpClients.remove(0);

			HttpRequest httpRequest = httpClientRequestBuilder.buildRequest(uriPreparator.buildURI(request));

			if (httpRequest != null) {

				client.sendAsync(httpRequest, BodyHandlers.ofInputStream()).thenAcceptAsync(response -> {

					inputStreamWriterToFileSystem(response.body(),
							inputStreamDecoder.determineContentEncoding(response.headers()),
							httpRequest.uri().toString());


				});

			}

			httpClients.add(client);

		}

	}

	/**
	 * 
	 * Decodes inputStream by given encoding, and then writes html response to fs, name of saved file is link.
	 * 
	 * @param inputStream
	 * @param encoding
	 * @param link
	 */
	public void inputStreamWriterToFileSystem(InputStream inputStream, String encoding, String link) {

		if (inputStreamDecoder == null) {

			inputStreamDecoder = new InputStreamDecoder();

		}

		inputStreamDecoder.inputStreamWriterToFileSystem(inputStream, encoding, link);

	}

	/**
	 * Deprecated.
	 * 
	 * @param inputStream
	 * @param encoding
	 * @return
	 */
	public String inputStreamWriterToString(InputStream inputStream, String encoding) {

		String response = "";

		if (inputStreamDecoder == null) {

			inputStreamDecoder = new InputStreamDecoder();

		}

		response = inputStreamDecoder.inputStreamHttpResponseToString(inputStream, encoding);

		return response;

	}

	
	
	/**
	 * Deprecated.
	 * 
	 * @param inputStream
	 * @param encoding
	 * @return
	 */
	public List<String> inputStreamWriterToStringNew(InputStream inputStream, String encoding) {

		List<String> response = null;

		if (inputStreamDecoder == null) {

			inputStreamDecoder = new InputStreamDecoder();

		}

		response = inputStreamDecoder.inputStreamHttpResponseToStringNew(inputStream, encoding);

		return response;

	}

	
	
	/**
	 * Deprecated.
	 * 
	 * @param toBeRead
	 * @param httpClients
	 */
	public void singleThreadedScraping(List<HttpRequest> toBeRead, List<HttpClient> httpClients) {

		List<HttpRequest> pagesToBeRead = Collections.synchronizedList(toBeRead);

		List<HttpClient> clients = Collections.synchronizedList(httpClients);

		Random random = new Random();

		while (!pagesToBeRead.isEmpty()) {

			while (isPause() == true) {

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (isRunning() == false) {

				return;

			}

			processRequest(pagesToBeRead, clients);

			try {

				Thread.sleep(random.nextInt(getDelayInMs()) + getDelayInMs());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * Takes request and clients and loops in while loop while requests lists is not empty
	 * in while loop clients and requests are processed with processRequestNew method.
	 * 
	 * @param requests
	 * @param httpClients
	 */
	public void singleThreadedScrapingNew(List<String> requests, List<HttpClient> httpClients) {

		Random random = new Random();

		while (!requests.isEmpty()) {

			while (isPause() == true) {

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (isRunning() == false) {

				return;

			}

			processRequestNew(requests, httpClients);
			

			try {

				Thread.sleep(random.nextInt(getDelayInMs()) + getDelayInMs());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			parseHtmlInFileSystem();

		}

	}

	/**
	 * Deprecated.
	 * 
	 * @param numberOfThreads
	 * @param httpRequests
	 * @param httpClients
	 */
	public void multiThreadedScraping(int numberOfThreads, List<HttpRequest> httpRequests,
			List<HttpClient> httpClients) {

		List<HttpRequest> requests = Collections.synchronizedList(httpRequests);

		List<HttpClient> clients = Collections.synchronizedList(httpClients);

		Random random = new Random();

		executorService = new PausableExecutorService(numberOfThreads);

		executorService.setMaximumPoolSize(numberOfThreads);

		while (!requests.isEmpty()) {

			if (isRunning() == false) {

				executorService.shutdown();

				return;

			}

			executorService.execute(() -> processRequest(requests, clients));

			try {

				Thread.sleep(random.nextInt(getDelayInMs()) + getDelayInMs());

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		executorService.shutdown();

	}

	/**
	 * This method is same as single threaded scraping instead of, it runs on multiple threads when used with proxy.
	 * 
	 * @param numberOfThreads
	 * @param requests
	 * @param httpClients
	 */
	public void multiThreadedScrapingNew(int numberOfThreads, List<String> requests, List<HttpClient> httpClients) {

		Random random = new Random();

		executorService = new PausableExecutorService(numberOfThreads);

		executorService.setMaximumPoolSize(numberOfThreads);

		while (!requests.isEmpty()) {

			if (isRunning() == false) {

				executorService.shutdown();

				return;

			}

			executorService.execute(() -> processRequestNew(requests, httpClients));

			try {

				Thread.sleep(random.nextInt(getDelayInMs()) + getDelayInMs());

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		executorService.shutdown();

	}

}
