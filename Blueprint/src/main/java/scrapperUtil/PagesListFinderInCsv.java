package scrapperUtil;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class PagesListFinderInCsv {
	
	public List<String> findLinksInListOfArrays(List<String[]> querys) {
		
		List<String> urls = new ArrayList<String>();
		
		for (String[] query : querys) {
			
			Arrays.asList(query).stream().filter(val -> val != null)
			.filter(val -> StringUtils.containsIgnoreCase(val, "http") || StringUtils.containsIgnoreCase(val, "www"))
//			.filter(val -> !StringUtils.containsIgnoreCase(val, "google"))
			.filter(val -> !StringUtils.containsIgnoreCase(val, "facebook")).filter(val -> !StringUtils.containsIgnoreCase(val, "twitter"))
			.filter(val -> !StringUtils.containsIgnoreCase(val, "instagram")).filter(val -> !StringUtils.containsIgnoreCase(val, "linkedin"))
			.forEach(val -> urls.add(val));
			
		}
		
		return urls;
		
	}
	
	public List<String> removeDuplicatesFromList(List<String> rawList) {
		
		List<String> listToReturn = new ArrayList<String>();
		
		rawList.stream().filter(line -> !listToReturn.contains(line)).forEach(line -> listToReturn.add(line));
		
		return listToReturn;
		
	}


	public List<String[]> removeDuplicates(List<String[]> rawList) {

		List<String> list = new ArrayList<String>();

		List<String[]> toReturnlist = new ArrayList<String[]>();

		for (String[] row : rawList) {

			if (row.length > 1) {

				if (!toReturnlist.contains(row)) {

					toReturnlist.add(row);

				}

			}

			if (row.length == 1) {

				if (!list.contains(row[0])) {

					list.add(row[0]);

					String[] rowToReturn = { row[0] };

					toReturnlist.add(rowToReturn);

				}

			}

		}

		return toReturnlist;

	}

}
