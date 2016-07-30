package com.jumbomob.invistoo.model.dto;

import com.jumbomob.invistoo.model.entity.Investment;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 30/07/2016
 */
public class InvestmentChartDTO {
    private String description;
    private long sum;
    private List<Investment> investments;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "InvestmentChartEntry{" +
                "description='" + description + '\'' +
                ", sum=" + sum +
                ", investments=" + investments.size() +
                '}';
    }
}
