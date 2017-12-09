package com.jumbomob.invistoo.model.entity;

import com.jumbomob.invistoo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 17/04/2016
 */
public enum AssetTypeEnum {

    PREFIXADO_2018(1, "Tesouro Prefixado 2018", "P", 2018, R.color.material_cyan_800, "Prefix. 2018"),
    PREFIXADO_2019(2, "Tesouro Prefixado 2019", "P", 2019, R.color.material_cyan_800, "Prefix. 2019"),
    PREFIXADO_2020(3, "Tesouro Prefixado 2020", "P", 2020, R.color.material_cyan_800, "Prefix. 2020"),
    PREFIXADO_2021(4, "Tesouro Prefixado 2021", "P", 2021, R.color.material_cyan_800, "Prefix. 2021"),
    PREFIXADO_2023(5, "Tesouro Prefixado 2023", "P", 2023, R.color.material_cyan_800, "Prefix. 2023"),
    PREFIXADO_JUROS_2021(6, "Tesouro Prefixado com Juros 2021", "PJ", 2021, R.color.material_orange_800, "Prefix. c/ Juros 2021"),
    PREFIXADO_JUROS_2023(7, "Tesouro Prefixado com Juros 2023", "PJ", 2023, R.color.material_orange_800, "Prefix. c/ Juros 2023"),
    PREFIXADO_JUROS_2025(8, "Tesouro Prefixado com Juros 2025", "PJ", 2025, R.color.material_orange_800, "Prefix. c/ Juros 2025"),
    PREFIXADO_JUROS_2027(9, "Tesouro Prefixado com Juros 2027", "PJ", 2027, R.color.material_orange_800, "Prefix. c/ Juros 2027"),
    IPCA_JUROS_2020(10, "Tesouro IPCA+ com Juros Semestrais 2020", "IJ", 2020, R.color.material_red_800, "IPCA c/ Juros 2020"),
    IPCA_JUROS_2024(11, "Tesouro IPCA+ com Juros Semestrais 2024", "IJ", 2024, R.color.material_red_800, "IPCA c/ Juros 2024"),
    IPCA_JUROS_2026(12, "Tesouro IPCA+ com Juros Semestrais 2026", "IJ", 2026, R.color.material_red_800, "IPCA c/ Juros 2026"),
    IPCA_JUROS_2035(13, "Tesouro IPCA+ com Juros Semestrais 2035", "IJ", 2035, R.color.material_red_800, "IPCA c/ Juros 2035"),
    IPCA_JUROS_2045(14, "Tesouro IPCA+ com Juros Semestrais 2045", "IJ", 2045, R.color.material_red_800, "IPCA c/ Juros 2045"),
    IPCA_JUROS_2050(15, "Tesouro IPCA+ com Juros Semestrais 2050", "IJ", 2050, R.color.material_red_800, "IPCA c/ Juros 2050"),
    IPCA_2019(16, "Tesouro IPCA+ 2019", "I", 2019, R.color.material_deep_purple_800, "IPCA 2019"),
    IPCA_2024(17, "Tesouro IPCA+ 2024", "I", 2024, R.color.material_deep_purple_800, "IPCA 2024"),
    IPCA_2035(18, "Tesouro IPCA+ 2035", "I", 2035, R.color.material_deep_purple_800, "IPCA 2035"),
    IPCA_2045(19, "Tesouro IPCA+ 2045", "I", 2045, R.color.material_deep_purple_800, "IPCA 2045"),
    IGPM_2021(20, "Tesouro IGPM+ com Juros Semestrais 2021", "IG", 2021, R.color.material_teal_700, "IGPM c/ Juros 2021"),
    IGPM_2031(21, "Tesouro IGPM+ com Juros Semestrais 2031", "IG", 2031, R.color.material_teal_700, "IGPM c/ Juros 2031"),
    SELIC_2021(22, "Tesouro Selic 2021", "S", 2017, R.color.material_yellow_A700, "SELIC 2021"),
    SELIC_2023(23, "Tesouro Selic 2023", "S", 2023, R.color.material_yellow_A700, "SELIC 2023");

    private long id;
    private String title;
    private String initials;
    private int year;
    private int colorResourceId;
    private String abbreviation;

    AssetTypeEnum(final long id, final String title, final String initials, final int year, final int colorResourceId, final String abbreviation) {
        this.id = id;
        this.title = title;
        this.initials = initials;
        this.year = year;
        this.colorResourceId = colorResourceId;
        this.abbreviation = abbreviation;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInitials() {
        return initials;
    }

    public int getYear() {
        return year;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getColorResourceId() {
        return colorResourceId;
    }

    public static AssetTypeEnum getById(long id) {
        for (AssetTypeEnum typeEnum : AssetTypeEnum.values()) {
            if (typeEnum.getId() == id) {
                return typeEnum;
            }
        }
        return null;
    }

    public static AssetTypeEnum getByTitle(String title) {
        for (AssetTypeEnum typeEnum : AssetTypeEnum.values()) {
            if (typeEnum.getTitle().equals(title)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static List<String> getTitles() {
        final AssetTypeEnum[] types = AssetTypeEnum.values();
        List<String> titles = new ArrayList<>();
        for (int index = 0; index < types.length; index++) {
            titles.add(types[index].title);
        }
        return titles;
    }

    public static int getPositionById(long typeAsset) {
        final AssetTypeEnum[] types = AssetTypeEnum.values();
        for (int index = 0; index < types.length; index++) {
            if (types[index].getId() == typeAsset) {
                return index;
            }
        }
        return 0;
    }

}
