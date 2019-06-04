package com.proxypool.service;

import com.github.pagehelper.PageInfo;
import com.proxypool.base.ResultData;
import com.proxypool.entry.BabyNameInfo;

public interface BabyNameService {

    ResultData genName();

    PageInfo<BabyNameInfo> getName(int page, int size);

    ResultData updateStatus(int id, String status);

    BabyNameInfo getById(Integer id);

}
