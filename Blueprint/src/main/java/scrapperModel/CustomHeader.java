package scrapperModel;

import lombok.*;

@Getter
@Setter
public class CustomHeader {
	
//	private String AcceptCharset = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
//	private String KeepAlive = "30";
//	private String Connection = "keep-alive";
//	private String Pragma = "no-cache";	
//	private String ETag = "134932-IB/oaVqNQhOjjemrS0bFsYSAQ2c";	
//	private String authority = "www.zoominfo.com";
	private String method = "GET";
//	private String path = "/maps/search/pica/@54.7392769,25.2194883,13z/data=!3m1!4b1?entry=ttu";
	private String scheme = "https";
//	private String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7";
//	private String accept = "*/*";
	private String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7";
	private String acceptencoding = "gzip, deflate";
//	private String acceptencoding = "gzip";
	private String acceptlanguage = "en-US,en;q=0.9,lt;q=0.8";
	private String cachecontrol = "max-age=0";
//	private String cookie
//	private String referer = "https://www.zoominfo.com/c/21st-century-plastics-corp/119439372";
	private String secchua = "\"Chromium\";v=\"112\", \"Google Chrome\";v=\"112\", \"Not:A-Brand\";v=\"99\"";
	private String secchuamobile = "?0";
	private String secchuaplatform = "\"Windows\"";
	private String secfetchdest = "document";
	private String secfetchmode = "navigate";
	private String secfetchsite = "same-origin";
	private String upgradeinsecurerequests = "1";
	private String useragent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36";
	
	
	
}


