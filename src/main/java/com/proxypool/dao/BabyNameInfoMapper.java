package com.proxypool.dao;


import com.proxypool.entry.BabyNameInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Description: 序列信息DAO类
 * @Author: Vincent
 * @Date: 2019/1/25
 */
public interface BabyNameInfoMapper extends Mapper<BabyNameInfo> {

    int insertBatch(List<String> babyNameList);

    List<BabyNameInfo> queryBy(BabyNameInfo babyNameInfo);

}