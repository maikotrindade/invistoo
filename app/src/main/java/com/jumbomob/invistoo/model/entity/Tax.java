package com.jumbomob.invistoo.model.entity;

/**
 * Created by trindade on 12/03/17.
 */

public class Tax {



    public enum IncomeTax {

        INCOME_LESS_THAN_180(1, 22.5, 180),
        INCOME_LESS_THAN_360(2, 20.0, 360),
        INCOME_LESS_THAN_720(3,17.5, 720),
        INCOME_MORE_THAN_720(4, 15.0, 720);

        private long id;
        private double rate;
        private int days;

        IncomeTax(final long id, double rate, int days) {
            this.id = id;
            this.rate = rate;
            this.days = days;
        }

        public long getId() {
            return id;
        }

        public static IncomeTax getById(long id) {
            for (IncomeTax statusEnum : IncomeTax.values()) {
                if (statusEnum.getId() == id) {
                    return statusEnum;
                }
            }
            return null;
        }
    }
}
