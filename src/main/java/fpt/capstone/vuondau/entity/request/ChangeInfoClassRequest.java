package fpt.capstone.vuondau.entity.request;


import fpt.capstone.vuondau.entity.common.EGenderType;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class ChangeInfoClassRequest implements Serializable {


    private Long id;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
