package fpt.capstone.vuondau.entity;


import fpt.capstone.vuondau.entity.common.EClassLevel;
import fpt.capstone.vuondau.entity.common.EClassStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "class")
public class Class {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EClassStatus status;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "class_type_id")
//    private ClassType classType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Account account;


    @OneToMany(mappedBy = "aClass", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentClass> studentClasses;


    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FeedBack> feedBacks;

    @Column(name = "number_student")
    private Long numberStudent;

    @Column(name = "max_number_student")
    private Long maxNumberStudent;


    @Column(name = "is_avtive")
    private boolean isActive;

    @Column(name = "class_level")
    @Enumerated(EnumType.STRING)
    private EClassLevel classLevel;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TimeTable> timeTables;

    @Column(name = "resource_mooodle_id")
    private Long resourceMoodleId;

    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Forum> forums = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Resource image;
    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sections;
    @OneToMany(mappedBy = "clazz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ClassTeacherCandicate> candicates = new ArrayList<>();

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EClassStatus getStatus() {
        return status;
    }

    public void setStatus(EClassStatus status) {
        this.status = status;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

//    public ClassType getClassType() {
//        return classType;
//    }
//
//    public void setClassType(ClassType classType) {
//        this.classType = classType;
//    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getNumberStudent() {
        return numberStudent;
    }

    public void setNumberStudent(Long numberStudent) {
        this.numberStudent = numberStudent;
    }

    public EClassLevel getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(EClassLevel classLevel) {
        this.classLevel = classLevel;
    }

    public List<StudentClass> getStudentClasses() {
        return studentClasses;
    }

    public void setStudentClasses(List<StudentClass> studentClasses) {
        this.studentClasses = studentClasses;
    }

    public List<FeedBack> getFeedBacks() {
        return feedBacks;
    }

    public void setFeedBacks(List<FeedBack> feedBacks) {
        this.feedBacks = feedBacks;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public Long getMaxNumberStudent() {
        return maxNumberStudent;
    }

    public void setMaxNumberStudent(Long maxNumberStudent) {
        this.maxNumberStudent = maxNumberStudent;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public List<TimeTable> getTimeTables() {
        return timeTables;
    }

    public void setTimeTables(List<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }

    public Long getResourceMoodleId() {
        return resourceMoodleId;
    }

    public void setResourceMoodleId(Long resourceMoodleId) {
        this.resourceMoodleId = resourceMoodleId;
    }

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    public Resource getImage() {
        return image;
    }

    public void setImage(Resource image) {
        this.image = image;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<ClassTeacherCandicate> getCandicates() {
        return candicates;
    }

    public void setCandicates(List<ClassTeacherCandicate> candicates) {
        this.candicates = candicates;
    }
}
