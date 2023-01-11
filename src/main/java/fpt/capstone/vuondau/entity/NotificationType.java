package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.ENotificationType;

import javax.persistence.*;

@Entity
@Table(name = "notification_type")
public class NotificationType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private ENotificationType code;
    @Column(name = "template")
    private String template;
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ENotificationType getCode() {
        return code;
    }

    public void setCode(ENotificationType code) {
        this.code = code;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
