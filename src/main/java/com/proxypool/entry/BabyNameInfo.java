package com.proxypool.entry;

import com.proxypool.base.BaseEntityInfo;

import javax.persistence.FieldResult;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 名字
 */
@Table(name = "t_baby_name")
public class BabyNameInfo extends BaseEntityInfo {

    private String name;

    private String pinYin;

    @Transient
    private boolean beRecord;


    /**
     * 默认构造方法
     */
    public BabyNameInfo() {
    }

    /**
     * 构造方法
     *
     * @param id
     */
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

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public boolean getBeRecord() {
        return beRecord;
    }

    public void setBeRecord(boolean beRecord) {
        this.beRecord = beRecord;
    }
}
