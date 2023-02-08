package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.Course_;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.Subject_;
import fpt.capstone.vuondau.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubjectSpecificationBuilder {

    public static SubjectSpecificationBuilder specification() {
        return new SubjectSpecificationBuilder();
    }

    private final List<Specification<Subject>> specifications = new ArrayList<>();


    public SubjectSpecificationBuilder queryLike(String q) {
        if (q == null || q.trim().isEmpty()) {
            return this;
        }

        specifications.add((root, query, criteriaBuilder) -> {
            Expression<String> courseCode = root.get(Subject_.CODE);
            Expression<String> courseName = root.get(Subject_.NAME);
            Expression<String> stringExpression = SpecificationUtil.concat(criteriaBuilder, " ", courseCode, courseName);
            return criteriaBuilder.like(stringExpression, '%' + q + '%');
        });


        return this;
    }

    public Specification<Subject> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Subject> all() {
        return Specification.where(null);
    }


}