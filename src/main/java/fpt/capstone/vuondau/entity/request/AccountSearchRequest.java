package fpt.capstone.vuondau.entity.request;

import java.io.Serializable;

public class AccountSearchRequest implements Serializable {
    private String q;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
