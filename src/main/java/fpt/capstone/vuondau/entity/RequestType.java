package fpt.capstone.vuondau.entity;


import fpt.capstone.vuondau.entity.common.EResourceType;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "request_type")
public class RequestType {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    private EResourceType code;


    @OneToMany(mappedBy = "requestType")
    private List<Request> requests;


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

    public EResourceType getCode() {
        return code;
    }

    public void setCode(EResourceType code) {
        this.code = code;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

}



