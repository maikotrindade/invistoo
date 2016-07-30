package com.jumbomob.invistoo.model.entity;

import io.realm.RealmObject;

/**
 * Class responsible for adapt enum for Realm.io
 *
 * @author maiko.trindade
 * @see <a href="https://github.com/realm/realm-java/issues/776">Github Issues</a>
 * @since 27/07/2016
 */

public class AssetTypeAdapter extends RealmObject {
    private Long assetTypeId;

    public void saveEnum(AssetTypeEnum assetType) {
        this.assetTypeId = assetType.getId();
    }

    public AssetTypeEnum getEnum() {
        return (assetTypeId != null) ? AssetTypeEnum.getById(assetTypeId) : null;
    }
}