package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.EClassLevelCode;
import fpt.capstone.vuondau.entity.common.ESubjectCode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "class_level")
public class ClassLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    private EClassLevelCode code;


    @OneToMany(mappedBy = "classLevel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountDetailClassLevel> accountDetailClassLevels;

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

    public EClassLevelCode getCode() {
        return code;
    }

    public void setCode(EClassLevelCode code) {
        this.code = code;
    }

    public List<AccountDetailClassLevel> getAccountDetailClassLevels() {
        return accountDetailClassLevels;
    }

    public void setAccountDetailClassLevels(List<AccountDetailClassLevel> accountDetailClassLevels) {
        this.accountDetailClassLevels = accountDetailClassLevels;
    }
}
