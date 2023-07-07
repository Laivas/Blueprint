package scrapperProxy;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import scrapperIO.CsvReaderWriter;


public class ProxyUtilImpl implements ProxyUtil {
	
	private List<String> proxyList;

	@Override
	public void fetchProxy(String APIKey) {
		// TODO Auto-generated method stub
		
//		String api = "https://proxy-seller.com/personal/api/v1/" + APIKey + "/proxy/list/mix";
//		
//		try {
//			URL url = new URL(api);
//
//			HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();
//			
//			urlconn.connect();
//			
//			System.out.println(urlconn.getResponseCode());
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	@Override
	public int getTotalNumberOfProxies() {
		// TODO Auto-generated method stub
		
		if(proxyList != null) {
			
			return proxyList.size();
			
		}
		
		return 0;
	}

	@Override
	public String getRandomProxy() {
		// TODO Auto-generated method stub
		
		String proxyString = "";
		
		if(proxyList != null) {
			
			Random random = new Random();
			
			proxyString = proxyList.get(random.nextInt(proxyList.size()));			
		
		}
		
		return proxyString;
	}
	

	@Override
	public List<String> getProxy(int amount) {
		// TODO Auto-generated method stub
		
		List<String> toReturn = new ArrayList<String>();
		
		if(proxyList != null) {
			
			if(proxyList.size() >= amount) {
				
				proxyList.stream().forEach(proxy -> toReturn.add(proxy));
				
			}
			
		}
		
		return toReturn;
	}
	

	@Override
	public List<String> getAllProxies() {
		// TODO Auto-generated method stub
		
		if(proxyList != null) {
			
			return proxyList;
			
		}
		
		return null;
	}

	@Override
	public List<String[]> loadProxy(Path path) {
		// TODO Auto-generated method stub
		CsvReaderWriter csvReaderWriter = new CsvReaderWriter();
		
		List<String[]> proxies = csvReaderWriter.readFromCsvByLine(path);
		
		return proxies;
		
	}

	

}
