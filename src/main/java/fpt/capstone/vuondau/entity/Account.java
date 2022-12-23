package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.EDegreeType;
import fpt.capstone.vuondau.entity.common.EGenderType;


import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "account")
public class Account {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_generator")
    @SequenceGenerator(name = "account_id_generator", sequenceName = "account_id_generator")
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private Boolean isActive = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private AccountDetail accountDetail;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentClass> studentClasses;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Class> teacherClass;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Request> requests;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentAnswer> studentAnswers;

    @Column(name = "is_keycloak")
    private Boolean isKeycloak;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<InfoFindTutorAccount> infoFindTutorAccounts = new ArrayList<>();


    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<FileAttachment> fileAttachments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private Resource resource;


//    @Column(name = "first_name")
//    private String firstName;
//    @Column(name = "last_name")
//    private String lastName;

//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "cv_url")
//    private String cvUrl;

//    @Column(name = "birthday")
//    private Instant birthday;
//
//    @Column(name = "introduce")
//    private String introduce;
//
//    @Column(name = "degree")
//    private EDegreeType degree;

//    @Column(name = "phone_number")
//    private String phoneNumber;
//
//    @Column(name = "gender")
//    @Enumerated(EnumType.STRING)
//    private EGenderType gender;


//    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TeacherCourse> teacherCourses;



//    public EGenderType getGender() {
//        return gender;
//    }
//
//    public void setGender(EGenderType gender) {
//        this.gender = gender;
//    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<InfoFindTutorAccount> getInfoFindTutorAccounts() {
        return infoFindTutorAccounts;
    }

    public void setInfoFindTutorAccounts(List<InfoFindTutorAccount> infoFindTutorAccounts) {
        this.infoFindTutorAccounts = infoFindTutorAccounts;
    }

//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public Instant getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(Instant birthday) {
//        this.birthday = birthday;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getCvUrl() {
//        return cvUrl;
//    }
//
//    public void setCvUrl(String cvUrl) {
//        this.cvUrl = cvUrl;
//    }
//
//    public String getIntroduce() {
//        return introduce;
//    }
//
//    public void setIntroduce(String introduce) {
//        this.introduce = introduce;
//    }
//
//    public EDegreeType getDegree() {
//        return degree;
//    }
//
//    public void setDegree(EDegreeType degree) {
//        this.degree = degree;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }


//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//    public List<TeacherCourse> getTeacherCourses() {
//        return teacherCourses;
//    }
//
//    public void setTeacherCourses(List<TeacherCourse> teacherCourses) {
//        this.teacherCourses = teacherCourses;
//    }

    public List<StudentClass> getStudentClasses() {
        return studentClasses;
    }

    public void setStudentClasses(List<StudentClass> studentClasses) {
        this.studentClasses = studentClasses;
    }


    public Boolean getKeycloak() {
        return isKeycloak;
    }

    public void setKeycloak(Boolean keycloak) {
        isKeycloak = keycloak;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }


    public List<StudentAnswer> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(List<StudentAnswer> studentAnswers) {
        this.studentAnswers = studentAnswers;

    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public AccountDetail getAccountDetail() {
        return accountDetail;
    }

    public void setAccountDetail(AccountDetail accountDetail) {
        this.accountDetail = accountDetail;
    }

    public List<Class> getTeacherClass() {
        return teacherClass;
    }

    public void setTeacherClass(List<Class> teacherClass) {
        this.teacherClass = teacherClass;
    }


    public List<FileAttachment> getFileAttachments() {
        return fileAttachments;
    }

    public void setFileAttachments(List<FileAttachment> fileAttachments) {
        this.fileAttachments = fileAttachments;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(username, account.username) && Objects.equals(password, account.password) && Objects.equals(isActive, account.isActive) && Objects.equals(role, account.role) && Objects.equals(transactions, account.transactions)  && Objects.equals(studentClasses, account.studentClasses) && Objects.equals(teacherClass, account.teacherClass) && Objects.equals(requests, account.requests) && Objects.equals(studentAnswers, account.studentAnswers) && Objects.equals(questions, account.questions) && Objects.equals(comments, account.comments) && Objects.equals(accountDetail, account.accountDetail) && Objects.equals(infoFindTutorAccounts, account.infoFindTutorAccounts) && Objects.equals(fileAttachments, account.fileAttachments) && Objects.equals(resource, account.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isActive,  role, transactions, studentClasses, teacherClass, requests, studentAnswers, questions, comments, accountDetail, infoFindTutorAccounts, fileAttachments, resource);
    }
}
