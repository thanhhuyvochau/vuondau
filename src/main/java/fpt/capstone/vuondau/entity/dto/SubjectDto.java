package fpt.capstone.vuondau.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class SubjectDto implements Serializable {

    @Schema(description = "ID subject")
    private Long id;
    @Schema(description = "Mã  subject")
    private String code;
    @Schema(description = "Tên subject")
    private String name ;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
