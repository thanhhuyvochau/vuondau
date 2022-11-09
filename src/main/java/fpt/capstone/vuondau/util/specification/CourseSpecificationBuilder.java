package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.Course;


import fpt.capstone.vuondau.entity.Course_;
import fpt.capstone.vuondau.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.Expression;

public class CourseSpecificationBuilder {

    public static CourseSpecificationBuilder specification() {
        return new CourseSpecificationBuilder();
    }

    private final List<Specification<Course>> specifications = new ArrayList<>();


    public CourseSpecificationBuilder queryLike(String q) {
        if (q == null || q.trim().isEmpty()) {
            return this;
        }

        specifications.add((root, query, criteriaBuilder) -> {
            Expression<String> courseCode = root.get(Course_.CODE);
            Expression<String> courseName = root.get(Course_.NAME);
            Expression<String> stringExpression = SpecificationUtil.concat(criteriaBuilder, " ", courseCode, courseName);
            return criteriaBuilder.like(stringExpression, '%' + q + '%');
        });


        return this;
    }

    public Specification<Course> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Course> all() {
        return Specification.where(null);
    }


}