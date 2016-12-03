package com.jumbomob.invistoo.model.entity;

/**
 * @author maiko.trindade
 * @since 03/12/2016
 */
public enum QuestionGroupEnum {

    BEGINNER(1, "Iniciante"),
    PROFITABILITY(2, "Rentabilidade"),
    ADVANCED(3, "Avançado"),
    TAXES(4, "Tributos");

    private long id;
    private String title;

    QuestionGroupEnum(final long id, final String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static QuestionGroupEnum getById(long id) {
        for (QuestionGroupEnum categoryEnum : QuestionGroupEnum.values()) {
            if (categoryEnum.getId() == id) {
                return categoryEnum;
            }
        }
        return null;
    }

}
