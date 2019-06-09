package com.proxypool.entry;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

public class QueryFormVo implements Serializable {

    public static final int PAGE_SIZE = 500;

    private String name;

    private String filter;

    private String secondFilter;

    private String thirdFilter;

    private int page = 0;

    private int size = PAGE_SIZE;

    private int start;

    private List<String> secondFilterList;

    private List<String> thirdFilterList;

    private List<String> filterList;


    /**
     * 转换成集合
     *
     * @return List
     */
    public List<String> convertToList(String filterStr) {
        if (StringUtils.isEmpty(filterStr)) {
            return Lists.newArrayList();
        }

        List<String> filterList = Lists.newArrayList();
        if (!StringUtils.isEmpty(filterStr)) {
            for (int i = 0; i < filterStr.length(); i++) {
                filterList.add(String.valueOf(filterStr.charAt(i)));
            }
        }
        return filterList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSecondFilter() {
        return secondFilter;
    }

    public void setSecondFilter(String secondFilter) {
        this.secondFilter = secondFilter;
    }

    public String getThirdFilter() {
        return thirdFilter;
    }

    public void setThirdFilter(String thirdFilter) {
        this.thirdFilter = thirdFilter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getSecondFilterList() {
        return secondFilterList;
    }

    public void setSecondFilterList(List<String> secondFilterList) {
        this.secondFilterList = secondFilterList;
    }

    public List<String> getThirdFilterList() {
        return thirdFilterList;
    }

    public void setThirdFilterList(List<String> thirdFilterList) {
        this.thirdFilterList = thirdFilterList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<String> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<String> filterList) {
        this.filterList = filterList;
    }
}
