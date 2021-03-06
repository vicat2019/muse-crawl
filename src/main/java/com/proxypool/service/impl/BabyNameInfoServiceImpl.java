package com.proxypool.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
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
import com.proxypool.util.TextUtils;
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
import tk.mybatis.mapper.entity.Example;

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
     *
     * @return 生成名称集合
     */
    private Set<String> genNameBy() {
        Set<String> nameSet = Sets.newHashSet();

        String firstName = babyDictService.getValueByName("firstName");
        String nameSource = babyDictService.getValueByName("nameSource");

        for (int i = 0; i < loopCount; i++) {
            nameSet.add(GenRandomPersonInfo.getChineseName(firstName, nameSource));
        }
        log.info("生成名字个数=" + nameSet.size());

        String secondFilter = babyDictService.getValueByName("secondFilter");
        String thirdFilter = babyDictService.getValueByName("thirdFilter");
        GenRandomPersonInfo.nameFileter(nameSet, secondFilter, thirdFilter);
        log.info("去重后剩余个数=" + nameSet.size());

        return nameSet;
    }

    @Override
    public PageInfo<BabyNameInfo> queryName(QueryFormVo queryFormVo, HttpServletRequest request, HttpServletResponse response) {

        // 获取参数
        getDataFromCookie(queryFormVo, request);

        // 按照条件查询
        handleQueryParam(queryFormVo);

        // 查询数据
        List<BabyNameInfo> dataList = mapper.multiQuery(queryFormVo);
        log.info("查询返回的个数=" + dataList.size());

        // 查询总数
        int totalCount = mapper.multiQueryCount(queryFormVo);
        // 计算分页信息
        int pageCount = (totalCount % queryFormVo.getSize() == 0) ? totalCount / queryFormVo.getSize() : (totalCount / queryFormVo.getSize() + 1);
        // 生成拼音
        if (!CollectionUtils.isEmpty(dataList)) {
            dataList.forEach(item -> {
                // 生成拼音
                item.setPinYin(getPinYin(item.getName()));
                // 是被记录的数据
                item.setBeRecord(item.getId() == queryFormVo.getStartRecordId());
            });
        }

        // 显示记录的ID
        if (queryFormVo.isFirstIn()) {
            int recordId = queryFormVo.getStartRecordId();
            if (recordId != 0) {
                for (BabyNameInfo item : dataList) {
                    if (recordId <= item.getId()) {
                        item.setBeRecord(true);
                        break;
                    }
                }
            }
        }

        // 存储查询条件
        storeParam(response, queryFormVo);

        // 返回结果
        return genPageInfo(dataList, queryFormVo.getPage(), queryFormVo.getSize(), totalCount, pageCount);
    }

    /**
     * 处理请求参数
     *
     * @param queryFormVo 请求参数
     */
    private void handleQueryParam(QueryFormVo queryFormVo) {

        // 记录ID
        String recordStr = babyDictService.getValueByName("record");
        if (NumberUtils.isDigits(recordStr)) {
            queryFormVo.setStartRecordId(Integer.parseInt(recordStr));
        } else {
            queryFormVo.setStartRecordId(0);
        }

        if (queryFormVo.getPage() == 0) {
            queryFormVo.setFirstIn(true);

            // 优先显示记录ID所在页
            if (queryFormVo.getStartRecordId() != 0) {
                // 计算过滤后记录的数据在哪一页数据内
                int count = mapper.getMatchCount(queryFormVo);
                log.info("查询小于" + queryFormVo.getStartRecordId() + "的记录数=" + count);

                int pageNum = count / queryFormVo.getSize() + 1;
                queryFormVo.setPage(pageNum);

                log.info("计算出记录的id所在的页码=" + pageNum);

            } else {
                // 再显示Cookie记录页
                queryFormVo.setPage(queryFormVo.getCookiePage());
            }
        } else {
            queryFormVo.setFirstIn(false);
        }

        // 计算数量
        int start = (queryFormVo.getPage() - 1) * queryFormVo.getSize();
        queryFormVo.setStart(start);
    }

    /**
     * 生成返回分页信息
     *
     * @param dataList   数据集合
     * @param page       页码
     * @param size       每页记录数
     * @param totalCount 总记录
     * @param pageCount  总页数
     * @return PageInfo
     */
    private PageInfo<BabyNameInfo> genPageInfo(List<BabyNameInfo> dataList, int page, int size, int totalCount, int pageCount) {
        // 生成分页信息返回
        PageInfo<BabyNameInfo> pageInfo = new PageInfo<>();
        pageInfo.setList(dataList);
        pageInfo.setPageNum(page);
        pageInfo.setNextPage(((page + 1) > pageCount) ? pageCount : (page + 1));
        pageInfo.setTotal(totalCount);
        pageInfo.setPages(pageCount);
        pageInfo.setPageSize(size);
        return pageInfo;
    }

    /**
     * 生成拼音
     *
     * @param name 名称
     * @return String
     */
    private String getPinYin(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }

        // 设置汉子拼音输出的格式
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        // 该方法的作用是返回一个字符数组，该字符数组中存放了当前字符串中的所有字符
        char[] nameAr = name.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char alpha : nameAr) {
            try {
                String[] py = PinyinHelper.toHanyuPinyinStringArray(alpha, format);
                result.append(firstUpperCase(py[0]));
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }

        return result.toString();
    }

    /**
     * 首字母大写
     *
     * @param pinyin 拼音
     * @return String
     */
    private String firstUpperCase(String pinyin) {
        if (!StringUtils.isEmpty(pinyin)) {
            return pinyin.substring(0, 1).toUpperCase() + pinyin.substring(1, pinyin.length());
        }
        return pinyin;
    }

    /**
     * 在Cookie中存储查询信息
     *
     * @param response    响应对象
     * @param queryFormVo 查询数据
     */
    private void storeParam(HttpServletResponse response, QueryFormVo queryFormVo) {
        // 页码
        CookieUtils.writeCookie(response, "page", String.valueOf(queryFormVo.getPage()));
        // 每页记录数
        CookieUtils.writeCookie(response, "size", String.valueOf(queryFormVo.getSize()));

        // 更新过滤字
        log.info("更新查询过滤字=" + babyDictService.updateFilter("filter", queryFormVo.getFilter()));
    }

    /**
     * 从Cookie初始化参数
     *
     * @param queryFormVo 查询对象
     * @param request     请求对象
     */
    public void getDataFromCookie(QueryFormVo queryFormVo, HttpServletRequest request) {
        // filter
        String totalFilter;
        // 库中的过滤条件
        if (StringUtils.isEmpty(queryFormVo.getFilter())) {
            totalFilter = babyDictService.getValueByName("filter");
        } else {
            totalFilter = queryFormVo.getFilter();
        }

        // 去重
        Set<String> filterSet = Sets.newHashSet();
        for (int i=0;i<totalFilter.length();i++) {
            filterSet.add(String.valueOf(totalFilter.charAt(i)));
        }
        queryFormVo.setFilter(Joiner.on("").join(filterSet));
        queryFormVo.setFilterList(Lists.newArrayList(filterSet));
        log.info("getDataFromCookie() filter=" + queryFormVo.getFilter());

        // Cookie中的页码
        String page = CookieUtils.getCookie(request, "page");
        if (NumberUtils.isDigits(page)) {
            queryFormVo.setCookiePage(Integer.parseInt(page));
        } else {
            queryFormVo.setCookiePage(1);
        }

        // 每页记录数
        if (queryFormVo.getSize() == 0) {
            String size = CookieUtils.getCookie(request, "size");
            if (NumberUtils.isDigits(size)) {
                queryFormVo.setSize(Integer.parseInt(size));
            } else {
                queryFormVo.setSize(QueryFormVo.PAGE_SIZE);
            }
        }

        log.info("获取参数=" + JSON.toJSONString(queryFormVo));
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

    @Override
    public PageInfo<BabyNameInfo> querySelectedList(int page, int size) {
        PageHelper.startPage(page, size);
        List<BabyNameInfo> dataList = mapper.querySelected();

        dataList.forEach(item -> item.setPinYin(getPinYin(item.getName())));

        PageInfo<BabyNameInfo> pageInfo = new PageInfo<>(dataList);
        return pageInfo;
    }


}
