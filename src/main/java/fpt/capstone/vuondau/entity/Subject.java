package fpt.capstone.vuondau.entity;

import fpt.capstone.vuondau.entity.common.ESubjectCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "moodle_category_id")
    private Long categoryMoodleId;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Forum> forums = new ArrayList<>();

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

    public Long getCategoryMoodleId() {
        return categoryMoodleId;
    }

    public void setCategoryMoodleId(Long categoryMoodleId) {
        this.categoryMoodleId = categoryMoodleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(name, subject.name) && code == subject.code && Objects.equals(courses, subject.courses) && Objects.equals(categoryMoodleId, subject.categoryMoodleId) && Objects.equals(forums, subject.forums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, courses, categoryMoodleId, forums);
    }
}
