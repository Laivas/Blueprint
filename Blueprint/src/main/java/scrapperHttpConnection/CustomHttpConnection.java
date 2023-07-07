package scrapperHttpConnection;

import lombok.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Authenticator;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Setter
@Getter
public class CustomHttpConnection {

	private HttpURLConnection httpURLConnection;

	private ConnectionSettings connectionSettings;

	private List<HttpCookie> cookies;

	public CustomHttpConnection(ConnectionSettings connectionSettings, URL url) {
		

		this.connectionSettings = connectionSettings;

		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();

			httpURLConnection.setAuthenticator(new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {

					if (getRequestorType().equals(RequestorType.PROXY)) {

						return getPasswordAuthentication(getRequestingHost(), getRequestingPort());

					} else {

						return null;

					}

				}

				protected PasswordAuthentication getPasswordAuthentication(String host, int port) {

					if (connectionSettings.getProxyHost().equals(host) && connectionSettings.getProxyPort() == port) {

						return new PasswordAuthentication(connectionSettings.getProxyUsername(),
								connectionSettings.getProxyPassword().toCharArray());

					} else

						return null;
				}

			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private HttpURLConnection addHeaders() {

		if (httpURLConnection != null) {

			if (connectionSettings.getHeaders() != null) {

				connectionSettings.getHeaders().entrySet().stream()
						.forEach(header -> httpURLConnection.setRequestProperty(header.getKey(), header.getValue()));

			}

		}

		return httpURLConnection;

	}

	private String buildRequestCookie(List<HttpCookie> cookies) {

		String cookie = "";

		for (HttpCookie ck : cookies) {

			if (!ck.hasExpired()) {

				cookie += ck.getName() + "=" + ck.getValue() + "; ";

			}

		}

		return cookie;

	}

	private HttpURLConnection addCookies() {

		if (httpURLConnection != null) {

			if (connectionSettings.getCookies() != null) {

				this.cookies = connectionSettings.getCookies();

				httpURLConnection.setRequestProperty("Cookie", buildRequestCookie(this.cookies));

			}

		}

		return httpURLConnection;

	}

	public List<HttpCookie> getCookiesFromConnection(HttpURLConnection httpURLConnection) {

		CookieManager cookieManager = null;

		if (httpURLConnection != null) {

			cookieManager = new CookieManager();

			cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

			try {

				URI uri = httpURLConnection.getURL().toURI();

				cookieManager.put(uri, httpURLConnection.getHeaderFields());

			} catch (URISyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return cookieManager.getCookieStore().getCookies();

	}

	public void cookieUpdate(List<HttpCookie> newCookies) {

		List<HttpCookie> updatedCookies = null;

		if (this.cookies != null) {

			updatedCookies = new ArrayList<HttpCookie>();

			for (HttpCookie cookie : this.cookies) {

				if (!cookie.hasExpired()) {

					updatedCookies.add(cookie);

				}

			}

			for (HttpCookie cookie : newCookies) {

				boolean contains = false;

				for (HttpCookie ck : updatedCookies) {

					if (cookie.getName().equals(ck.getName())) {

						contains = true;

					}

				}

				if (contains == false) {

					updatedCookies.add(cookie);

				}

			}

			this.connectionSettings.setCookies(updatedCookies);

		}

	}

	public String getHtmlContent() {

		String content = null;

		if (httpURLConnection != null) {

			addHeaders();

			addCookies();

		}

		try {

			httpURLConnection.connect();

			System.out.println(httpURLConnection.getResponseCode() + " Requestor: " + connectionSettings.getProxyHost());
			
			System.out.println();

			if (this.cookies == null) {

				this.cookies = getCookiesFromConnection(httpURLConnection);

				connectionSettings.setCookies(this.cookies);

			}

			cookieUpdate(getCookiesFromConnection(httpURLConnection));

			content = inputStreamWriterToString(httpURLConnection.getInputStream());

			httpURLConnection.disconnect();

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return content;

	}

	public String inputStreamWriterToString(InputStream inputStream) {

		String content = "";

		try {
			GZIPInputStream gzipStream = new GZIPInputStream(inputStream);

			Reader reader = new InputStreamReader(gzipStream);

			BufferedReader br = new BufferedReader(reader);

			String line = "";

			while ((line = br.readLine()) != null) {

				content += line;

			}

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return content;

	}

}
