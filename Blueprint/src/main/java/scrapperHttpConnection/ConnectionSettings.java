package scrapperHttpConnection;

import java.net.*;
import java.net.Proxy.Type;
import java.util.List;
import java.util.Map;

import lombok.*;
import scrapperModel.CustomHeader;


@Setter
@Getter
public class ConnectionSettings {
	
	private String proxyUsername;
	
	private String proxyPassword;
	
	private String proxyHost;
	
	private int proxyPort;
	
	private Proxy proxy;
	
	private CustomHeader header;
	
	private List<HttpCookie> cookies;
	
	private Map<String, String> headers;
	
	private int numberOfThreads;
	
	public void buildHttpProxy() {
		
		this.proxy = new Proxy(Type.HTTP, new InetSocketAddress(this.proxyHost, this.proxyPort));
		
	}

}
