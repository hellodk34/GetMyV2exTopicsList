package com.hellodk.getv2exmytopics.service;

import com.alibaba.fastjson.JSONObject;
import com.hellodk.getv2exmytopics.config.UserCookiesVerify;
import org.apache.commons.collections4.MapUtils;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

//    private MainService mainService;
//
//    @Autowired
//    public MainService(MainService mainService) {
//        this.mainService = mainService;
//    }

    private final String BASE_URL = "https://www.v2ex.com/my/topics?p=";

    private final String SITE_BASE_URL = "https://www.v2ex.com";

    private final String CLASS_NAME = "topic-link";

    private final String TOPIC_SEQ = "seq";

    private final String TOPIC_TITLE = "title";

    private final String TOPIC_CREATED_TIME = "createdTime";

    private final String TOPIC_URL = "url";

    private final String TOPIC_CREATOR = "creator";

    static int totalPageNumber = 1;

    static String a2;

    static String v2exTab;

    static String pb3Session;

    static String v2exLang;

    static String v2exReferrer;

    static long startTime;

    static {
        totalPageNumber = UserCookiesVerify.TOTAL_PAGE_NUMBER;
        a2 = UserCookiesVerify.A2;
        v2exTab = UserCookiesVerify.V2EX_TAB;
        pb3Session = UserCookiesVerify.PB3_SESSION;
        v2exLang = UserCookiesVerify.V2EX_LANG;
        v2exReferrer = UserCookiesVerify.V2EX_REFERRER;
        startTime = System.currentTimeMillis();
    }

    @Autowired
    private RestTemplate restTemplate;

    public void sendGet() throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        List<String> cookiesList = new ArrayList<>();

        // ???????????????????????????????????????
        Boolean loadConfigFile = UserCookiesVerify.getLoadConfigFile();
        // ????????????????????????????????? cookie ??????????????? header ??? cookie ?????????????????? `key=` ???key=value ???????????????
        if (Boolean.TRUE.equals(loadConfigFile)) {
            cookiesList.add("A2=".concat(a2));
            cookiesList.add("V2EX_TAB=".concat(v2exTab));
            cookiesList.add("PB3_SESSION=".concat(pb3Session));
            cookiesList.add("V2EX_LANG=".concat(v2exLang));
            cookiesList.add("V2EX_REFERRER=".concat(v2exReferrer));
        }
        else {
            cookiesList.add(a2);
            cookiesList.add(v2exTab);
            cookiesList.add(pb3Session);
            cookiesList.add(v2exLang);
            cookiesList.add(v2exReferrer);
        }
        headers.put(HttpHeaders.COOKIE, cookiesList);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        JSONObject resultJsonObj = new JSONObject(totalPageNumber, true);

        for (int i = 0; i < totalPageNumber; i++) {
            long currentPageStartTime = System.currentTimeMillis();
            String iPlusOne = String.valueOf(i + 1);
            String url = BASE_URL.concat(iPlusOne);
            System.out.print("??????????????? " + (i + 1) + " ????????????url ??? " + url);
            RequestEntity requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(url));
            ResponseEntity responseEntity = restTemplate.exchange(requestEntity, String.class);
            Object responseEntityBody = responseEntity.getBody();
            Document document = Jsoup.parse((String) responseEntityBody);

            Elements elements = document.getElementsByClass(CLASS_NAME);
            String pageNum = "page".concat(iPlusOne);
            List<Map<String, Object>> jsonList = new ArrayList<>();

            // ?????????????????????????????? seq
            int forEachLoopVar = 1;
            for (Element element : elements) {
                String title = element.ownText();
                String href = element.attr("href");

                Map<String, Object> titleInfoMap = new HashMap<>();
                titleInfoMap.put(TOPIC_SEQ, forEachLoopVar);
                titleInfoMap.put(TOPIC_TITLE, title);
                // ???????????? url
                String topicUrl = SITE_BASE_URL.concat(href);
                // ???????????? url ??????
                titleInfoMap.put(TOPIC_URL, topicUrl);

                /*
                ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                ????????????????????????????????????????????????????????????????????????????????????
                ???????????? cpu ????????????????????????????????????????????? v ?????? 403 forbidden ????????????
                 */
                Map resultMap = getFinalTopicCreatedTime(headers, topicUrl);
                if (resultMap != null) {
                    titleInfoMap.put(TOPIC_CREATED_TIME, MapUtils.getString(resultMap, TOPIC_CREATED_TIME));
                    titleInfoMap.put(TOPIC_CREATOR, MapUtils.getString(resultMap, TOPIC_CREATOR));
                }

                jsonList.add(titleInfoMap);
                ++forEachLoopVar;
            }
            long currentPageEndTime = System.currentTimeMillis();
            float currentPageExecTime = (float) (currentPageEndTime - currentPageStartTime) / 1000;
            System.out.println(" ??????????????????????????? " + currentPageExecTime + "s");
            resultJsonObj.put(pageNum, jsonList);
        }
        write2File(resultJsonObj);
        return;
    }

    /**
     * @param * @param resultJsonObj:
     * @return void
     * @author hellodk
     * @description ??? json ??????????????????
     * @date 7/26/2021 12:43 PM
     */
    private void write2File(JSONObject resultJsonObj) {
        String currentPath = System.getProperty("user.dir");
        String filePath = currentPath.concat("/myv2extopicslist.json");
        String jsonString = resultJsonObj.toString();
        File file = new File(filePath);
        try {
            if (file.exists()) {
                file.delete();
            }
            else {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            fileWriter.write(jsonString);
            fileWriter.close();
            System.out.println("done");
            long endTime = System.currentTimeMillis();
            // float execTime = (float) (endTime - startTime) / 1000;
            // long totalMinutes = TimeUnit.MINUTES.toMinutes(endTime - startTime);
            long totalMinutes = (endTime - startTime) / 1000 / 60;
            long totalSeconds = ((endTime - startTime) / 1000 % 60) % 60;
            System.out.println("while fetching your v2ex topics list, total elapsed time is " + totalMinutes + "min " + totalSeconds + "sec");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param *         @param headers:
     * @param topicUrl:
     * @return void
     * @author hellodk
     * @description ?????????????????????????????????
     * @date 7/26/2021 9:45 AM
     */
    private Map getFinalTopicCreatedTime(HttpHeaders headers, String topicUrl) {
        RequestEntity requestEntity = null;
        Map<String, String> resultMap = new HashMap<>();
        try {
            requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(topicUrl));
        }
        catch (URISyntaxException uriSE) {
            uriSE.printStackTrace();
        }
        if (null != requestEntity) {
            ResponseEntity responseEntity = restTemplate.exchange(requestEntity, String.class);
            Object responseEntityBody = responseEntity.getBody();
            Document document = Jsoup.parse((String) responseEntityBody);
            // Get publishedTime from document object
            String publishedTime = document.select("meta[property=article:published_time]").get(0)
                    .attr("content");
            publishedTime = getTopicCreatedTime(publishedTime);
            Element creator = document.select("div.header").first();
            Element tagA = creator.select("a").first();
            // tagA ??????????????????????????????????????????????????????????????????????????? div.header ??? Element ????????? a ????????? href ??????
            if (tagA == null) {
                creator = document.select("div.header").last();
                tagA = creator.select("a").first();
            }
            String creatorString = tagA.attr("href");
            creatorString = creatorString.replace("/member/", "");
            resultMap.put(TOPIC_CREATED_TIME, publishedTime);
            resultMap.put(TOPIC_CREATOR, creatorString);
        }
        return resultMap;
    }

    /**
     * @param * @param timeWithTZ:
     * @return java.lang.String
     * @author hellodk
     * @description ???????????? TZ ????????????????????????????????? 2021-07-23T03:43:21Z??????????????????????????????
     * @date 7/26/2021 10:17 AM
     */
    private String getTopicCreatedTime(String timeWithTZ) {
        //String time = "2021-07-23T03:43:21Z";
        timeWithTZ = timeWithTZ.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
        Date d = null;
        try {
            d = format.parse(timeWithTZ);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        // ????????????date??????????????????????????????????????????Sat Nov 18 15:12:06 CST 2017?????????2017-11-18 15:12:06
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(d);//??????????????????date???????????????
//        System.out.println(d.getTime() / 1000);//????????????????????????????????????long??????????????????????????????????????????????????????????????????1000.
        return sf.format(d);
    }
}
