package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.EClassLevelCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "info_find_tutor")
public class InfoFindTutor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_generator")
    @SequenceGenerator(name = "account_id_generator", sequenceName = "account_id_generator")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;


    @Column(name = "address")
    private String address;

    @Column(name = "class_level")
    private EClassLevelCode classLevel;


    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "infoFindTutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InfoFindTutorAccount> infoFindTutors;

    @OneToMany(mappedBy = "infoFindTutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InfoFindTutorSubject> infoFindTutorSubjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EClassLevelCode getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(EClassLevelCode classLevel) {
        this.classLevel = classLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InfoFindTutorAccount> getInfoFindTutors() {
        return infoFindTutors;
    }

    public void setInfoFindTutors(List<InfoFindTutorAccount> infoFindTutors) {
        this.infoFindTutors = infoFindTutors;
    }

    public List<InfoFindTutorSubject> getInfoFindTutorSubjects() {
        return infoFindTutorSubjects;
    }

    public void setInfoFindTutorSubjects(List<InfoFindTutorSubject> infoFindTutorSubjects) {
        this.infoFindTutorSubjects = infoFindTutorSubjects;
    }
}
