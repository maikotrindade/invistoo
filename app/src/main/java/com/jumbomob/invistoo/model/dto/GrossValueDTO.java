package com.jumbomob.invistoo.model.dto;

/**
 * Created by trindade on 11/03/17.
 */

public class GrossValueDTO {

    private Long assetType;
    private Double amount;
    private String userId;

    public GrossValueDTO() {
    }

    public GrossValueDTO(Long assetType, Double amount, String userId) {
        this.assetType = assetType;
        this.amount = amount;
        this.userId = userId;
    }

    public Long getAssetType() {
        return assetType;
    }

    public void setAssetType(Long assetType) {
        this.assetType = assetType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
