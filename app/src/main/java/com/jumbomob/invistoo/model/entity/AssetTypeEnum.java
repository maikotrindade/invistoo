package com.jumbomob.invistoo.model.entity;

import com.jumbomob.invistoo.R;

/**
 * @author maiko.trindade
 * @since 17/04/2016
 */
public enum AssetTypeEnum {

    PREFIXADO_2017(1, "Tesouro Prefixado 2017", "P", 2017, R.color.material_cyan_800),
    PREFIXADO_2018(2, "Tesouro Prefixado 2018", "P", 2018, R.color.material_cyan_800),
    PREFIXADO_2019(3, "Tesouro Prefixado 2019", "P", 2019, R.color.material_cyan_800),
    PREFIXADO_2021(4, "Tesouro Prefixado 2021", "P", 2021, R.color.material_cyan_800),
    PREFIXADO_2023(5, "Tesouro Prefixado 2023", "P", 2023, R.color.material_cyan_800),
    PREFIXADO_JUROS_2017(6, "Tesouro Prefixado 2017", "PJ", 2017, R.color.material_orange_800),
    PREFIXADO_JUROS_2021(7, "Tesouro Prefixado 2021", "PJ", 2021, R.color.material_orange_800),
    PREFIXADO_JUROS_2023(8, "Tesouro Prefixado 2023", "PJ", 2023, R.color.material_orange_800),
    PREFIXADO_JUROS_2025(9, "Tesouro Prefixado 2025", "PJ", 2025, R.color.material_orange_800),
    PREFIXADO_JUROS_2027(10, "Tesouro Prefixado 2027", "PJ", 2027, R.color.material_orange_800),
    IPCA_JUROS_2017(11, "Tesouro IPCA+ com Juros Semestrais 2017", "IJ", 2017, R.color.material_red_800),
    IPCA_JUROS_2020(12, "Tesouro IPCA+ com Juros Semestrais 2020", "IJ", 2020, R.color.material_red_800),
    IPCA_JUROS_2024(13, "Tesouro IPCA+ com Juros Semestrais 2024", "IJ", 2024, R.color.material_red_800),
    IPCA_JUROS_2026(14, "Tesouro IPCA+ com Juros Semestrais 2026", "IJ", 2026, R.color.material_red_800),
    IPCA_JUROS_2035(15, "Tesouro IPCA+ com Juros Semestrais 2035", "IJ", 2035, R.color.material_red_800),
    IPCA_JUROS_2045(16, "Tesouro IPCA+ com Juros Semestrais 2045", "IJ", 2045, R.color.material_red_800),
    IPCA_JUROS_2050(17, "Tesouro IPCA+ com Juros Semestrais 2050", "IJ", 2050, R.color.material_red_800),
    IPCA_2019(18, "Tesouro IPCA+ 2019", "I", 2019, R.color.material_deep_purple_800),
    IPCA_2024(19, "Tesouro IPCA+ 2024", "I", 2024, R.color.material_deep_purple_800),
    IPCA_2035(20, "Tesouro IPCA+ 2035", "I", 2035, R.color.material_deep_purple_800),
    IGPM_2017(21, "Tesouro IGPM+ com Juros Semestrais 2017", "IG", 2017, R.color.material_teal_700),
    IGPM_2021(22, "Tesouro IGPM+ com Juros Semestrais 2021", "IG", 2021, R.color.material_teal_700),
    IGPM_2031(23, "Tesouro IGPM+ com Juros Semestrais 2031", "IG", 2031, R.color.material_teal_700),
    SELIC_2017(24, "Tesouro Selic 2017", "S", 2017, R.color.material_yellow_A400),
    SELIC_2021(25, "Tesouro Selic 2021", "S", 2021, R.color.material_yellow_A400);

    private long id;
    private String title;
    private String abbreviation;
    private int year;
    private int colorResourceId;

    AssetTypeEnum(final long id, final String title, final String abbreviation, final int year, final int colorResourceId) {
        this.id = id;
        this.title = title;
        this.abbreviation = abbreviation;
        this.year = year;
        this.colorResourceId = colorResourceId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getYear() {
        return year;
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

    public static String[] getTitles() {
        final AssetTypeEnum[] types = AssetTypeEnum.values();
        String[] titles = new String[types.length];
        for (int index = 0; index < types.length; index++) {
            titles[index] = types[index].title;
        }
        return titles;
    }

}
