package fpt.capstone.vuondau.entity.dto;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.AccountDetail;
import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.PostResource;
import fpt.capstone.vuondau.entity.common.EResourceType;

import javax.persistence.*;
import java.util.List;


public class ResourceDto {

    private Long id ;

    private String url;


    private String name;

    private EResourceType resourceType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(EResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
