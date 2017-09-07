package com.jumbomob.invistoo.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author maiko.trindade
 * @since 16/08/2016
 */
public class InvestmentSuggestionDTO implements Parcelable {

    private Double total;
    private Long assetType;
    private Double suggestion;

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

    public Double getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Double suggestion) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.total);
        dest.writeValue(this.assetType);
        dest.writeValue(this.suggestion);
    }

    public InvestmentSuggestionDTO() {
    }

    protected InvestmentSuggestionDTO(Parcel in) {
        this.total = (Double) in.readValue(Double.class.getClassLoader());
        this.assetType = (Long) in.readValue(Long.class.getClassLoader());
        this.suggestion = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<InvestmentSuggestionDTO> CREATOR = new Parcelable.Creator<InvestmentSuggestionDTO>() {
        @Override
        public InvestmentSuggestionDTO createFromParcel(Parcel source) {
            return new InvestmentSuggestionDTO(source);
        }

        @Override
        public InvestmentSuggestionDTO[] newArray(int size) {
            return new InvestmentSuggestionDTO[size];
        }
    };
}
