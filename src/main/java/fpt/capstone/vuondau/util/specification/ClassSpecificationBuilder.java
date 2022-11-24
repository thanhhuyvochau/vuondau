package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassSpecificationBuilder {

    public static ClassSpecificationBuilder specification() {
        return new ClassSpecificationBuilder();
    }

    private final List<Specification<Class>> specifications = new ArrayList<>();

    public ClassSpecificationBuilder queryStatusClass(EClassStatus status) {
        if (status == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Class_.STATUS), status));
        return this;
    }

//    public ClassSpecificationBuilder queryLike(String q) {
//        if (q == null || q.trim().isEmpty()) {
//            return this;
//        }
//
//        specifications.add((root, query, criteriaBuilder) -> {
//            Expression<String> courseCode = root.get(Course_.CODE);
//            Expression<String> courseName = root.get(Course_.NAME);
//            Join<Course, TeacherCourse> teacherCourseJoin = root.join(Course_.teacherCourses, JoinType.INNER);
//            Join<TeacherCourse, Account> teacherCourseAccountJoin = teacherCourseJoin.join(TeacherCourse_.ACCOUNT, JoinType.INNER);
//            Expression<String> fullName = teacherCourseAccountJoin.get(Account_.NAME);
//
//            Join<Course, Subject> courseSubjectJoin = root.join(Course_.subject, JoinType.INNER);
//            Expression<String> subject = courseSubjectJoin.get(Subject_.name);
//
//            Expression<String> stringExpression = SpecificationUtil.concat(criteriaBuilder, " " ,courseCode, courseName, fullName, subject);
//            return criteriaBuilder.like(stringExpression, '%' + q + '%');
//        });
//
//
//        return this;
//    }

    public Specification<Class> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Class> all() {
        return Specification.where(null);
    }


}