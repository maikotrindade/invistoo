package com.jumbomob.invistoo.ui.adapter;

import com.jumbomob.invistoo.model.entity.AssetTypeEnum;

/**
 * Created by trindade on 1/21/17.
 */

public class HeaderItem extends GroupSectionItem {
    private AssetTypeEnum assetType;

    public AssetTypeEnum getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetTypeEnum assetType) {
        this.assetType = assetType;
    }

    @Override
    public int getSection() {
        return SECTION_GROUP_HEADER;
    }

}
