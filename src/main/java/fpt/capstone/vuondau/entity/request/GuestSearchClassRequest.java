package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.common.EClassType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GuestSearchClassRequest implements Serializable {
        private EClassStatus status ;
        private EClassType classType ;
        private Long subject ;

    public EClassStatus getStatus() {
        return status;
    }

    public void setStatus(EClassStatus status) {
        this.status = status;
    }

    public EClassType getClassType() {
        return classType;
    }

    public void setClassType(EClassType classType) {
        this.classType = classType;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }
}
