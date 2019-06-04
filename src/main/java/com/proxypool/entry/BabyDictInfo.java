package com.proxypool.entry;

import com.proxypool.base.BaseEntityInfo;

import javax.persistence.Table;

/**
 * 字典
 */
@Table(name = "t_baby_dict")
public class BabyDictInfo extends BaseEntityInfo {

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
