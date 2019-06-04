package com.proxypool.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.proxypool.base.ResultData;
import com.proxypool.entry.BabyNameInfo;
import com.proxypool.service.BabyDictService;
import com.proxypool.service.BabyNameService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    public String getBabyName(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "30") int size, ModelMap modelMap) {
        PageInfo<BabyNameInfo> data = babyNameService.getName(page, size);
        modelMap.addAttribute("data", data);

        String recordIdStr = babyDictService.getValueByName("baba_record");
        if (!StringUtils.isEmpty(recordIdStr)) {
            modelMap.addAttribute("record", Integer.valueOf(recordIdStr));
        } else {
            modelMap.addAttribute("baba_record", 0);
        }

        // 过滤内容
        String secondFilter = babyDictService.getValueByName("secondFilter");
        String thirdFilter = babyDictService.getValueByName("thirdFilter");
        modelMap.put("secondFilter", secondFilter);
        modelMap.put("thirdFilter", thirdFilter);

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

    @RequestMapping("/uninterested")
    @ResponseBody
    public ResultData beUnInterested(int id) {
        return babyNameService.updateStatus(id, "2");
    }

    @RequestMapping("/ajax/record/{id}")
    @ResponseBody
    public ResultData record(@PathVariable int id) {
        return babyDictService.record(id, 1);
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
