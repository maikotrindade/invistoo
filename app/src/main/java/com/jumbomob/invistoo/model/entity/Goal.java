package com.jumbomob.invistoo.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author maiko.trindade
 * @since 25/07/2016
 */
public class Goal extends RealmObject {

    @PrimaryKey
    private Long id;
    private Double percent;
    private Long assetTypeEnum;
    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetTypeEnum() {
        return assetTypeEnum;
    }

    public void setAssetTypeEnum(Long assetTypeEnum) {
        this.assetTypeEnum = assetTypeEnum;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "assetTypeEnum=" + assetTypeEnum +
                ", id=" + id +
                ", percent=" + percent +
                '}';
    }
}