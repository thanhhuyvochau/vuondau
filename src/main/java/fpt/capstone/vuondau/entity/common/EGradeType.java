package fpt.capstone.vuondau.entity.common;

import java.util.HashMap;
import java.util.Map;

public enum EGradeType {


    TEN("lớp 10"),
    ELEVENT("lớp 11"),
    TWELTFH("lớp 12")  ;

    public final String label;

    EGradeType(String label) {
        this.label = label;
    }

}
