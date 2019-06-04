package com.proxypool.dao;


import com.proxypool.entry.BabyDictInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Description: 序列信息DAO类
 * @Author: Vincent
 * @Date: 2019/1/25
 */
public interface BabyDictInfoMapper extends Mapper<BabyDictInfo> {

    int addFilter(@Param("name") String name, @Param("value") String value);

}