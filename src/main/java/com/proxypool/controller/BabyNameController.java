package com.proxypool.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.proxypool.base.ResultData;
import com.proxypool.entry.BabyNameInfo;
import com.proxypool.entry.QueryFormVo;
import com.proxypool.service.BabyDictService;
import com.proxypool.service.BabyNameService;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BabyNameController {
    private Logger log = LoggerFactory.getLogger("BabyNameController");

    @Autowired
    private BabyNameService babyNameService;

    @Autowired
    private BabyDictService babyDictService;

    /**
     * 生成列表
     * @return
     */
    @RequestMapping("/gen")
    @ResponseBody
    public ResultData genBabyName() {
        // 生成
        return babyNameService.genName();
    }

    /**
     * 分页查询
     * @param queryFormVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String getBabyName(QueryFormVo queryFormVo, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

        // 查询
        PageInfo<BabyNameInfo> data = babyNameService.getName(queryFormVo, request, response);
        modelMap.addAttribute("data", data);

        // 设置显示内容
        String recordIdStr = babyDictService.getValueByName("baba_record");
        if (!StringUtils.isEmpty(recordIdStr)) {
            modelMap.addAttribute("record", Integer.valueOf(recordIdStr));
        } else {
            modelMap.addAttribute("baba_record", 0);
        }

        modelMap.put("secondFilter", queryFormVo.getSecondFilter());
        modelMap.put("thirdFilter", queryFormVo.getThirdFilter());

        modelMap.put("name", queryFormVo.getName());

        return "bbn/index";
    }

    /**
     * Ajax分页查询
     * @param queryFormVo
     * @return
     */
    @RequestMapping("/ajax/list")
    @ResponseBody
    public PageInfo<BabyNameInfo> ajaxQuery(QueryFormVo queryFormVo, HttpServletRequest request, HttpServletResponse response) {
        return babyNameService.getName(queryFormVo, request, response);
    }

    /**
     * 感兴趣
     * @param id
     * @return
     */
    @RequestMapping("/interested")
    @ResponseBody
    public ResultData beInterested(int id) {
        return babyNameService.updateStatus(id, "1");
    }

    /**
     * 不感兴趣
     * @param id
     * @return
     */
    @RequestMapping("/uninterested")
    @ResponseBody
    public ResultData beUnInterested(int id) {
        return babyNameService.updateStatus(id, "0");
    }

    /**
     * Ajax记录位置
     * @param id
     * @return
     */
    @RequestMapping("/ajax/record/{id}")
    @ResponseBody
    public ResultData record(@PathVariable int id) {
        return babyDictService.record(id, 1);
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/del")
    public ResultData del(int id) {
        return babyNameService.updateStatus(id, "2");
    }

    /**
     * 添加过滤
     * @param type
     * @param filter
     * @return
     */
    @RequestMapping("/addFilter/{type}")
    public ResultData addFilter(@PathVariable String type, String filter) {
        return babyDictService.updateFilter(type, filter);
    }

    /**
     * 测试
     * @param id
     * @return
     */
    @RequestMapping("/test/{id}")
    public BabyNameInfo test(@PathVariable Integer id) {

        BabyNameInfo name = babyNameService.getById(id);
        log.info(JSON.toJSONString(name));

        return name;
    }








}
