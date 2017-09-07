package com.jumbomob.invistoo.model.entity;

/**
 * Created by trindade on 12/03/17.
 */

public class Tax {

    public enum IncomeTax {

        INCOME_LESS_THAN_180(1, 0.22, 180),
        INCOME_LESS_THAN_360(2, 0.20, 360),
        INCOME_LESS_THAN_720(3, 0.17, 720),
        INCOME_MORE_THAN_720(4, 0.15, 720);

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

        public void setId(long id) {
            this.id = id;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
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
