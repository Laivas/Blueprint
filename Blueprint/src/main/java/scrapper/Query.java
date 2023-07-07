package scrapper;

import java.util.List;

public interface Query {

	public List<String[]> getQuerys();
	
	public List<String> getQuerysList();
	
	public String[] getQuerysArray();
	
	public String getQuery();
	
	public void setQuerys(List<String[]> querys);
	
	public void setQuery(String query);
	
	public void setQuerysArray(String[] query);
		
	public void setQuerysList(List<String> querys);
	
}
