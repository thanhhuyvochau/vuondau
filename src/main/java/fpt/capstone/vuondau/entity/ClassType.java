package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.EClassLevel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "class_level")
public class ClassType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    private EClassLevel code;

//    @OneToMany(mappedBy="classType")
//    private List<Class> classes;

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

    public EClassLevel getCode() {
        return code;
    }

    public void setCode(EClassLevel code) {
        this.code = code;
    }

//    public List<Class> getClasses() {
//        return classes;
//    }
//
//    public void setClasses(List<Class> classes) {
//        this.classes = classes;
//    }

}
