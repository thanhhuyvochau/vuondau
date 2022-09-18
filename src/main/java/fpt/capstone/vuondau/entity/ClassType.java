package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.request.EClassType;

import javax.persistence.*;

@Entity
@Table(name = "class_type")
public class ClassType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    private EClassType code;

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

    public EClassType getCode() {
        return code;
    }

    public void setCode(EClassType code) {
        this.code = code;
    }
}
