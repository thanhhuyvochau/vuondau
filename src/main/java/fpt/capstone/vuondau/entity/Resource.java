package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.EResourceType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "name")
    private String name;
    @Column(name = "resource_type")
    @Enumerated(EnumType.STRING)
    private EResourceType resourceType;

    @OneToMany(mappedBy = "resource")
    List<PostResource> postResources;

    @OneToMany(mappedBy = "resource")
    List<Account> accounts;

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

    public EResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(EResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public List<PostResource> getPostResources() {
        return postResources;
    }

    public void setPostResources(List<PostResource> postResources) {
        this.postResources = postResources;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
