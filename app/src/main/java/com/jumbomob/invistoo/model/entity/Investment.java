package com.jumbomob.invistoo.model.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author maiko.trindade
 * @since 14/02/2016
 */
public class Investment extends RealmObject {

    @PrimaryKey
    private Long id;
    private String name;
    private double quantity;
    private String price;
    private Date creationDate;
    private Date updateDate;
    private Date removedDate;
    private Long assetType;
    private Long assetStatus;
    private boolean isActive;
    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Date getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(Date removedDate) {
        this.removedDate = removedDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetTypeEnum assetType) {
        this.assetType = assetType.getId();
    }

    public Long getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(AssetStatusEnum assetStatus) {
        this.assetStatus = assetStatus.getId();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Investment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price='" + price + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", removedDate='" + removedDate + '\'' +
                ", assetType=" + assetType +
                ", assetStatus=" + assetStatus +
                ", isActive=" + isActive +
                '}';
    }
}
