package com.jumbomob.invistoo.model.entity;

import io.realm.RealmObject;

/**
 * Class responsible for adapt enum for Realm.io
 * @see <a href="https://github.com/realm/realm-java/issues/776">Github Issues</a>
 *
 * @author maiko.trindade
 * @since 27/07/2016
 */

public class AssetTypeAdapter extends RealmObject {
    private String enumDescription;

    public void saveEnum(AssetTypeEnum val) {
        this.enumDescription = val.toString();
    }

    public AssetTypeEnum getEnum() {
        return (enumDescription != null) ? AssetTypeEnum.valueOf(enumDescription) : null;
    }
}