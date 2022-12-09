package fpt.capstone.vuondau.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProvincesDto {

    @JsonProperty("name")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
