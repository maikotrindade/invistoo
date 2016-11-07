package com.jumbomob.invistoo.model.entity;

/**
 * Created by trindade on 11/6/16.
 */

public enum AssetStatusEnum {

    SELL(1, "Venda"),
    BUY(2, "Compra");

    private long id;
    private String title;

    AssetStatusEnum(final long id, final String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static AssetStatusEnum getById(long id) {
        for (AssetStatusEnum statusEnum : AssetStatusEnum.values()) {
            if (statusEnum.getId() == id) {
                return statusEnum;
            }
        }
        return null;
    }

}
