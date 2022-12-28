package fpt.capstone.vuondau.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title ;

    @Column(name = "brief")
    private String brief;

    @Column(name = "content")
    private String content;

    @Column(name = "create_by")
    private Instant createBy;

    @Column(name = "create_date")
    private Instant createDate;

    @ManyToOne
    @JoinColumn(name="admin_id", nullable=false)
    private Account account;

    @OneToMany(mappedBy = "post")
    List<PostResource> postResources;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostCategory> postCategories;



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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Instant createBy) {
        this.createBy = createBy;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<PostResource> getPostResources() {
        return postResources;
    }

    public void setPostResources(List<PostResource> postResources) {
        this.postResources = postResources;
    }

    public List<PostCategory> getPostCategories() {
        return postCategories;
    }

    public void setPostCategories(List<PostCategory> postCategories) {
        this.postCategories = postCategories;
    }
}
