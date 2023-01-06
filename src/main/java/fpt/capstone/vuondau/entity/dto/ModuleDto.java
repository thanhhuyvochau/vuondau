package fpt.capstone.vuondau.entity.dto;

import fpt.capstone.vuondau.entity.BaseEntity;
import fpt.capstone.vuondau.entity.EModuleType;
import fpt.capstone.vuondau.entity.Section;

import javax.persistence.*;
import java.util.Objects;



public class ModuleDto extends BaseEntity {
    private Long id;
    
    private String name;
    
    private String url;

    private EModuleType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EModuleType getType() {
        return type;
    }

    public void setType(EModuleType type) {
        this.type = type;
    }
}
