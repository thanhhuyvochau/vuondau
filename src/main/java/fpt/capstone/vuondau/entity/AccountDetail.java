package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.EAccountDetailStatus;
import fpt.capstone.vuondau.entity.common.EDegreeType;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "account_detail")
public class AccountDetail {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_generator")
    @SequenceGenerator(name = "account_id_generator", sequenceName = "account_id_generator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birthDay")
    private Instant birthDay;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;


    @Column(name = "domicile")
    private String domicile;

    @Column(name = "teaching_province")
    private String teachingProvince;


    @Column(name = "voice")
    private String voice;

    @Column(name = "gender")
    private String gender;

    @Column(name = "current_address")
    private String currentAddress;


    @Column(name = "id_card")
    private String idCard;


    @Column(name = "training_school_name")
    private String trainingSchoolName;

    @Column(name = "majors")
    private String majors;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EAccountDetailStatus status;

    @Column(name = "level")
    private String level;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "accountDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountDetailSubject> accountDetailSubjects;

    @OneToMany(mappedBy = "accountDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountDetailClassLevel> accountDetailClassLevels;

    @OneToMany(mappedBy = "accountDetail")
    List<Resource> resources;


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EAccountDetailStatus getStatus() {
        return status;
    }

    public void setStatus(EAccountDetailStatus status) {
        this.status = status;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getTeachingProvince() {
        return teachingProvince;
    }

    public void setTeachingProvince(String teachingProvince) {
        this.teachingProvince = teachingProvince;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTrainingSchoolName() {
        return trainingSchoolName;
    }

    public void setTrainingSchoolName(String trainingSchoolName) {
        this.trainingSchoolName = trainingSchoolName;
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<AccountDetailSubject> getAccountDetailSubjects() {
        return accountDetailSubjects;
    }

    public void setAccountDetailSubjects(List<AccountDetailSubject> accountDetailSubjects) {
        this.accountDetailSubjects = accountDetailSubjects;
    }

    public List<AccountDetailClassLevel> getAccountDetailClassLevels() {
        return accountDetailClassLevels;
    }

    public void setAccountDetailClassLevels(List<AccountDetailClassLevel> accountDetailClassLevels) {
        this.accountDetailClassLevels = accountDetailClassLevels;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Instant getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Instant birthDay) {
        this.birthDay = birthDay;
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

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
