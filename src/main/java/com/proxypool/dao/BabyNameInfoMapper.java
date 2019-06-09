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

    List<BabyNameInfo> multiQuery(@Param("secondList") List<String> secondList,
                                  @Param("thirdList") List<String> thirdList,
                                  @Param("name") String name,
                                  @Param("start") int start,
                                  @Param("size") int size
    );

    int multiQueryCount(QueryFormVo queryFormVo);

}