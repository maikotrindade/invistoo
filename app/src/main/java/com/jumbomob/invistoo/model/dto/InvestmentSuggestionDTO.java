package com.jumbomob.invistoo.model.dto;

/**
 * @author maiko.trindade
 * @since 16/08/2016
 */
public class InvestmentSuggestionDTO {

    private Double total;
    private Long assetType;
    private Long suggestion;

    public Long getAssetType() {
        return assetType;
    }

    public void setAssetType(Long assetType) {
        this.assetType = assetType;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Long suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public String toString() {
        return "InvestmentSuggestionDTO{" +
                "assetType=" + assetType +
                ", total=" + total +
                ", suggestion=" + suggestion +
                '}';
    }
}
