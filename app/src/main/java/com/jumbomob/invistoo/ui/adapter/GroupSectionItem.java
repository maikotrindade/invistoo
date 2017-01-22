package com.jumbomob.invistoo.ui.adapter;

/**
 * Created by trindade on 1/21/17.
 */

public abstract class GroupSectionItem {
    public static final int SECTION_GROUP_HEADER = 0;
    public static final int SECTION_GROUP_LIST = 1;

    abstract public int getSection();
}