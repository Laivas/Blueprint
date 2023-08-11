package scrapperParsers;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.*;
import scrapperModel.DataFromPage;
import scrapperUtil.UriPreparator;

@Setter
@Getter
public class HtmlParser {


	private UriPreparator uriPreparator;
	

	public DataFromPage landingPageHtmlParser(String html, String link) {

		Document document = Jsoup.parse(html);

		Elements hrefs = document.getElementsByTag("a");

		Elements addressElements = document.getElementsByTag("address");

		DataFromPage dataFromPage = new DataFromPage();

		dataFromPage.setUrl(link);
		
		String emails = "";

		for (Element element : hrefs) {

			String href = element.attr("href");

			if (href.contains("facebook")) {

				dataFromPage.setFacebook(href);

			}

			if (href.contains("twitter")) {

				dataFromPage.setTwitter(href);

			}

			if (href.contains("instagram")) {

				dataFromPage.setInstagram(href);

			}

			if (href.contains("linkedin")) {

				dataFromPage.setLinkedin(href);

			}

			if (href.contains("tel:")) {

				dataFromPage.setTel(href);

			}

			if (href.contains("mailto")) {				
				
				emails += href + " ";
				
				if(emails.contains("mailto:")) {
					
					emails = emails.split("mailto:")[1];
					
				}
				
				dataFromPage.setMail(emails);

			}

		}

		dataFromPage.setAddress(findAddressInElements(addressElements));

//		dataFromPage.setAdditionalEmails(filteredMails(findEmailInJsoupDocument(document)));

		return dataFromPage;

	}
	

	public String findAddressInElements(Elements addressElements) {

		if (addressElements != null) {

			if (addressElements.size() > 0) {

				String address = "";

				for (Element addressElement : addressElements) {

					address += addressElement.text();

				}

				return address;

			}

		}

		return null;

	}


	public List<String> findEmailInJsoupDocument(Document document) {

		Elements elements = document.getAllElements();

		String email = "";

		List<String> mails = new ArrayList<String>();

		for (Element element : elements) {

			if (!element.html().isEmpty()) {

				for (String line : Arrays.asList(element.html().split("\""))) {

					email = findEmail("\"" + line + "\"");

					if (!mails.contains(email)) {

						mails.add(email);

					}

				}

			}

		}

		return mails;

	}

	public String filteredMails(List<String> emails) {

		List<String> emailsFiltered = new ArrayList<String>();

		String emailsToReturn = "";

		emails.stream().filter(email -> email != null).filter(email -> !email.isEmpty())
				.filter(email -> !email.contains("context")).filter(email -> email.indexOf("@") != 0)
				.filter(email -> email.contains(".")).forEach(email -> emailsFiltered.add(email));

		for (String email : emailsFiltered) {
			
			if(email.length() > 4) {
				
				email = checkEmailEndingValidation(email);

				emailsToReturn += email + " ";
				
			}

		}

		return emailsToReturn;

	}
	
	public String checkEmailEndingValidation(String email) {
		
		if(email.split("@")[1].matches("[.0-9.]+") && email.split("@")[1].contains(".") && !email.split("@")[1].matches("[a-zA-Z]+")) {
			
			return "";
			
		} else 
			
		return email;
	}
	
	public String findEmail(String line) {
		
		String mail = "";
		
		String lineFromEta = "";
		
		String reverseMail = "";
		
		if (line.contains("@")) {
			
			int index = line.indexOf("@");
			
			int indexOfDot = line.indexOf(".", line.indexOf("@"));
			
			if (indexOfDot > index) {
				
				if (line.contains("@") && line.contains(".")) {
					
					mail = line.substring(line.indexOf("@"), line.indexOf(".", line.indexOf("@")));
					
					if(mail.contains(" ")) {
						
						return " ";
						
					}
					
					lineFromEta = line.substring(line.indexOf(mail));
					
					char secondIllegal = findFirstIllegalCharracter(
							line.substring(line.indexOf(line.codePointAt(line.indexOf(mail)))));
					
					mail = lineFromEta.substring(0, lineFromEta.indexOf(secondIllegal));
					
					String reverse = new StringBuffer(line).reverse().toString();
					
					reverseMail = reverse.substring(reverse.indexOf("@"));
					
//					int indexFirstIllegal = findIndexOfFirstIllegalCharracter(reverseMail);
					
//					int substringIndex = line.indexOf("@") - findIndexOfFirstIllegalCharracter(reverseMail) + 1;
					
					mail = line.substring(line.indexOf("@") - findIndexOfFirstIllegalCharracter(reverseMail) + 1,
							line.indexOf(secondIllegal, line.indexOf("@")));
					
				}
				
			}
			
		}
		
		return mail;
		
	}
	
	

	public char findFirstIllegalCharracter(String line) {

		String regexes = "!#$%&'*+-/=?^_`{|}~\"(),:;<>[] ";

		for (char check : line.toCharArray()) {

			for (char regex : regexes.toCharArray()) {

				if (check == regex) {

					return regex;

				}

			}

		}

		return 0;

	}

	public int findIndexOfFirstIllegalCharracter(String line) {

		String regexes = "!#$%&'*+-/=?^_`{|}~\"(),:;<>[] ";

		for (char check : line.toCharArray()) {

			for (char regex : regexes.toCharArray()) {

				if (check == regex) {

					return line.indexOf(regex);

				}

			}

		}

		return 0;

	}

}
