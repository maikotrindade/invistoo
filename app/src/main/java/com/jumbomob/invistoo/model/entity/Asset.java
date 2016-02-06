package com.jumbomob.invistoo.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author maiko.trindade
 * @since 04/02/2016
 */
public class Asset {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("buyTax")
    @Expose
    private String buyTax;
    @SerializedName("sellTax")
    @Expose
    private String sellTax;
    @SerializedName("buyPrice")
    @Expose
    private String buyPrice;
    @SerializedName("sellPrice")
    @Expose
    private String sellPrice;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;

    /**
     * @return The Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate The dueDate
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return The buyTax
     */
    public String getBuyTax() {
        return buyTax;
    }

    /**
     * @param buyTax The buyTax
     */
    public void setBuyTax(String buyTax) {
        this.buyTax = buyTax;
    }

    /**
     * @return The sellTax
     */
    public String getSellTax() {
        return sellTax;
    }

    /**
     * @param sellTax The sellTax
     */
    public void setSellTax(String sellTax) {
        this.sellTax = sellTax;
    }

    /**
     * @return The buyPrice
     */
    public String getBuyPrice() {
        return buyPrice;
    }

    /**
     * @param buyPrice The buyPrice
     */
    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    /**
     * @return The sellPrice
     */
    public String getSellPrice() {
        return sellPrice;
    }

    /**
     * @param sellPrice The sellPrice
     */
    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * @return The lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate The lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}