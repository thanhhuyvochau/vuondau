package fpt.capstone.vuondau.entity.request;


import fpt.capstone.vuondau.entity.common.EGenderType;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class UpdateForumRequest implements Serializable {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
