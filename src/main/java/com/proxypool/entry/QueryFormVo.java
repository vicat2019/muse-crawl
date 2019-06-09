package com.proxypool.entry;

import java.io.Serializable;
import java.util.List;

public class QueryFormVo implements Serializable {

    private String name;

    private String filter;

    private String secondFilter;

    private String thirdFilter;

    private int page = 0;

    private int size = 500;

    private List<String> secondFilterList;

    private List<String> thirdFilterList;


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
}
