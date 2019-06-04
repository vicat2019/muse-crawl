package com.proxypool.entry;

import com.proxypool.base.BaseEntityInfo;

import javax.persistence.Table;

/**
 * 名字
 */
@Table(name = "t_baby_name")
public class BabyNameInfo extends BaseEntityInfo {

    private String name;

    public BabyNameInfo() {}

    public BabyNameInfo(int id) {
        this.id = id;
    }

    public BabyNameInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
