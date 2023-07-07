package scrapperProxy;

import java.nio.file.Path;
import java.util.List;

public interface ProxyUtil {
	
	public List<String[]> loadProxy(Path path);
	
	public void fetchProxy(String APIKey);
	
	public int getTotalNumberOfProxies();
	
	public String getRandomProxy();
	
	public List<String> getProxy(int amount);
	
	public List<String> getAllProxies();

}
