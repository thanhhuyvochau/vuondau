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
    @Column(name = "name")
    private String title;
    @Column(name = "template")
    private String template;
    @Column(name = "entity")
    private String entity;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
