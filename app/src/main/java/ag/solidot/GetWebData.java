package ag.solidot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by flicker on 16/6/13.
 */
public class GetWebData {
    private Document classDom, storyDom, hotDom;

    public List<Map<String, String>> parseClassDom(String html) {
        try {
            classDom = Jsoup.parse(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map<String, String>> classList = new ArrayList<>();

        Elements es = classDom.select(".block_m");
        for (Element e : es) {
            Map<String, String> map = new HashMap<>();
            map.put("title", e.select("h2 > a").text());
            map.put("sid", e.select("h2 > a").attr("href").substring(11));
            map.put("content", e.select(".p_mainnew").text());
            map.put("author", e.select("b").first().text().substring(2));
            classList.add(map);
        }
        return classList;
    }

    public List<Map<String, String>> parseStoryDom(String html) {
        try {
            storyDom = Jsoup.parse(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map<String, String>> storyList = new ArrayList<>();

        Elements es = storyDom.select(".block_m");
        for (Element e : es) {
            Map<String, String> map = new HashMap<>();
            map.put("title", e.select("h2").text());
            map.put("date", e.select(".talk_time").first().text().split("发表于 ")[1].split(" 星期")[0]);
            map.put("content", e.select(".p_mainnew").text());
            map.put("author", e.select("b").first().text().substring(2));
            storyList.add(map);
        }
        return storyList;
    }

    public List<Map<String, String>> parseHotDom(String html) {
        try {
            hotDom = Jsoup.parse(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map<String, String>> hotList = new ArrayList<>();

        Elements es = hotDom.select(".block_m");
        for (Element e : es) {
            Map<String, String> map = new HashMap<>();
            map.put("title", e.select("h2 > a").text());
            map.put("sid", e.select("h2 > a").attr("href").substring(11));
            hotList.add(map);
        }
        return hotList;
    }
}

