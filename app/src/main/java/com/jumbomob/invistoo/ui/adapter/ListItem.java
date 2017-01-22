package com.jumbomob.invistoo.ui.adapter;

import com.jumbomob.invistoo.model.entity.Investment;

/**
 * Created by trindade on 1/21/17.
 */

public class ListItem extends GroupSectionItem {
    private Investment investment;

    public Investment getInvestment() {
        return investment;
    }

    public void setInvestment(Investment investment) {
        this.investment = investment;
    }

    @Override
    public int getSection() {
        return SECTION_GROUP_LIST;
    }
}
