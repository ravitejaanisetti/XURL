package xurl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class XUrlImpl implements XUrl{
    private Map<String, String> longToShortUrlMap;
    private Map<String,String> shortToLongUrlMap;
    private Map<String,Integer> urlHitCountMap;
    private static final String base_URL= "http://short.url/";

    public XUrlImpl(){
        longToShortUrlMap = new HashMap<>();
        shortToLongUrlMap = new HashMap<>();
        urlHitCountMap = new HashMap<>();
    }

    private String generateShortUrl(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,9);
    }

    @Override
    public String registerNewUrl(String longUrl) {
        if (longToShortUrlMap.containsKey(longUrl)) {
            return longToShortUrlMap.get(longUrl);
        }

        String shortUrl;
        do {
            shortUrl = base_URL + generateShortUrl();
        } while (shortToLongUrlMap.containsKey(shortUrl));

        longToShortUrlMap.put(longUrl, shortUrl);
        shortToLongUrlMap.put(shortUrl, longUrl);
        urlHitCountMap.put(longUrl, 0);
        return shortUrl;

        }
        

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if(shortToLongUrlMap.containsKey(shortUrl)){
            return null;
        }

        longToShortUrlMap.put(longUrl, shortUrl);
        shortToLongUrlMap.put(shortUrl, longUrl);
        urlHitCountMap.put(longUrl,0);
        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        String longUrl = shortToLongUrlMap.get(shortUrl);
        if(longUrl != null){
            urlHitCountMap.put(longUrl, urlHitCountMap.get(longUrl)+1);
        }
        return longUrl;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        return urlHitCountMap.getOrDefault(longUrl, 0);
    }

    @Override
    public String delete(String longUrl) {
        if(longToShortUrlMap.containsKey(longUrl)){
            String shortUrl = longToShortUrlMap.get(longUrl);
            longToShortUrlMap.remove(longUrl);
            shortToLongUrlMap.remove(shortUrl);
            return shortUrl;
        }
        return null;
    } 
}
