package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.common.EClassStatus;

import java.io.Serializable;

public class ClassSearchRequest implements Serializable {
    private String q;

    private EClassStatus status ;

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
}
