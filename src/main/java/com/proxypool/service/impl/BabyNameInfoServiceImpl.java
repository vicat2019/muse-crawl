package com.proxypool.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.proxypool.base.BaseService;
import com.proxypool.base.ResultData;
import com.proxypool.dao.BabyNameInfoMapper;
import com.proxypool.dao.RpSequenceInfoMapper;
import com.proxypool.entry.BabyNameInfo;
import com.proxypool.entry.RpSequenceInfo;
import com.proxypool.service.BabyDictService;
import com.proxypool.service.BabyNameService;
import com.proxypool.service.RpSequenceInfoService;
import com.proxypool.util.GenRandomPersonInfo;
import com.proxypool.util.RedisUtil;
import com.proxypool.util.SequenceUtil;
import com.proxypool.util.TextUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;


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
        String nameSource = babyDictService.getValueByName("nameSource");

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
    public PageInfo<BabyNameInfo> getName(int page, int size) {
        PageHelper.startPage(page, size);

        List<BabyNameInfo> dataList = mapper.selectAll();

        return new PageInfo<>(dataList);
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
