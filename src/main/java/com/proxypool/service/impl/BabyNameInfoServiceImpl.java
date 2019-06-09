package com.proxypool.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.proxypool.base.BaseService;
import com.proxypool.base.ResultData;
import com.proxypool.dao.BabyNameInfoMapper;
import com.proxypool.entry.BabyNameInfo;
import com.proxypool.entry.QueryFormVo;
import com.proxypool.service.BabyDictService;
import com.proxypool.service.BabyNameService;
import com.proxypool.util.CookieUtils;
import com.proxypool.util.GenRandomPersonInfo;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;


/**
 * Created by Vincent on 2018/12/17.
 */
@Service("babyNameService")
@Transactional
public class BabyNameInfoServiceImpl extends BaseService<BabyNameInfoMapper, BabyNameInfo> implements BabyNameService {
    private Logger log = LoggerFactory.getLogger("BabyNameInfoServiceImpl");

    @Autowired
    private BabyDictService babyDictService;

    // 循环次数
    private static final int loopCount = 10000000;


    @Override
    public ResultData genName() {
        // 生成名字
        Set<String> nameSet = genNameBy();

        // 拆分指定大小
        List<List<String>> nameLists = Lists.partition(Lists.newArrayList(nameSet.iterator()), 1000);

        // 添加到数据库中
        int count = 0;
        for (List<String> nameList : nameLists) {
            int tempCount = mapper.insertBatch(nameList);
            count += tempCount;
            log.info("添加到数据库=" + tempCount);
        }

        ResultData result = ResultData.getSuccessResult();
        result.setData(count);
        return result;
    }

    /**
     * 生成名字
     */
    private Set<String> genNameBy() {
        Set<String> nameSet = Sets.newHashSet();

        String firstName = babyDictService.getValueByName("firstName");
        String nameSource = babyDictService.getValueByName("nameSource2");

        for (int i = 0; i < loopCount; i++) {
            nameSet.add(GenRandomPersonInfo.getChineseName(firstName, nameSource));
        }

        String secondFilter = babyDictService.getValueByName("secondFilter");
        String thridFilter = babyDictService.getValueByName("thirdFilter");
        log.info("生成名字个数=" + nameSet.size());
        GenRandomPersonInfo.nameFileter(nameSet, secondFilter, thridFilter);

        log.info("去重后剩余个数=" + nameSet.size());

        return nameSet;
    }

    @Override
    public PageInfo<BabyNameInfo> getName(QueryFormVo queryFormVo, HttpServletRequest request, HttpServletResponse response) {

        // 过滤字
        if (StringUtils.isEmpty(queryFormVo.getSecondFilter())) {
            queryFormVo.setSecondFilter(babyDictService.getValueByName("secondFilter"));
        }
        if (StringUtils.isEmpty(queryFormVo.getThirdFilter())) {
            queryFormVo.setThirdFilter(babyDictService.getValueByName("thirdFilter"));
        }
        if (queryFormVo.getSize() == 0) {
            queryFormVo.setSize(100);
        }

        // Cookie中的页码
        String page = CookieUtils.getCookie(request, "page");
        if (queryFormVo.getPage() == 0) {
            if (NumberUtils.isDigits(page)) {
                int cookiePage = Integer.parseInt(page);
                queryFormVo.setPage(cookiePage);
            } else {
                queryFormVo.setPage(1);
            }
        }

        // 按照条件查询
        List<String> secondFilterList = Lists.newArrayList();
        if (!StringUtils.isEmpty(queryFormVo.getSecondFilter())) {
            for (int i=0; i<queryFormVo.getSecondFilter().length(); i++) {
                secondFilterList.add(String.valueOf(queryFormVo.getSecondFilter().charAt(i)));
            }
        }
        queryFormVo.setSecondFilterList(secondFilterList);

        List<String> thirdFilterList = Lists.newArrayList();
        if (!StringUtils.isEmpty(queryFormVo.getThirdFilter())) {
            for (int i = 0; i<queryFormVo.getThirdFilter().length(); i++) {
                thirdFilterList.add(String.valueOf(queryFormVo.getThirdFilter().charAt(i)));
            }
        }
        queryFormVo.setThirdFilterList(thirdFilterList);

        // 计算数量
        int start = (queryFormVo.getPage() - 1) * queryFormVo.getSize();

        // 查询数据
        List<BabyNameInfo> dataList = mapper.multiQuery(secondFilterList, thirdFilterList, queryFormVo.getName(),
                start, queryFormVo.getSize());
        log.info("查询返回的个数=" + dataList.size());

        // 查询总数
        int totalCount = mapper.multiQueryCount(queryFormVo);

        // 计算分页信息
        int pageCount = (totalCount % queryFormVo.getSize() == 0)?totalCount/queryFormVo.getSize() : (totalCount/queryFormVo.getSize() + 1);

        // 生成拼音
        if (!CollectionUtils.isEmpty(dataList)) {
            dataList.forEach(item -> item.setPinYin(getPinYin(item.getName())));
        }

        PageInfo<BabyNameInfo> pageInfo = new PageInfo<>();
        pageInfo.setList(dataList);
        pageInfo.setPageNum(queryFormVo.getPage());
        pageInfo.setNextPage(((queryFormVo.getPage() + 1) > pageCount) ? pageCount : (queryFormVo.getPage() + 1));
        pageInfo.setTotal(totalCount);
        pageInfo.setPages(pageCount);
        pageInfo.setPageSize(queryFormVo.getSize());

        CookieUtils.writeCookie(response, "page", String.valueOf(queryFormVo.getPage()));
        log.info("写入Cookie中page=" + queryFormVo.getPage());

        log.info(JSON.toJSONString(pageInfo));

        return pageInfo;
    }

    private String getPinYin(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }

        // 设置汉子拼音输出的格式
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        //该方法的作用是返回一个字符数组，该字符数组中存放了当前字符串中的所有字符
        char[] nameAr = name.toCharArray();

        StringBuilder result = new StringBuilder();

        for (char alpha : nameAr) {
            try {
                String[] py = PinyinHelper.toHanyuPinyinStringArray(alpha, format);
                result.append(firstUpcase(py[0]));

            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }

        return result.toString();
    }

    private String firstUpcase(String pinyin) {
        if (!StringUtils.isEmpty(pinyin)) {
            return pinyin.substring(0,1).toUpperCase() + pinyin.substring(1, pinyin.length());
        }
        return pinyin;
    }

    @Override
    public ResultData updateStatus(int id, String status) {

        BabyNameInfo babyNameInfo = mapper.selectByPrimaryKey(id);
        if (babyNameInfo == null) {
            return ResultData.getErrResult("不存在");
        }

        babyNameInfo.setStatus(status);
        int updateResult = mapper.updateByPrimaryKey(babyNameInfo);
        log.info("修改成功=" + updateResult);

        return ResultData.getSuccessResult();
    }

    @Override
    public BabyNameInfo getById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }
}
