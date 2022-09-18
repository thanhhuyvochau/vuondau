package fpt.capstone.vuondau.entity.request;


import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

public class DegreeRequest implements Serializable {


    @Schema(description = "Mã  grade")
    private String code;
    @Schema(description = "Tên grade")
    private String name ;




    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
