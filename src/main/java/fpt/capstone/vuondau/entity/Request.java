package fpt.capstone.vuondau.entity;


import javax.persistence.*;
import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "tile")
    private String title;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "request_type_id")
    private RequestType requestType;

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
}
