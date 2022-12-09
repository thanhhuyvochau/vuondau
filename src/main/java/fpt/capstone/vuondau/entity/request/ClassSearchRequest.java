package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.common.EClassStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class ClassSearchRequest implements Serializable {
    private String q;

    private EClassStatus status;
    private Instant startDate;
    private Instant endDate;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public EClassStatus getStatus() {
        return status;
    }

    public void setStatus(EClassStatus status) {
        this.status = status;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
