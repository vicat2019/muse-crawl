package com.proxypool.dao;


import com.proxypool.entry.BabyNameInfo;
import com.proxypool.entry.QueryFormVo;
import org.apache.ibatis.annotations.Param;
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

    List<BabyNameInfo> multiQuery(@Param("queryFormVo") QueryFormVo queryFormVo);

    int multiQueryCount(@Param("queryFormVo") QueryFormVo queryFormVo);

    int getMatchCount(@Param("queryFormVo") QueryFormVo queryFormVo);

    List<BabyNameInfo> querySelected();

}