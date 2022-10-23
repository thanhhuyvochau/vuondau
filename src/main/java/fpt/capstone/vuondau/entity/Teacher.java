package fpt.capstone.vuondau.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "teacher")

public class Teacher {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "introduce")
    private String introduce;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cv_url_id", referencedColumnName = "id")
    private Resource resource;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "degree_id", referencedColumnName = "id")
    private Degree degree;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;


//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher", cascade = CascadeType.ALL)
//    private List<TeacherCourse> teacherCourses = new ArrayList<>();


}


