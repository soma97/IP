package services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class RSSService {
	
	public static List<SyndEntry> getRSSFeed(){
		try {
			URL feedSource = new URL(Constants.RSS_FEED);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedSource));
			List<SyndEntry> entries = feed.getEntries();
			return entries;
		} catch (IllegalArgumentException | FeedException | IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<SyndEntry>();
	}
}
