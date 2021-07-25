package com.hellodk.getv2exmytopics.service;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hellodk
 * @date 2021-07-25 18:25
 */
@Configuration
public class MainService {

    private final String BASE_URL = "https://www.v2ex.com/my/topics?p=";

    private final String SITE_BASE_URL = "https://www.v2ex.com";

    private final String CLASS_NAME = "topic-link";

    @Autowired
    private RestTemplate restTemplate;

    public JSONObject sendGet() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        List<String> cookiesList = new ArrayList<>();
        cookiesList.add("A2=2|1:0|10:1623574307|2:A2|56:MmYxMjNkNDM2MmE1MzEyYjVmYjUwNTdmOGYxNzRkZjRhNGIyMzQ3MA==|f8ffa9f5e7824d1248777385e4d8eaad170dfce4873a70bcee947de3797ff097");
        cookiesList.add("V2EX_LANG=zhcn");
        cookiesList.add("PB3_SESSION=2|1:0|10:1627048274|11:PB3_SESSION|36:djJleDoxNi4xNjIuMTIwLjgwOjY1NTY4MDE4|f5a442e65ec193abb97cda1990d697bde4955ed1b13517c69abed24cca302f21");
        cookiesList.add("V2EX_TAB=2|1:0|10:1627213331|8:V2EX_TAB|4:YWxs|b46f144a77da501a01261107a770b04378f211921ca83ee2a8f58fac63dc99fd");
        headers.put(HttpHeaders.COOKIE, cookiesList);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        List<String> titleList = new ArrayList<>();
        JSONObject resultJsonObj = new JSONObject(30, true);

        for (int i = 0; i < 30; i++) {
            String iPlusOne = String.valueOf(i + 1);
            String url = BASE_URL.concat(iPlusOne);
            System.out.println("第 " + (i + 1) + " 次循环 url 是 " + url);
            RequestEntity requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(url));
            ResponseEntity responseEntity = restTemplate.exchange(requestEntity, String.class);
            Object ooo = responseEntity.getBody();
            Document document = Jsoup.parse((String) ooo);
            Elements elements = document.getElementsByClass(CLASS_NAME);
            String pageNum = "page".concat(iPlusOne);
            List<Map<String, Object>> jsonList = new ArrayList<>();

            int forEachLoopVar = 1;
            for (Element element : elements) {
                String title = element.ownText();
                String href = element.attr("href");

                Map<String, Object> titleInfoMap = new HashMap<>();
                titleInfoMap.put("seq", forEachLoopVar);
                titleInfoMap.put("title", title);
                // 拼接完整 url
                String topicUrl = SITE_BASE_URL.concat(href);
                titleInfoMap.put("url", topicUrl);
                getTopicInfo(topicUrl);
                jsonList.add(titleInfoMap);
                ++forEachLoopVar;
            }
            resultJsonObj.put(pageNum, jsonList);
        }
//        System.out.println(titleList.toString());
        return resultJsonObj;
    }

    public String getTopicCreatedTime() {
        String time = "2021-07-23T03:43:21Z";

        time = time.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
        Date d = null;
        try {
            d = format.parse(time);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        //此处是将date类型装换为字符串类型，比如：Sat Nov 18 15:12:06 CST 2017转换为2017-11-18 15:12:06
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String date = sf.format(d);
//        System.out.println(d);//这里输出的是date类型的时间
//        System.out.println(d.getTime() / 1000);//这里输出的是以秒为单位的long类型的时间。如果需要一毫秒为单位，可以不用除1000.
        return sf.format(d);
    }
}
