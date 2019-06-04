package com.proxypool.service;

import com.proxypool.base.ResultData;
import com.proxypool.entry.BabyDictInfo;

import java.util.List;

public interface BabyDictService {

    List<BabyDictInfo> getAllDict();

    int updateDict(BabyDictInfo babyDictInfo);

    String getValueByName(String name);

    ResultData updateFilter(String type, String filter);


}
