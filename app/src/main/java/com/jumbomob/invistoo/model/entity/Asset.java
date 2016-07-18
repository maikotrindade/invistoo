package com.jumbomob.invistoo.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author maiko.trindade
 * @since 04/02/2016
 */
public class Asset extends RealmObject {

    @SerializedName("_id")
    @Expose
    @PrimaryKey
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("dueDate")
    @Expose
    private String dueDate;

    @SerializedName("indexer")
    @Expose
    private String indexer;

    @SerializedName("annualBuyPrice")
    @Expose
    private String annualBuyPrice;

    @SerializedName("annualSellPrice")
    @Expose
    private String annualSellPrice;

    @SerializedName("buyPrice")
    @Expose
    private String buyPrice;

    @SerializedName("sellPrice")
    @Expose
    private String sellPrice;

    @SerializedName("last30daysProfitability")
    @Expose
    private String last30daysProfitability;

    @SerializedName("lastMonthProfitability")
    @Expose
    private String lastMonthProfitability;

    @SerializedName("yearProfitability")
    @Expose
    private String yearProfitability;

    @SerializedName("lastYearProfitability")
    @Expose
    private String lastYearProfitability;

    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;

    @SerializedName("index")
    @Expose
    private String index;

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndexer() {
        return indexer;
    }

    public void setIndexer(String indexer) {
        this.indexer = indexer;
    }

    public String getLast30daysProfitability() {
        return last30daysProfitability;
    }

    public void setLast30daysProfitability(String last30daysProfitability) {
        this.last30daysProfitability = last30daysProfitability;
    }

    public String getLastMonthProfitability() {
        return lastMonthProfitability;
    }

    public void setLastMonthProfitability(String lastMonthProfitability) {
        this.lastMonthProfitability = lastMonthProfitability;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastYearProfitability() {
        return lastYearProfitability;
    }

    public void setLastYearProfitability(String lastYearProfitability) {
        this.lastYearProfitability = lastYearProfitability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnualBuyPrice() {
        return annualBuyPrice;
    }

    public void setAnnualBuyPrice(String annualBuyPrice) {
        this.annualBuyPrice = annualBuyPrice;
    }

    public String getAnnualSellPrice() {
        return annualSellPrice;
    }

    public void setAnnualSellPrice(String annualSellPrice) {
        this.annualSellPrice = annualSellPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYearProfitability() {
        return yearProfitability;
    }

    public void setYearProfitability(String yearProfitability) {
        this.yearProfitability = yearProfitability;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}