package com.jumbomob.invistoo.model.entity;

/**
 * @author maiko.trindade
 * @since 17/04/2016
 */
public enum AssetTypeEnum {

    PREFIXADO_2017(1, "Tesouro Prefixado 2017"),
    PREFIXADO_2018(2, "Tesouro Prefixado 2018"),
    PREFIXADO_2019(3, "Tesouro Prefixado 2019"),
    PREFIXADO_2021(4, "Tesouro Prefixado 2021"),
    PREFIXADO_2023(5, "Tesouro Prefixado 2023"),
    PREFIXADO_JUROS_2017(6, "Tesouro Prefixado 2017"),
    PREFIXADO_JUROS_2021(7, "Tesouro Prefixado 2021"),
    PREFIXADO_JUROS_2023(8, "Tesouro Prefixado 2023"),
    PREFIXADO_JUROS_2025(9, "Tesouro Prefixado 2025"),
    PREFIXADO_JUROS_2027(10, "Tesouro Prefixado 2027"),
    IPCA_JUROS_2017(11, "Tesouro IPCA+ com Juros Semestrais 2017"),
    IPCA_JUROS_2020(12, "Tesouro IPCA+ com Juros Semestrais 2020"),
    IPCA_JUROS_2024(13, "Tesouro IPCA+ com Juros Semestrais 2024"),
    IPCA_JUROS_2026(14, "Tesouro IPCA+ com Juros Semestrais 2026"),
    IPCA_JUROS_2035(15, "Tesouro IPCA+ com Juros Semestrais 2035"),
    IPCA_JUROS_2045(16, "Tesouro IPCA+ com Juros Semestrais 2045"),
    IPCA_JUROS_2050(17, "Tesouro IPCA+ com Juros Semestrais 2050"),
    IPCA_2019(18, "Tesouro IPCA+ 2019"),
    IPCA_2024(19, "Tesouro IPCA+ 2024"),
    IPCA_2035(20, "Tesouro IPCA+ 2035"),
    IGPM_2017(21, "Tesouro IGPM+ com Juros Semestrais 2017"),
    IGPM_2021(22, "Tesouro IGPM+ com Juros Semestrais 2021"),
    IGPM_2031(23, "Tesouro IGPM+ com Juros Semestrais 2031"),
    SELIC_2017(24, "Tesouro Selic 2017"),
    SELIC_2021(25, "Tesouro Selic 2021");

    private int id;
    private String title;

    AssetTypeEnum(final int id, final String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static AssetTypeEnum getById(int id) {
        for (AssetTypeEnum paxStatus : AssetTypeEnum.values()) {
            if (paxStatus.getId() == id) {
                return paxStatus;
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
