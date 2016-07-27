package com.jumbomob.invistoo.model.entity;

/**
 * @author maiko.trindade
 * @since 25/07/2016
 */
public class Goal {

    private Double percent;
    private AssetTypeEnum assetTypeEnum;

    public AssetTypeEnum getAssetTypeEnum() {
        return assetTypeEnum;
    }

    public void setAssetTypeEnum(AssetTypeEnum assetTypeEnum) {
        this.assetTypeEnum = assetTypeEnum;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
