package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.ESubjectCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    private ESubjectCode code;
    @OneToMany(mappedBy = "subject")
    private List<Course> courses;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Question> questions;

    @Column(name = "moodle_category_id")
    private Long categoryMoodleId;

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

    public ESubjectCode getCode() {
        return code;
    }

    public void setCode(ESubjectCode code) {
        this.code = code;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Long getCategoryMoodleId() {
        return categoryMoodleId;
    }

    public void setCategoryMoodleId(Long categoryMoodleId) {
        this.categoryMoodleId = categoryMoodleId;
    }
}
