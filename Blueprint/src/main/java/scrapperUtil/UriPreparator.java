package scrapperUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class UriPreparator {

	public String linkBuilder(URI uri, String path) {

		String link = "";

		if (StringUtils.startsWithIgnoreCase(path, "http")) {

			path = prepareUri(path);

			return path;

		}

		if (!path.contains(uri.getAuthority())) {

			link = uri.getAuthority() + path;

		}

		link = prepareUri(link);

		return link;

	}

	public List<URI> removeDuplicates(List<URI> uris) {

		List<URI> urisToReturn = new ArrayList<URI>();

		for (URI uri : uris) {

			if (uri != null) {

				if (!urisToReturn.contains(uri)) {

					urisToReturn.add(uri);

				}

			}

		}

		return urisToReturn;

	}

	public String prepareUri(String rawUrl) {

		if (rawUrl != null) {

			rawUrl = removeWhiteSpaces(rawUrl);

			if (!rawUrl.startsWith("http")) {

				rawUrl = "http://" + rawUrl;

			}

			if (!rawUrl.startsWith("https") && !rawUrl.startsWith("http")) {

				rawUrl = "http://" + rawUrl;

			}

			if (rawUrl.matches("http://")) {

				return null;

			}

			rawUrl = rawUrl.strip();

			rawUrl = replaceIncorrectCharracters(rawUrl);

			if (rawUrl.startsWith("http") || rawUrl.startsWith("https")) {

				return rawUrl;

			}

		}

		return null;

	}

	private String removeWhiteSpaces(String line) {

		if (line != null) {

			if (StringUtils.containsWhitespace(line)) {

				line = line.replaceAll(" ", "");

			}

		}

		return line;

	}

	public URI uriValidatorForHttpRequestBuilder(URI uri) {

		if (uri.getScheme() != null && uri.getHost() != null) {

			return uri;

		}

		return null;

	}

	public URI buildURI(String link) {

		if (urlValidator(link)) {

			try {
				return uriValidatorForHttpRequestBuilder(new URI(link));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;

	}

	public boolean urlValidator(String link) {

		try {
			new URI(link);

			return true;

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	private String replaceIncorrectCharracters(String line) {

		if (line.contains("..")) {

			line = line.replaceAll("..", ".");

		}

		if (line.contains("...")) {

			line = line.replaceAll("...", ".");

		}

		if (line.contains(",")) {

			line = line.replaceAll(",", ".");

		}

		if (line.contains(",,")) {

			line = line.replaceAll(",,", ".");

		}

		if (line.contains(";")) {

			line = line.replaceAll(";", "");

		}

		return line;

	}

}
