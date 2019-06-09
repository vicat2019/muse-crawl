package com.proxypool.service;

import com.github.pagehelper.PageInfo;
import com.proxypool.base.ResultData;
import com.proxypool.entry.BabyNameInfo;
import com.proxypool.entry.QueryFormVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BabyNameService {

    ResultData genName();

    PageInfo<BabyNameInfo> queryName(QueryFormVo queryFormVo, HttpServletRequest request, HttpServletResponse response);

    ResultData updateStatus(int id, String status);

    BabyNameInfo getById(Integer id);

    PageInfo<BabyNameInfo> querySelectedList(int page, int size);


}
