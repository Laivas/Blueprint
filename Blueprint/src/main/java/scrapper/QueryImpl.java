package scrapper;

import java.util.List;

public class QueryImpl implements Query {
	
	private List<String[]> querys;
	
	private List<String> querysList;
	
	private String[] querysArray;
	
	private String query;

	@Override
	public List<String[]> getQuerys() {
		// TODO Auto-generated method stub
		if(querys != null) {
			
			return querys;
			
		}
		
		return null;
	}

	@Override
	public void setQuerys(List<String[]> querys) {
		// TODO Auto-generated method stub
		this.querys = querys;
	}

	@Override
	public String getQuery() {
		// TODO Auto-generated method stub
		if(query != null) {
			
			return query;
			
		}
		
		return null;
	}

	@Override
	public void setQuery(String query) {
		// TODO Auto-generated method stub
		this.query = query;
	}

	@Override
	public String[] getQuerysArray() {
		// TODO Auto-generated method stub
		if(querysArray != null) {
			
			return querysArray;
			
		}
		
		return null;
	}

	@Override
	public void setQuerysArray(String[] querysAsArray) {
		// TODO Auto-generated method stub
		this.querysArray = querysAsArray;
	}

	@Override
	public List<String> getQuerysList() {
		// TODO Auto-generated method stub
		if(querysList != null) {
			
			return querysList;
			
		}
		
		return null;
	}
	
	@Override
	public void setQuerysList(List<String> querys) {
		// TODO Auto-generated method stub
		this.querysList = querys;
	}
	

}
