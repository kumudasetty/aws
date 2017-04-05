import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36").get();
 *Elements links = doc.select("a[href]");
 *Elements media = doc.select("[src]");
 *Elements imports = doc.select("link[href]");
 */


public final class HTMLParser
{
	private Matcher matcher= null;
	private static Pattern pattern= null;

	private static final String DOMAIN_NAME_PATTERN= "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
  	static {
		pattern= Pattern.compile(DOMAIN_NAME_PATTERN);
  	}

	public static boolean isValidDomainName(String url) {
		return pattern.matcher(url).find();
	}


	public static void print(String url) throws IOException
	{
		if(url!= null)
		{
			print("Fetching the url: %s", url);
			//Document doc= Jsoup.connect(url).get();
			Document doc= Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36").get();

			//title
			String title= doc.title();
			print("title: %s", title);

			//Parsing a body fragment
			//print("%s", doc);

			int i= 1;
			Elements elements= doc.getAllElements();
			int size= elements.size();
			print("\n***Total Elements: (%d)", size);
			for(Element element: elements)
			{
				String nodeName=  element.nodeName();
				//print(i + ". * %s: ", nodeName);


				if(nodeName.equals("a")) print(i+ ". * %s: <%s> (%s)", nodeName, element.absUrl("href"), trim(element.text(), 35));
				//if(nodeName.equals("link")) print(i+ " * %s: <%s> (%s)", nodeName, element.attr("abs:href"), element.attr("rel"));

				//if(nodeName.equals("img")) print(i+ ". * %s: <%s> %sx%s (%s)", nodeName, element.absUrl("src"), element.attr("width"), element.attr("height"), trim(element.attr("alt"), 20));


				i++;
			}
		}
	}

	private static void print(String s, Object... args)
	{
        System.out.println(String.format(s, args));
    };

    private static String trim(String s, int width)
    {
        if(s!= null&& s.length() > width) return s.substring(0, width- 1)+ ".";
        return s;
    };



	public static void main(String[]sa) throws Exception
	{
		long end= -System.currentTimeMillis();
		String str= "https://google.co.in/";
		str="https://news.google.co.in/";




		print(str);

		System.out.println("\n\n\ntime: " + (System.currentTimeMillis() + end));

	}

};;