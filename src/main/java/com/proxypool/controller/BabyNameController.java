package com.proxypool.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.proxypool.base.ResultData;
import com.proxypool.entry.BabyNameInfo;
import com.proxypool.service.BabyDictService;
import com.proxypool.service.BabyNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bbn")
@Controller
public class BabyNameController {
    private Logger log = LoggerFactory.getLogger("BabyNameController");

    @Autowired
    private BabyNameService babyNameService;

    @Autowired
    private BabyDictService babyDictService;

    @RequestMapping("/gen")
    public ResultData genBabyName() {
        // 生成
        return babyNameService.genName();
    }

    @RequestMapping("/list")
    public String getBabyName(int page, int size, ModelMap modelMap) {
        PageInfo<BabyNameInfo> data = babyNameService.getName(page, size);
        modelMap.addAttribute("data", data);

        System.out.println(JSON.toJSONString(data));

        return "bbn/index";
    }

    @RequestMapping("/ajax/list")
    @ResponseBody
    public PageInfo<BabyNameInfo> ajaxQuery(int page, int size) {
        return babyNameService.getName(page, size);
    }

    @RequestMapping("/interested")
    @ResponseBody
    public ResultData beInterested(int id) {
        return babyNameService.updateStatus(id, "1");
    }

    @RequestMapping("/del")
    public ResultData del(int id) {
        return babyNameService.updateStatus(id, "2");
    }

    @RequestMapping("/addFilter/{type}")
    public ResultData addFilter(@PathVariable String type, String filter) {
        return babyDictService.updateFilter(type, filter);
    }

    @RequestMapping("/test/{id}")
    public BabyNameInfo test(@PathVariable Integer id) {

        BabyNameInfo name = babyNameService.getById(id);
        log.info(JSON.toJSONString(name));

        return name;
    }








}
