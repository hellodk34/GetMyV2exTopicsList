package com.hellodk.getv2exmytopics;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.hellodk.getv2exmytopics.config.UserCookiesVerify;
import com.hellodk.getv2exmytopics.service.MainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//@SpringBootApplication(scanBasePackages = {"com.hellodk"})
@SpringBootApplication
public class Getv2exmytopicsApplication {

//    @Autowired
//    private static MainService mainService;

    public static void main(String[] args) throws URISyntaxException, IOException {
//        String proxyHost = "127.0.0.1";
//        String proxyPort = "7890";
//        System.setProperty("socksProxyHost", proxyHost);
//        System.setProperty("socksProxyPort", proxyPort);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
        Date date = new Date();
        System.out.println("current time is " + sdf.format(date));
        int length = args.length;
        if (length < 1) {
            System.out.println("program arguments is empty, start to read config.json");
            String configJsonFilePath = "/config.json";
            ClassPathResource classPathResource = new ClassPathResource(configJsonFilePath);
            InputStream configJson = classPathResource.getInputStream();
            if (configJson == null) {
                throw new RuntimeException("read config.json file failed");
            }
            else {
                JSONObject json = JSONObject.parseObject(configJson, JSONObject.class, Feature.OrderedField);
                System.out.println("the config.json file's content is " + json.toJSONString());
                Object[] arguments = new Object[json.size()];
                if (json.size() == 5) {
                    int i = 0;
                    for (Map.Entry key : json.entrySet()) {
                        arguments[i] = key.getValue();
                        ++i;
                    }
                    UserCookiesVerify.verify(((Integer) arguments[0]), (String) arguments[1], (String) arguments[2], (String) arguments[3], (String) arguments[4]);

                }
                else if (json.size() == 6) {
                    int i = 0;
                    for (Map.Entry key : json.entrySet()) {
                        arguments[i] = key.getValue();
                        ++i;
                    }
                    UserCookiesVerify.verify(((Integer) arguments[0]), (String) arguments[1], (String) arguments[2], (String) arguments[3], (String) arguments[4], (String) arguments[5]);
                }
            }
            UserCookiesVerify.setLoadConfigFile(true);
        }
        else if (length < 2) {
            System.out.println("please input at least 2 variables");
            return;
        }
        else if (length == 5) {
            UserCookiesVerify.verify(Integer.parseInt(args[0]), args[1], args[2], args[3], args[4]);
            UserCookiesVerify.setLoadConfigFile(false);
        }
        else if (length == 6) {
            UserCookiesVerify.verify(Integer.parseInt(args[0]), args[1], args[2], args[3], args[4], args[5]);
            UserCookiesVerify.setLoadConfigFile(false);
        }

        ConfigurableApplicationContext context = SpringApplication.run(Getv2exmytopicsApplication.class, args);
        MainService mainService = context.getBean(MainService.class);
        mainService.sendGet();

        // 执行一次后就可以关闭 Spring Boot 程序了
        try {
            // 当终端出现 while fetching your v2ex topics list, elapsed time is 提示时，睡眠 3 秒后程序终止
            TimeUnit.SECONDS.sleep(3);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.close();
    }
}
