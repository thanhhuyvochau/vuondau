package fpt.capstone.vuondau.entity;



import fpt.capstone.vuondau.entity.common.ERequestStatus;

import javax.persistence.*;


@Entity
@Table(name = "request")
public class Request extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "tile")
    private String title;

    @Column(name = "name")
    private String name ;

    @Column(name = "reason")
    private String reason  ;

    @Column(name = "url")
    private String url;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "request_type_id")
    private RequestType requestType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ERequestStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ERequestStatus getStatus() {
        return status;
    }

    public void setStatus(ERequestStatus status) {
        this.status = status;
    }
}
