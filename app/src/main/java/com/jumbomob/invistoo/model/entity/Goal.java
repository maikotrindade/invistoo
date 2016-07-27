package com.jumbomob.invistoo.model.entity;

import io.realm.RealmObject;

/**
 * @author maiko.trindade
 * @since 25/07/2016
 */
public class Goal extends RealmObject {

    private Long id;
    private Double percent;
    private AssetTypeAdapter assetTypeEnum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetTypeAdapter getAssetTypeEnum() {
        return assetTypeEnum;
    }

    public void setAssetTypeEnum(AssetTypeAdapter assetTypeEnum) {
        this.assetTypeEnum = assetTypeEnum;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}