package com.hellodk.getv2exmytopics.controller;

import com.alibaba.fastjson.JSONObject;
import com.hellodk.getv2exmytopics.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

/**
 * @author hellodk
 * @date 2021-07-25 18:23
 */

@RestController
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public JSONObject testGetRequest() throws URISyntaxException {
        return mainService.sendGet();
    }

}
