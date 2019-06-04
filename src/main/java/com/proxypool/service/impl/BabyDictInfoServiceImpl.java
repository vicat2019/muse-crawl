package com.proxypool.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.proxypool.base.BaseService;
import com.proxypool.base.ResultData;
import com.proxypool.dao.BabyDictInfoMapper;
import com.proxypool.entry.BabyDictInfo;
import com.proxypool.service.BabyDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Vincent on 2018/12/17.
 */
@Service("babyDictService")
@Transactional
public class BabyDictInfoServiceImpl extends BaseService<BabyDictInfoMapper, BabyDictInfo> implements BabyDictService {

    @Override
    public List<BabyDictInfo> getAllDict() {
        return mapper.selectAll();
    }

    /**
     * 获取指定名称的字典值
     *
     * @param name 字典名称
     * @return String
     */
    @Override
    public String getValueByName(String name) {
        List<BabyDictInfo> dictList = getAllDict();
        for (BabyDictInfo dict : dictList) {
            if (dict.getName().equals(name)) {
                return dict.getValue();
            }
        }

        return "";
    }

    @Override
    public ResultData updateFilter(String type, String filter) {
        String name = "";
        if (type.equals("2")) {
            name = "secondFilter";
        } else if (type.equals("3")) {
            name = "thirdFilter";
        }

        String allFilter = getValueByName(name) + filter;
        Set<Character> filterSet = Sets.newHashSet();
        for (int i = 0; i < allFilter.length(); i++) {
            filterSet.add(allFilter.charAt(i));
        }

        String newFilter = Joiner.on("").join(filterSet.iterator());
        int count = mapper.addFilter(name, newFilter);

        if (count > 0) {
            return ResultData.getSuccessResult();
        } else {
            return ResultData.getErrResult();
        }
    }

    @Override
    public ResultData record(int id, int type) {
        if (id == 0) {
            return ResultData.getErrResult("参数异常");
        }

        String name = type==1?"baba_record":"mama_record";

        // 设置值
        Example example = new Example(BabyDictInfo.class);
        example.createCriteria().andEqualTo("name", "baba_record");

        BabyDictInfo babyDictInfo = new BabyDictInfo();
        babyDictInfo.setValue(String.valueOf(id));

        int count = mapper.updateByExampleSelective(babyDictInfo, example);

        return (count>0?ResultData.getSuccessResult("记录成功"):ResultData.getErrResult("记录失败"));
    }

    @Override
    public int updateDict(BabyDictInfo babyDictInfo) {
        return mapper.updateByPrimaryKey(babyDictInfo);
    }
}
